package br.com.zup.edu.nossospotify.controllers;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.edu.nossospotify.models.Artista;
import br.com.zup.edu.nossospotify.models.ArtistaDTO;
import br.com.zup.edu.nossospotify.models.ArtistaPatchDTO;
import br.com.zup.edu.nossospotify.models.ArtistaResponseDTO;
import br.com.zup.edu.nossospotify.repositories.ArtistaRepository;

@RestController
@RequestMapping(ArtistaController.BASE_URI)
public class ArtistaController {

    public final static String BASE_URI = "/artistas";

    private final ArtistaRepository artistaRepository;

    public ArtistaController(ArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid ArtistaDTO artistaDTO,
                                       UriComponentsBuilder ucb) {
        Artista artista = artistaRepository.save(artistaDTO.toModel());

        URI location = ucb.path(BASE_URI + "/{id}").buildAndExpand(artista.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<Void> patch(@PathVariable Long id,
                                      @RequestBody @Valid ArtistaPatchDTO artistaPatchDTO) {
        Artista artista = artistaRepository.findById(id)
                                           .orElseThrow(
                                               () -> new ResponseStatusException(
                                                   HttpStatus.NOT_FOUND,
                                                   "Não existe um artista com o id informado."
                                               )
                                           );

        artista.setNome(artistaPatchDTO.getNome());
        artistaRepository.save(artista);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistaResponseDTO> show(@PathVariable Long id) {
        Artista artista = artistaRepository.findWithAlbunsAndMusicasById(id)
                                           .orElseThrow(
                                               () -> new ResponseStatusException(
                                                   HttpStatus.NOT_FOUND,
                                                   "Não existe um artista com o id informado."
                                               )
                                           );

        return ResponseEntity.ok(new ArtistaResponseDTO(artista));
    }

}
