package com.biblioteca.bibliotecaapiclient26.dto;

import java.util.List;

public class PaisDTO {

    private Integer id;
    private String nomPais;
    private String bandera;
    private List<AutorDTO> autores;
    private List<PremioDTO> premios;

    public PaisDTO() {
    }

    public PaisDTO(Integer id, String nomPais, String bandera) {
        this.id = id;
        this.nomPais = nomPais;
        this.bandera = bandera;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomPais() {
        return nomPais;
    }

    public void setNomPais(String nomPais) {
        this.nomPais = nomPais;
    }

    public String getBandera() {
        return bandera;
    }

    public void setBandera(String bandera) {
        this.bandera = bandera;
    }

    public List<AutorDTO> getAutores() {
        return autores;
    }

    public void setAutores(List<AutorDTO> autores) {
        this.autores = autores;
    }

    public List<PremioDTO> getPremios() {
        return premios;
    }

    public void setPremios(List<PremioDTO> premios) {
        this.premios = premios;
    }
}
