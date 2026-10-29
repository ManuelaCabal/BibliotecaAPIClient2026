package com.biblioteca.bibliotecaapiclient26.web.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PremioForm {

    private Integer id;

    @NotBlank(message = "El nombre del premio es obligatorio.")
    @Size(max = 160, message = "El nombre del premio no puede superar 160 caracteres.")
    private String nomPremio;

    private Integer paisId;

    @NotBlank(message = "El tipo es obligatorio.")
    @Size(max = 120, message = "El tipo no puede superar 120 caracteres.")
    private String tipo;

    @Size(max = 255, message = "El logotipo no puede superar 255 caracteres.")
    private String logotipo;

    @Min(value = 0, message = "El año debe ser positivo.")
    @Max(value = 3000, message = "El año indicado no es válido.")
    private Integer anyo;

    @Size(max = 120, message = "La localidad no puede superar 120 caracteres.")
    private String localidad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomPremio() {
        return nomPremio;
    }

    public void setNomPremio(String nomPremio) {
        this.nomPremio = nomPremio;
    }

    public Integer getPaisId() {
        return paisId;
    }

    public void setPaisId(Integer paisId) {
        this.paisId = paisId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLogotipo() {
        return logotipo;
    }

    public void setLogotipo(String logotipo) {
        this.logotipo = logotipo;
    }

    public Integer getAnyo() {
        return anyo;
    }

    public void setAnyo(Integer anyo) {
        this.anyo = anyo;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }
}
