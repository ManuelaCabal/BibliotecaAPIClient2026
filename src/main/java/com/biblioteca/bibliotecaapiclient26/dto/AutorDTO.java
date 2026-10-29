package com.biblioteca.bibliotecaapiclient26.dto;

import java.time.LocalDate;
import java.util.List;

public class AutorDTO {

    private Integer id;
    private String nomAutor;
    private String apellido1;
    private String apellido2;
    private LocalDate fechaNacimiento;
    private LocalDate fechaDefuncion;
    private PaisDTO pais;
    private String foto;
    private String localidad;
    private String biografia;
    private List<LibroDTO> libros;
    private List<PremioDTO> premios;

    public AutorDTO() {
    }

    public AutorDTO(Integer id, String nomAutor, String apellido1, String apellido2) {
        this.id = id;
        this.nomAutor = nomAutor;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomAutor() {
        return nomAutor;
    }

    public void setNomAutor(String nomAutor) {
        this.nomAutor = nomAutor;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDate getFechaDefuncion() {
        return fechaDefuncion;
    }

    public void setFechaDefuncion(LocalDate fechaDefuncion) {
        this.fechaDefuncion = fechaDefuncion;
    }

    public PaisDTO getPais() {
        return pais;
    }

    public void setPais(PaisDTO pais) {
        this.pais = pais;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public List<LibroDTO> getLibros() {
        return libros;
    }

    public void setLibros(List<LibroDTO> libros) {
        this.libros = libros;
    }

    public List<PremioDTO> getPremios() {
        return premios;
    }

    public void setPremios(List<PremioDTO> premios) {
        this.premios = premios;
    }
}
