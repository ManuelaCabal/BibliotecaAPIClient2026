package com.biblioteca.bibliotecaapiclient26.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "biblioteca.api")
public class ApiProperties {

    private String baseUrl = "http://localhost:8082/api";
    private final Paths paths = new Paths();

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Paths getPaths() {
        return paths;
    }

    public static class Paths {
        private String autores = "/autores/DTO";
        private String libros = "/libros/DTO";
        private String autorLibro = "/autor-libro";
        private String paises = "/paises/DTO";
        private String premios = "/premios/DTO";
        private String series = "/series/DTO";

        public String getAutores() {
            return autores;
        }

        public void setAutores(String autores) {
            this.autores = autores;
        }

        public String getLibros() {
            return libros;
        }

        public void setLibros(String libros) {
            this.libros = libros;
        }

        public String getAutorLibro() {
            return autorLibro;
        }

        public void setAutorLibro(String autorLibro) {
            this.autorLibro = autorLibro;
        }

        public String getPaises() {
            return paises;
        }

        public void setPaises(String paises) {
            this.paises = paises;
        }

        public String getPremios() {
            return premios;
        }

        public void setPremios(String premios) {
            this.premios = premios;
        }

        public String getSeries() {
            return series;
        }

        public void setSeries(String series) {
            this.series = series;
        }
    }
}
