package com.biblioteca.bibliotecaapiclient26.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.biblioteca.bibliotecaapiclient26.service.ApiClientException;
import com.biblioteca.bibliotecaapiclient26.service.AutorService;
import com.biblioteca.bibliotecaapiclient26.service.LibroService;
import com.biblioteca.bibliotecaapiclient26.service.PaisService;
import com.biblioteca.bibliotecaapiclient26.service.PremioService;

@Controller
public class HomeController {

    private final AutorService autorService;
    private final LibroService libroService;
    private final PaisService paisService;
    private final PremioService premioService;

    public HomeController(AutorService autorService, LibroService libroService, PaisService paisService,
            PremioService premioService) {
        this.autorService = autorService;
        this.libroService = libroService;
        this.paisService = paisService;
        this.premioService = premioService;
    }

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("activeSection", "inicio");
        model.addAttribute("autoresCount", safeCount(() -> autorService.findAll().size()));
        model.addAttribute("librosCount", safeCount(() -> libroService.findAll().size()));
        model.addAttribute("paisesCount", safeCount(() -> paisService.findAll().size()));
        model.addAttribute("premiosCount", safeCount(() -> premioService.findAll().size()));
        model.addAttribute("apiStatus", "API BibliotecaAPI26 configurada en http://localhost:8080/api");
        return "index";
    }

    private int safeCount(CountSupplier supplier) {
        try {
            return supplier.get();
        } catch (ApiClientException exception) {
            return 0;
        }
    }

    @FunctionalInterface
    private interface CountSupplier {
        int get();
    }
}
