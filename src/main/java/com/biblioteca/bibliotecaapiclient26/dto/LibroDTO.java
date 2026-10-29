package com.biblioteca.bibliotecaapiclient26.dto;

public class LibroDTO {

    private Integer id;
    private String nomLibro;
    private Integer ano;
    private String portada;
    private String nomArchivo;
    private String pelicula;
    private String comentario;
    private SerieDTO serie;
    private Integer indiceSerie;

    public LibroDTO() {
    }

    public LibroDTO(Integer id, String nomLibro, Integer ano) {
        this.id = id;
        this.nomLibro = nomLibro;
        this.ano = ano;
    }

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

    public SerieDTO getSerie() {
        return serie;
    }

    public void setSerie(SerieDTO serie) {
        this.serie = serie;
    }

    public Integer getIndiceSerie() {
        return indiceSerie;
    }

    public void setIndiceSerie(Integer indiceSerie) {
        this.indiceSerie = indiceSerie;
    }
}
