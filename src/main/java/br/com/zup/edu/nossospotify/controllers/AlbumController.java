package br.com.zup.edu.nossospotify.controllers;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.edu.nossospotify.models.Album;
import br.com.zup.edu.nossospotify.models.AlbumDTO;
import br.com.zup.edu.nossospotify.models.Artista;
import br.com.zup.edu.nossospotify.repositories.AlbumRepository;
import br.com.zup.edu.nossospotify.repositories.ArtistaRepository;

@RestController
public class AlbumController {

    public final static String BASE_URI = "/albuns";

    private final AlbumRepository albumRepository;
    private final ArtistaRepository artistaRepository;

    public AlbumController(AlbumRepository albumRepository, ArtistaRepository artistaRepository) {
        this.albumRepository = albumRepository;
        this.artistaRepository = artistaRepository;
    }

    @Transactional
    @PostMapping(ArtistaController.BASE_URI + "/{artistaId}" + BASE_URI)
    public ResponseEntity<?> cadastrar(@PathVariable Long artistaId,
                                       @RequestBody @Valid AlbumDTO albumDTO,
                                       UriComponentsBuilder ucb) {
        Artista artista = artistaRepository.findById(artistaId)
                                           .orElseThrow(
                                               () -> new ResponseStatusException(
                                                   HttpStatus.NOT_FOUND,
                                                   "Não existe um artista com o id informado."
                                               )
                                           );

        Album album = albumDTO.toModel(artista);
        album = albumRepository.save(album);
        artistaRepository.save(artista);

        URI location = ucb.path(ArtistaController.BASE_URI + "/{artistaId}" + BASE_URI + "/{id}")
                          .buildAndExpand(artistaId, album.getId())
                          .toUri();

        return ResponseEntity.created(location).build();
    }

    @Transactional
    @DeleteMapping(BASE_URI + "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Album album = albumRepository.findById(id)
                                     .orElseThrow(
                                         () -> new ResponseStatusException(
                                             HttpStatus.NOT_FOUND,
                                             "Não existe um album com o id informado."
                                         )
                                     );

        albumRepository.delete(album);

        return ResponseEntity.noContent().build();
    }

}
