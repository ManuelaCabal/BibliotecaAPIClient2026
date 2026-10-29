package com.biblioteca.bibliotecaapiclient26.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.biblioteca.bibliotecaapiclient26.config.ApiProperties;
import com.biblioteca.bibliotecaapiclient26.dto.AutorLibroDTO;

@Service
public class AutorLibroService extends AbstractApiCrudService<AutorLibroDTO> {

    public AutorLibroService(RestClient restClient, ApiProperties apiProperties) {
        super(restClient, apiProperties.getPaths().getAutorLibro(), AutorLibroDTO.class,
                new ParameterizedTypeReference<List<AutorLibroDTO>>() {
                }, "relaciones autor-libro");
    }
}
