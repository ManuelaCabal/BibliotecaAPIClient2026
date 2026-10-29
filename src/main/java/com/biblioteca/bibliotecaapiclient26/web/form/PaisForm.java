package com.biblioteca.bibliotecaapiclient26.web.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PaisForm {

    private Integer id;

    @NotBlank(message = "El nombre del país es obligatorio.")
    @Size(max = 120, message = "El nombre del país no puede superar 120 caracteres.")
    private String nomPais;

    @Size(max = 255, message = "La bandera no puede superar 255 caracteres.")
    private String bandera;

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
}
