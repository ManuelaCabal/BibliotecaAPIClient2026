package com.biblioteca.bibliotecaapiclient26.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.biblioteca.bibliotecaapiclient26.config.ApiProperties;
import com.biblioteca.bibliotecaapiclient26.dto.SerieDTO;

@Service
public class SerieService extends AbstractApiCrudService<SerieDTO> {

    public SerieService(RestClient restClient, ApiProperties apiProperties) {
        super(restClient, apiProperties.getPaths().getSeries(), SerieDTO.class,
                new ParameterizedTypeReference<List<SerieDTO>>() {
                }, "series");
    }
}
