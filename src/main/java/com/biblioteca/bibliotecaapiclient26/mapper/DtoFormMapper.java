package com.biblioteca.bibliotecaapiclient26.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.biblioteca.bibliotecaapiclient26.dto.AutorDTO;
import com.biblioteca.bibliotecaapiclient26.dto.LibroDTO;
import com.biblioteca.bibliotecaapiclient26.dto.PaisDTO;
import com.biblioteca.bibliotecaapiclient26.dto.PremioDTO;
import com.biblioteca.bibliotecaapiclient26.dto.SerieDTO;
import com.biblioteca.bibliotecaapiclient26.web.form.AutorForm;
import com.biblioteca.bibliotecaapiclient26.web.form.LibroForm;
import com.biblioteca.bibliotecaapiclient26.web.form.PaisForm;
import com.biblioteca.bibliotecaapiclient26.web.form.PremioForm;

@Component
public class DtoFormMapper {

    public AutorForm toAutorForm(AutorDTO dto) {
        AutorForm form = new AutorForm();
        form.setId(dto.getId());
        form.setNomAutor(dto.getNomAutor());
        form.setApellido1(dto.getApellido1());
        form.setApellido2(dto.getApellido2());
        form.setFechaNacimiento(dto.getFechaNacimiento());
        form.setFechaDefuncion(dto.getFechaDefuncion());
        form.setFoto(dto.getFoto());
        form.setLocalidad(dto.getLocalidad());
        form.setBiografia(dto.getBiografia());
        if (dto.getPais() != null) {
            form.setPaisId(dto.getPais().getId());
        }
        if (dto.getPremios() != null) {
            form.setPremioIds(dto.getPremios().stream()
                    .map(PremioDTO::getId)
                    .filter(Objects::nonNull)
                    .toList());
        }
        return form;
    }

    public AutorDTO toAutorDto(AutorForm form, PaisDTO pais, List<PremioDTO> premios) {
        AutorDTO dto = new AutorDTO();
        dto.setId(form.getId());
        dto.setNomAutor(form.getNomAutor());
        dto.setApellido1(form.getApellido1());
        dto.setApellido2(form.getApellido2());
        dto.setFechaNacimiento(form.getFechaNacimiento());
        dto.setFechaDefuncion(form.getFechaDefuncion());
        dto.setPais(pais);
        dto.setFoto(form.getFoto());
        dto.setLocalidad(form.getLocalidad());
        dto.setBiografia(form.getBiografia());
        dto.setPremios(premios != null ? premios : new ArrayList<>());
        return dto;
    }

    public LibroForm toLibroForm(LibroDTO dto) {
        LibroForm form = new LibroForm();
        form.setId(dto.getId());
        form.setNomLibro(dto.getNomLibro());
        form.setAno(dto.getAno());
        form.setPortada(dto.getPortada());
        form.setNomArchivo(dto.getNomArchivo());
        form.setPelicula(dto.getPelicula());
        form.setComentario(dto.getComentario());
        form.setIndiceSerie(dto.getIndiceSerie());
        if (dto.getSerie() != null) {
            form.setSerieId(dto.getSerie().getId());
        }
        return form;
    }

    public LibroDTO toLibroDto(LibroForm form, SerieDTO serie) {
        LibroDTO dto = new LibroDTO();
        dto.setId(form.getId());
        dto.setNomLibro(form.getNomLibro());
        dto.setAno(form.getAno());
        dto.setPortada(form.getPortada());
        dto.setNomArchivo(form.getNomArchivo());
        dto.setPelicula(form.getPelicula());
        dto.setComentario(form.getComentario());
        dto.setSerie(serie);
        dto.setIndiceSerie(form.getIndiceSerie());
        return dto;
    }

    public PaisForm toPaisForm(PaisDTO dto) {
        PaisForm form = new PaisForm();
        form.setId(dto.getId());
        form.setNomPais(dto.getNomPais());
        form.setBandera(dto.getBandera());
        return form;
    }

    public PaisDTO toPaisDto(PaisForm form) {
        PaisDTO dto = new PaisDTO();
        dto.setId(form.getId());
        dto.setNomPais(form.getNomPais());
        dto.setBandera(form.getBandera());
        return dto;
    }

    public PremioForm toPremioForm(PremioDTO dto) {
        PremioForm form = new PremioForm();
        form.setId(dto.getId());
        form.setNomPremio(dto.getNomPremio());
        form.setTipo(dto.getTipo());
        form.setLogotipo(dto.getLogotipo());
        form.setAnyo(dto.getAnyo());
        form.setLocalidad(dto.getLocalidad());
        if (dto.getPais() != null) {
            form.setPaisId(dto.getPais().getId());
        }
        return form;
    }

    public PremioDTO toPremioDto(PremioForm form, PaisDTO pais) {
        PremioDTO dto = new PremioDTO();
        dto.setId(form.getId());
        dto.setNomPremio(form.getNomPremio());
        dto.setPais(pais);
        dto.setTipo(form.getTipo());
        dto.setLogotipo(form.getLogotipo());
        dto.setAnyo(form.getAnyo());
        dto.setLocalidad(form.getLocalidad());
        return dto;
    }
}
