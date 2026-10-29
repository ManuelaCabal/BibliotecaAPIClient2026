package com.biblioteca.bibliotecaapiclient26.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.biblioteca.bibliotecaapiclient26.config.ApiProperties;
import com.biblioteca.bibliotecaapiclient26.dto.PaisDTO;

@Service
public class PaisService extends AbstractApiCrudService<PaisDTO> {

    public PaisService(RestClient restClient, ApiProperties apiProperties) {
        super(restClient, apiProperties.getPaths().getPaises(), PaisDTO.class,
                new ParameterizedTypeReference<List<PaisDTO>>() {
                }, "países");
    }
}
