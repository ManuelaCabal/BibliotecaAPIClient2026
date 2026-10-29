package com.biblioteca.bibliotecaapiclient26.web.view;

import java.util.List;

import com.biblioteca.bibliotecaapiclient26.dto.AutorDTO;
import com.biblioteca.bibliotecaapiclient26.dto.LibroDTO;
import com.biblioteca.bibliotecaapiclient26.dto.PremioDTO;

public record LibroListRow(
        LibroDTO libro,
        List<AutorDTO> autoresOrdenados,
        List<PremioDTO> premiosOrdenados) {
}