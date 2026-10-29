package com.biblioteca.bibliotecaapiclient26.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.biblioteca.bibliotecaapiclient26.config.ApiProperties;
import com.biblioteca.bibliotecaapiclient26.dto.PremioDTO;

@Service
public class PremioService extends AbstractApiCrudService<PremioDTO> {

    public PremioService(RestClient restClient, ApiProperties apiProperties) {
        super(restClient, apiProperties.getPaths().getPremios(), PremioDTO.class,
                new ParameterizedTypeReference<List<PremioDTO>>() {
                }, "premios");
    }
}
