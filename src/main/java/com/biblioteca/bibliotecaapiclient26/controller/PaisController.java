package com.biblioteca.bibliotecaapiclient26.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.biblioteca.bibliotecaapiclient26.dto.PaisDTO;
import com.biblioteca.bibliotecaapiclient26.mapper.DtoFormMapper;
import com.biblioteca.bibliotecaapiclient26.service.ApiClientException;
import com.biblioteca.bibliotecaapiclient26.service.PaisService;
import com.biblioteca.bibliotecaapiclient26.web.form.PaisForm;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/paises")
public class PaisController {

    private final PaisService paisService;
    private final DtoFormMapper mapper;

    public PaisController(PaisService paisService, DtoFormMapper mapper) {
        this.paisService = paisService;
        this.mapper = mapper;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("activeSection", "paises");
        try {
            List<PaisDTO> paises = paisService.findAll();
            model.addAttribute("paises", paises);
            model.addAttribute("totalPaises", paises.size());
        } catch (ApiClientException exception) {
            model.addAttribute("paises", Collections.emptyList());
            model.addAttribute("totalPaises", 0);
            model.addAttribute("globalError", exception.getMessage());
        }
        return "paises/list";
    }

    @GetMapping("/nuevo")
    public String createForm(Model model) {
        populateForm(model, new PaisForm(), false, null);
        return "paises/form";
    }

    @GetMapping("/{id}/editar")
    public String editForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            PaisDTO pais = paisService.findById(id);
            populateForm(model, mapper.toPaisForm(pais), true, null);
            return "paises/form";
        } catch (ApiClientException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/paises";
        }
    }

    @PostMapping("/guardar")
    public String save(@Valid @ModelAttribute("paisForm") PaisForm paisForm, BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) {
        boolean editing = paisForm.getId() != null;
        if (bindingResult.hasErrors()) {
            populateForm(model, paisForm, editing, null);
            return "paises/form";
        }

        try {
            PaisDTO paisDto = mapper.toPaisDto(paisForm);
            if (editing) {
                paisService.update(paisForm.getId(), paisDto);
                redirectAttributes.addFlashAttribute("successMessage", "País actualizado correctamente.");
            } else {
                paisService.create(paisDto);
                redirectAttributes.addFlashAttribute("successMessage", "País creado correctamente.");
            }
            return "redirect:/paises";
        } catch (ApiClientException exception) {
            populateForm(model, paisForm, editing, exception.getMessage());
            return "paises/form";
        }
    }

    @PostMapping("/{id}/eliminar")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            paisService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "País eliminado correctamente.");
        } catch (ApiClientException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
        }
        return "redirect:/paises";
    }

    private void populateForm(Model model, PaisForm paisForm, boolean editing, String globalError) {
        model.addAttribute("activeSection", "paises");
        model.addAttribute("paisForm", paisForm);
        model.addAttribute("pageTitle", editing ? "Editar país" : "Nuevo país");
        model.addAttribute("submitLabel", editing ? "Guardar cambios" : "Crear país");
        if (globalError != null) {
            model.addAttribute("globalError", globalError);
        }
    }
}
