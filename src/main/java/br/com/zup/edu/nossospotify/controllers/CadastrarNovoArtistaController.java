package br.com.zup.edu.nossospotify.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.edu.nossospotify.models.Artista;
import br.com.zup.edu.nossospotify.models.ArtistaRequest;
import br.com.zup.edu.nossospotify.repositories.ArtistaRepository;

@RestController
public class CadastrarNovoArtistaController {

    private final ArtistaRepository repository;

    public CadastrarNovoArtistaController(ArtistaRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/artistas")
    public ResponseEntity<?> cadastrar(@RequestBody @Valid ArtistaRequest request,
                                       UriComponentsBuilder uriComponentsBuilder) {

        Artista novoArtista = request.paraArtista();

        repository.save(novoArtista);

        URI location = uriComponentsBuilder.path("/artistas/{id}")
                                           .buildAndExpand(novoArtista.getId())
                                           .toUri();

        return ResponseEntity.created(location).build();
    }

}
