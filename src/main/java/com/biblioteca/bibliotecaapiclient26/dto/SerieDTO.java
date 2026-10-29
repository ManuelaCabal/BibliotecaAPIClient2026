package com.biblioteca.bibliotecaapiclient26.dto;

import java.util.List;

public class SerieDTO {

    private Integer id;
    private String denominacion;
    private List<LibroDTO> libros;

    public SerieDTO() {
    }

    public SerieDTO(Integer id, String denominacion) {
        this.id = id;
        this.denominacion = denominacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public List<LibroDTO> getLibros() {
        return libros;
    }

    public void setLibros(List<LibroDTO> libros) {
        this.libros = libros;
    }
}
