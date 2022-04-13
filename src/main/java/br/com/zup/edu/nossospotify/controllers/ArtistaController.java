package br.com.zup.edu.nossospotify.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.edu.nossospotify.models.Artista;
import br.com.zup.edu.nossospotify.models.ArtistaDTO;
import br.com.zup.edu.nossospotify.repositories.ArtistaRepository;

@RestController
@RequestMapping(ArtistaController.BASE_URI)
public class ArtistaController {

    public final static String BASE_URI = "/artistas";

    private final ArtistaRepository artistaRepository;

    public ArtistaController(ArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid ArtistaDTO artistaDTO,
                                       UriComponentsBuilder ucb) {
        Artista artista = artistaRepository.save(artistaDTO.toModel());

        URI location = ucb.path(BASE_URI + "/{id}").buildAndExpand(artista.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

}
