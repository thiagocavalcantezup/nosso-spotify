package br.com.zup.edu.nossospotify.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.edu.nossospotify.models.Album;
import br.com.zup.edu.nossospotify.models.AlbumRequest;
import br.com.zup.edu.nossospotify.models.Artista;
import br.com.zup.edu.nossospotify.repositories.AlbumRepository;
import br.com.zup.edu.nossospotify.repositories.ArtistaRepository;

@RestController
public class CadastrarNovoAlbumController {

    private final AlbumRepository repository;
    private final ArtistaRepository artistaRepository;

    public CadastrarNovoAlbumController(AlbumRepository repository,
                                        ArtistaRepository artistaRepository) {
        this.repository = repository;
        this.artistaRepository = artistaRepository;
    }

    @PostMapping("/artista/{id}/albuns")
    public ResponseEntity<?> cadastrar(@RequestBody @Valid AlbumRequest request,
                                       @PathVariable Long id,
                                       UriComponentsBuilder uriComponentsBuilder) {

        Artista artista = artistaRepository.findById(id)
                                           .orElseThrow(
                                               () -> new ResponseStatusException(
                                                   HttpStatus.NOT_FOUND,
                                                   "Artista não cadastrado no sistema."
                                               )
                                           );

        Album novoAlbum = request.paraAlbum(artista);

        repository.save(novoAlbum);

        URI location = uriComponentsBuilder.path("/artista/{id}/albuns/{idAlbum}")
                                           .buildAndExpand(artista.getId(), novoAlbum.getId())
                                           .toUri();

        return ResponseEntity.created(location).build();

    }

}
