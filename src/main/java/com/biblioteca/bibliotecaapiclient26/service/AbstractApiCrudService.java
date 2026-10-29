package com.biblioteca.bibliotecaapiclient26.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

public abstract class AbstractApiCrudService<T> {

    private final RestClient restClient;
    private final String endpoint;
    private final Class<T> dtoClass;
    private final ParameterizedTypeReference<List<T>> listTypeReference;
    private final String resourceLabel;

    protected AbstractApiCrudService(
            RestClient restClient,
            String endpoint,
            Class<T> dtoClass,
            ParameterizedTypeReference<List<T>> listTypeReference,
            String resourceLabel) {
        this.restClient = restClient;
        this.endpoint = endpoint;
        this.dtoClass = dtoClass;
        this.listTypeReference = listTypeReference;
        this.resourceLabel = resourceLabel;
    }

    public List<T> findAll() {
        try {
            List<T> response = restClient.get()
                    .uri(endpoint)
                    .retrieve()
                    .body(listTypeReference);
            return response != null ? response : Collections.emptyList();
        } catch (RestClientException exception) {
            throw new ApiClientException("No se pudieron cargar los datos de " + resourceLabel + ".", exception);
        }
    }

    public T findById(Integer id) {
        requireId(id);
        try {
            T response = restClient.get()
                    .uri(endpoint + "/{id}", id)
                    .retrieve()
                    .body(dtoClass);
            if (response == null) {
                throw new ApiClientException("No se encontró el registro solicitado de " + resourceLabel + ".", null);
            }
            return response;
        } catch (RestClientException exception) {
            throw new ApiClientException("No se pudo recuperar el detalle de " + resourceLabel + ".", exception);
        }
    }

    public T create(T body) {
        try {
            return restClient.post()
                    .uri(endpoint)
                    .body(body)
                    .retrieve()
                    .body(dtoClass);
        } catch (RestClientException exception) {
            throw new ApiClientException("No se pudo crear el registro de " + resourceLabel + ".", exception);
        }
    }

    public T update(Integer id, T body) {
        requireId(id);
        try {
            return restClient.put()
                    .uri(endpoint + "/{id}", id)
                    .body(body)
                    .retrieve()
                    .body(dtoClass);
        } catch (RestClientException exception) {
            throw new ApiClientException("No se pudo actualizar el registro de " + resourceLabel + ".", exception);
        }
    }

    public void delete(Integer id) {
        requireId(id);
        try {
            restClient.delete()
                    .uri(endpoint + "/{id}", id)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientException exception) {
            throw new ApiClientException("No se pudo eliminar el registro de " + resourceLabel + ".", exception);
        }
    }

    public T findByIdOrNull(Integer id) {
        return id == null ? null : findById(id);
    }

    public List<T> findByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        return ids.stream()
                .filter(Objects::nonNull)
                .distinct()
                .map(this::findById)
                .toList();
    }

    private void requireId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El identificador es obligatorio.");
        }
    }
}
