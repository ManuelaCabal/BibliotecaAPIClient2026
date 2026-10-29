package com.biblioteca.bibliotecaapiclient26.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.biblioteca.bibliotecaapiclient26.config.ApiProperties;
import com.biblioteca.bibliotecaapiclient26.dto.AutorDTO;

@Service
public class AutorService extends AbstractApiCrudService<AutorDTO> {

    public AutorService(RestClient restClient, ApiProperties apiProperties) {
        super(restClient, apiProperties.getPaths().getAutores(), AutorDTO.class,
                new ParameterizedTypeReference<List<AutorDTO>>() {
                }, "autores");
    }
}