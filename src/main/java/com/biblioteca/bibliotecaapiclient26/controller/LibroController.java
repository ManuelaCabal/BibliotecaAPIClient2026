package com.biblioteca.bibliotecaapiclient26.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
import com.biblioteca.bibliotecaapiclient26.dto.PremioDTO;
import com.biblioteca.bibliotecaapiclient26.dto.SerieDTO;
import com.biblioteca.bibliotecaapiclient26.mapper.DtoFormMapper;
import com.biblioteca.bibliotecaapiclient26.service.ApiClientException;
import com.biblioteca.bibliotecaapiclient26.service.AutorLibroService;
import com.biblioteca.bibliotecaapiclient26.service.AutorService;
import com.biblioteca.bibliotecaapiclient26.service.LibroService;
import com.biblioteca.bibliotecaapiclient26.service.SerieService;
import com.biblioteca.bibliotecaapiclient26.web.form.LibroForm;
import com.biblioteca.bibliotecaapiclient26.web.view.LibroListRow;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/libros")
public class LibroController {

    private final LibroService libroService;
    private final AutorService autorService;
    private final AutorLibroService autorLibroService;
    private final SerieService serieService;
    private final DtoFormMapper mapper;

    public LibroController(LibroService libroService, AutorService autorService, AutorLibroService autorLibroService,
            SerieService serieService, DtoFormMapper mapper) {
        this.libroService = libroService;
        this.autorService = autorService;
        this.autorLibroService = autorLibroService;
        this.serieService = serieService;
        this.mapper = mapper;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("activeSection", "libros");
        try {
            List<LibroDTO> libros = libroService.findAll();
            model.addAttribute("libros", libros);
            model.addAttribute("libroRows", buildLibroRows(libros));
            model.addAttribute("totalLibros", libros.size());
        } catch (ApiClientException exception) {
            model.addAttribute("libros", Collections.emptyList());
            model.addAttribute("libroRows", Collections.emptyList());
            model.addAttribute("totalLibros", 0);
            model.addAttribute("globalError", exception.getMessage());
        }
        return "libros/list";
    }

    private List<LibroListRow> buildLibroRows(List<LibroDTO> libros) {
        Map<Integer, List<AutorDTO>> authorsByBookId = loadAuthorsByBookId();
        return libros.stream()
                .map(libro -> buildLibroRow(libro, authorsByBookId.getOrDefault(libro.getId(), Collections.emptyList())))
                .toList();
    }

    private LibroListRow buildLibroRow(LibroDTO libro, List<AutorDTO> autores) {
        List<AutorDTO> autoresOrdenados = sortAuthors(autores);
        List<PremioDTO> premiosOrdenados = sortPremios(resolveBookAwards(autoresOrdenados));
        return new LibroListRow(libro, autoresOrdenados, premiosOrdenados);
    }

    private Map<Integer, List<AutorDTO>> loadAuthorsByBookId() {
        try {
            List<AutorLibroDTO> relaciones = autorLibroService.findAll();
            List<AutorDTO> autores = autorService.findAll();

            Map<Integer, AutorDTO> autorById = autores.stream()
                    .filter(Objects::nonNull)
                    .filter(autor -> autor.getId() != null)
                    .collect(Collectors.toMap(autor -> autor.getId(), autor -> autor, (left, right) -> left));

            Map<Integer, Set<Integer>> autorIdsByLibro = relaciones.stream()
                    .filter(Objects::nonNull)
                    .filter(rel -> rel.getLibroId() != null && rel.getAutorId() != null)
                    .collect(Collectors.groupingBy(
                        rel -> rel.getLibroId(),
                        Collectors.mapping(rel -> rel.getAutorId(), Collectors.toSet())));

            return autorIdsByLibro.entrySet().stream()
                    .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                            entry -> entry.getValue().stream()
                                    .map(autorById::get)
                                    .filter(Objects::nonNull)
                                    .toList()));
        } catch (ApiClientException exception) {
            return Collections.emptyMap();
        }
    }

    private List<AutorDTO> sortAuthors(List<AutorDTO> autores) {
        if (autores == null) {
            return Collections.emptyList();
        }
        return autores.stream()
                .sorted(Comparator.comparing((AutorDTO autor) -> autor.getApellido1(), Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))
                        .thenComparing(autor -> autor.getApellido2(), Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))
                        .thenComparing(autor -> autor.getNomAutor(), Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)))
                .toList();
    }

    private List<PremioDTO> resolveBookAwards(List<AutorDTO> autores) {
        if (autores == null) {
            return Collections.emptyList();
        }
        Map<String, PremioDTO> premiosByKey = autores.stream()
                .filter(Objects::nonNull)
                .flatMap(autor -> autor.getPremios() != null ? autor.getPremios().stream() : java.util.stream.Stream.empty())
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(this::premioKey, premio -> premio, (left, right) -> left));
        return premiosByKey.values().stream().toList();
    }

    private String premioKey(PremioDTO premio) {
        if (premio.getId() != null) {
            return "id:" + premio.getId();
        }
        return "nom:" + String.valueOf(premio.getNomPremio()) + ":anyo:" + String.valueOf(premio.getAnyo());
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
        populateForm(model, new LibroForm(), false, null);
        return "libros/form";
    }

    @GetMapping("/{id}/editar")
    public String editForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            LibroDTO libro = libroService.findById(id);
            populateForm(model, mapper.toLibroForm(libro), true, null);
            return "libros/form";
        } catch (ApiClientException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/libros";
        }
    }

    @PostMapping("/guardar")
    public String save(@Valid @ModelAttribute("libroForm") LibroForm libroForm, BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes) {
        boolean editing = libroForm.getId() != null;
        if (bindingResult.hasErrors()) {
            populateForm(model, libroForm, editing, null);
            return "libros/form";
        }

        try {
            SerieDTO serie = serieService.findByIdOrNull(libroForm.getSerieId());
            LibroDTO libroDto = mapper.toLibroDto(libroForm, serie);
            if (editing) {
                libroService.update(libroForm.getId(), libroDto);
                redirectAttributes.addFlashAttribute("successMessage", "Libro actualizado correctamente.");
            } else {
                libroService.create(libroDto);
                redirectAttributes.addFlashAttribute("successMessage", "Libro creado correctamente.");
            }
            return "redirect:/libros";
        } catch (ApiClientException exception) {
            populateForm(model, libroForm, editing, exception.getMessage());
            return "libros/form";
        }
    }

    @PostMapping("/{id}/eliminar")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            libroService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Libro eliminado correctamente.");
        } catch (ApiClientException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
        }
        return "redirect:/libros";
    }

    private void populateForm(Model model, LibroForm libroForm, boolean editing, String globalError) {
        model.addAttribute("activeSection", "libros");
        model.addAttribute("libroForm", libroForm);
        model.addAttribute("pageTitle", editing ? "Editar libro" : "Nuevo libro");
        model.addAttribute("submitLabel", editing ? "Guardar cambios" : "Crear libro");
        model.addAttribute("series", loadSeries());
        if (globalError != null) {
            model.addAttribute("globalError", globalError);
        }
    }

    private List<SerieDTO> loadSeries() {
        try {
            return serieService.findAll();
        } catch (ApiClientException exception) {
            return Collections.emptyList();
        }
    }
}
