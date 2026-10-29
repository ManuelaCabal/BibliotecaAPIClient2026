package com.biblioteca.bibliotecaapiclient26.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.biblioteca.bibliotecaapiclient26.dto.AutorDTO;
import com.biblioteca.bibliotecaapiclient26.dto.AutorLibroDTO;
import com.biblioteca.bibliotecaapiclient26.dto.LibroDTO;
import com.biblioteca.bibliotecaapiclient26.dto.PaisDTO;
import com.biblioteca.bibliotecaapiclient26.dto.PremioDTO;
import com.biblioteca.bibliotecaapiclient26.mapper.DtoFormMapper;
import com.biblioteca.bibliotecaapiclient26.service.ApiClientException;
import com.biblioteca.bibliotecaapiclient26.service.AutorLibroService;
import com.biblioteca.bibliotecaapiclient26.service.AutorService;
import com.biblioteca.bibliotecaapiclient26.service.LibroService;
import com.biblioteca.bibliotecaapiclient26.service.PaisService;
import com.biblioteca.bibliotecaapiclient26.service.PremioService;
import com.biblioteca.bibliotecaapiclient26.web.form.AutorForm;
import com.biblioteca.bibliotecaapiclient26.web.view.AutorListRow;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;
    private final AutorLibroService autorLibroService;
    private final LibroService libroService;
    private final PaisService paisService;
    private final PremioService premioService;
    private final DtoFormMapper mapper;

    public AutorController(AutorService autorService, AutorLibroService autorLibroService, LibroService libroService,
            PaisService paisService, PremioService premioService, DtoFormMapper mapper) {
        this.autorService = autorService;
        this.autorLibroService = autorLibroService;
        this.libroService = libroService;
        this.paisService = paisService;
        this.premioService = premioService;
        this.mapper = mapper;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("activeSection", "autores");
        try {
            List<AutorDTO> autores = autorService.findAll();
            model.addAttribute("autores", autores);
            model.addAttribute("autorRows", buildAutorRows(autores));
            model.addAttribute("totalAutores", autores.size());
        } catch (ApiClientException exception) {
            model.addAttribute("autores", Collections.emptyList());
            model.addAttribute("autorRows", Collections.emptyList());
            model.addAttribute("totalAutores", 0);
            model.addAttribute("globalError", exception.getMessage());
        }
        return "autores/list";
    }

    private List<AutorListRow> buildAutorRows(List<AutorDTO> autores) {
        Map<Integer, List<LibroDTO>> booksByAuthorId = loadBooksByAuthorId();
        return autores.stream()
                .map(autor -> buildAutorRow(autor, booksByAuthorId))
                .toList();
    }

    private AutorListRow buildAutorRow(AutorDTO autor, Map<Integer, List<LibroDTO>> booksByAuthorId) {
        List<LibroDTO> librosAutor = booksByAuthorId.getOrDefault(autor.getId(), Collections.emptyList());
        return new AutorListRow(
                autor,
                sortBooks(librosAutor),
                sortPremios(autor.getPremios()));
    }

    private Map<Integer, List<LibroDTO>> loadBooksByAuthorId() {
        try {
            List<AutorLibroDTO> relaciones = autorLibroService.findAll();
            List<LibroDTO> libros = libroService.findAll();

            Map<Integer, LibroDTO> libroById = libros.stream()
                    .filter(Objects::nonNull)
                    .filter(libro -> libro.getId() != null)
                    .collect(Collectors.toMap(LibroDTO::getId, libro -> libro, (left, right) -> left));

            Map<Integer, Set<Integer>> libroIdsByAutor = relaciones.stream()
                    .filter(Objects::nonNull)
                    .filter(rel -> rel.getAutorId() != null && rel.getLibroId() != null)
                    .collect(Collectors.groupingBy(
                            AutorLibroDTO::getAutorId,
                            Collectors.mapping(AutorLibroDTO::getLibroId, Collectors.toSet())));

            return libroIdsByAutor.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> entry.getValue().stream()
                                    .map(libroById::get)
                                    .filter(Objects::nonNull)
                                    .toList()));
        } catch (ApiClientException exception) {
            return Collections.emptyMap();
        }
    }

    private List<LibroDTO> sortBooks(List<LibroDTO> libros) {
        if (libros == null) {
            return Collections.emptyList();
        }
        return libros.stream()
                .sorted(Comparator.comparing((LibroDTO libro) -> libro.getAno(), Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(libro -> libro.getNomLibro(), Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)))
                .toList();
    }

    private List<PremioDTO> sortPremios(List<PremioDTO> premios) {
        if (premios == null) {
            return Collections.emptyList();
        }
        return premios.stream()
                .sorted(Comparator.comparing((PremioDTO premio) -> premio.getAnyo(), Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(premio -> premio.getNomPremio(), Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)))
                .toList();
    }

    @GetMapping("/nuevo")
    public String createForm(Model model) {
        populateForm(model, new AutorForm(), false, null);
        return "autores/form";
    }

    @GetMapping("/{id}/editar")
    public String editForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            AutorDTO autor = autorService.findById(id);
            populateForm(model, mapper.toAutorForm(autor), true, null);
            return "autores/form";
        } catch (ApiClientException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/autores";
        }
    }

    @PostMapping("/guardar")
    public String save(@Valid @ModelAttribute("autorForm") AutorForm autorForm, BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes) {
        boolean editing = autorForm.getId() != null;
        if (bindingResult.hasErrors()) {
            populateForm(model, autorForm, editing, null);
            return "autores/form";
        }

        try {
            PaisDTO pais = paisService.findByIdOrNull(autorForm.getPaisId());
            List<PremioDTO> premios = premioService.findByIds(autorForm.getPremioIds());
            AutorDTO autorDto = mapper.toAutorDto(autorForm, pais, premios);

            if (editing) {
                autorService.update(autorForm.getId(), autorDto);
                redirectAttributes.addFlashAttribute("successMessage", "Autor actualizado correctamente.");
            } else {
                autorService.create(autorDto);
                redirectAttributes.addFlashAttribute("successMessage", "Autor creado correctamente.");
            }
            return "redirect:/autores";
        } catch (ApiClientException exception) {
            populateForm(model, autorForm, editing, exception.getMessage());
            return "autores/form";
        }
    }

    @PostMapping("/{id}/eliminar")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            autorService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Autor eliminado correctamente.");
        } catch (ApiClientException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
        }
        return "redirect:/autores";
    }

    private void populateForm(Model model, AutorForm autorForm, boolean editing, String globalError) {
        model.addAttribute("activeSection", "autores");
        model.addAttribute("autorForm", autorForm);
        model.addAttribute("pageTitle", editing ? "Editar autor" : "Nuevo autor");
        model.addAttribute("submitLabel", editing ? "Guardar cambios" : "Crear autor");
        model.addAttribute("paises", loadCountries());
        model.addAttribute("premiosDisponibles", loadPremios());
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

    private List<PremioDTO> loadPremios() {
        try {
            return premioService.findAll();
        } catch (ApiClientException exception) {
            return Collections.emptyList();
        }
    }
}
