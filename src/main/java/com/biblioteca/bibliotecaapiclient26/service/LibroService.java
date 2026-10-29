package com.biblioteca.bibliotecaapiclient26.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.biblioteca.bibliotecaapiclient26.config.ApiProperties;
import com.biblioteca.bibliotecaapiclient26.dto.LibroDTO;

@Service
public class LibroService extends AbstractApiCrudService<LibroDTO> {

    public LibroService(RestClient restClient, ApiProperties apiProperties) {
        super(restClient, apiProperties.getPaths().getLibros(), LibroDTO.class,
                new ParameterizedTypeReference<List<LibroDTO>>() {
                }, "libros");
    }
}
