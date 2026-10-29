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
import com.biblioteca.bibliotecaapiclient26.dto.PremioDTO;
import com.biblioteca.bibliotecaapiclient26.mapper.DtoFormMapper;
import com.biblioteca.bibliotecaapiclient26.service.ApiClientException;
import com.biblioteca.bibliotecaapiclient26.service.PaisService;
import com.biblioteca.bibliotecaapiclient26.service.PremioService;
import com.biblioteca.bibliotecaapiclient26.web.form.PremioForm;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/premios")
public class PremioController {

    private final PremioService premioService;
    private final PaisService paisService;
    private final DtoFormMapper mapper;

    public PremioController(PremioService premioService, PaisService paisService, DtoFormMapper mapper) {
        this.premioService = premioService;
        this.paisService = paisService;
        this.mapper = mapper;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("activeSection", "premios");
        try {
            List<PremioDTO> premios = premioService.findAll();
            model.addAttribute("premios", premios);
            model.addAttribute("totalPremios", premios.size());
        } catch (ApiClientException exception) {
            model.addAttribute("premios", Collections.emptyList());
            model.addAttribute("totalPremios", 0);
            model.addAttribute("globalError", exception.getMessage());
        }
        return "premios/list";
    }

    @GetMapping("/nuevo")
    public String createForm(Model model) {
        populateForm(model, new PremioForm(), false, null);
        return "premios/form";
    }

    @GetMapping("/{id}/editar")
    public String editForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            PremioDTO premio = premioService.findById(id);
            populateForm(model, mapper.toPremioForm(premio), true, null);
            return "premios/form";
        } catch (ApiClientException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/premios";
        }
    }

    @PostMapping("/guardar")
    public String save(@Valid @ModelAttribute("premioForm") PremioForm premioForm, BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes) {
        boolean editing = premioForm.getId() != null;
        if (bindingResult.hasErrors()) {
            populateForm(model, premioForm, editing, null);
            return "premios/form";
        }

        try {
            PaisDTO pais = paisService.findByIdOrNull(premioForm.getPaisId());
            PremioDTO premioDto = mapper.toPremioDto(premioForm, pais);
            if (editing) {
                premioService.update(premioForm.getId(), premioDto);
                redirectAttributes.addFlashAttribute("successMessage", "Premio actualizado correctamente.");
            } else {
                premioService.create(premioDto);
                redirectAttributes.addFlashAttribute("successMessage", "Premio creado correctamente.");
            }
            return "redirect:/premios";
        } catch (ApiClientException exception) {
            populateForm(model, premioForm, editing, exception.getMessage());
            return "premios/form";
        }
    }

    @PostMapping("/{id}/eliminar")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            premioService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Premio eliminado correctamente.");
        } catch (ApiClientException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
        }
        return "redirect:/premios";
    }

    private void populateForm(Model model, PremioForm premioForm, boolean editing, String globalError) {
        model.addAttribute("activeSection", "premios");
        model.addAttribute("premioForm", premioForm);
        model.addAttribute("pageTitle", editing ? "Editar premio" : "Nuevo premio");
        model.addAttribute("submitLabel", editing ? "Guardar cambios" : "Crear premio");
        model.addAttribute("paises", loadCountries());
        if (globalError != null) {
            model.addAttribute("globalError", globalError);
        }
    }

    private List<PaisDTO> loadCountries() {
        try {
            return paisService.findAll();
        } catch (ApiClientException exception) {
            return Collections.emptyList();
        }
    }
}
