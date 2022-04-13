package br.com.zup.edu.nossospotify.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.edu.nossospotify.models.Artista;
import br.com.zup.edu.nossospotify.models.Musica;
import br.com.zup.edu.nossospotify.models.MusicaDTO;
import br.com.zup.edu.nossospotify.repositories.ArtistaRepository;
import br.com.zup.edu.nossospotify.repositories.MusicaRepository;

@RestController
@RequestMapping(ArtistaController.BASE_URI + "/{artistaId}" + MusicaController.BASE_URI)
public class MusicaController {

    public final static String BASE_URI = "/musicas";

    private final MusicaRepository musicaRepository;
    private final ArtistaRepository artistaRepository;

    public MusicaController(MusicaRepository musicaRepository,
                            ArtistaRepository artistaRepository) {
        this.musicaRepository = musicaRepository;
        this.artistaRepository = artistaRepository;
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@PathVariable Long artistaId,
                                       @RequestBody @Valid MusicaDTO musicaDTO,
                                       UriComponentsBuilder ucb) {
        Artista artista = artistaRepository.findById(artistaId)
                                           .orElseThrow(
                                               () -> new ResponseStatusException(
                                                   HttpStatus.NOT_FOUND,
                                                   "Não existe um artista com o id informado."
                                               )
                                           );

        Musica musica = musicaRepository.save(musicaDTO.toModel(artista));

        URI location = ucb.path(ArtistaController.BASE_URI + "/{artistaId}" + BASE_URI + "/{id}")
                          .buildAndExpand(artistaId, musica.getId())
                          .toUri();

        return ResponseEntity.created(location).build();

    }

}
