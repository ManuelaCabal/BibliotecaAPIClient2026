package com.biblioteca.bibliotecaapiclient26.web.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LibroForm {

    private Integer id;

    @NotBlank(message = "El nombre del libro es obligatorio.")
    @Size(max = 200, message = "El nombre del libro no puede superar 200 caracteres.")
    private String nomLibro;

    @Min(value = 0, message = "El año debe ser positivo.")
    @Max(value = 3000, message = "El año indicado no es válido.")
    private Integer ano;

    @Size(max = 255, message = "La portada no puede superar 255 caracteres.")
    private String portada;

    @Size(max = 255, message = "El nombre de archivo no puede superar 255 caracteres.")
    private String nomArchivo;

    @Size(max = 255, message = "La película no puede superar 255 caracteres.")
    private String pelicula;

    @Size(max = 3000, message = "El comentario no puede superar 3000 caracteres.")
    private String comentario;

    private Integer serieId;

    @Min(value = 0, message = "El índice de serie debe ser positivo.")
    private Integer indiceSerie;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomLibro() {
        return nomLibro;
    }

    public void setNomLibro(String nomLibro) {
        this.nomLibro = nomLibro;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public String getNomArchivo() {
        return nomArchivo;
    }

    public void setNomArchivo(String nomArchivo) {
        this.nomArchivo = nomArchivo;
    }

    public String getPelicula() {
        return pelicula;
    }

    public void setPelicula(String pelicula) {
        this.pelicula = pelicula;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getSerieId() {
        return serieId;
    }

    public void setSerieId(Integer serieId) {
        this.serieId = serieId;
    }

    public Integer getIndiceSerie() {
        return indiceSerie;
    }

    public void setIndiceSerie(Integer indiceSerie) {
        this.indiceSerie = indiceSerie;
    }
}
