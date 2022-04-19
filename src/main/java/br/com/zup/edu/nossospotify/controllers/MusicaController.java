package br.com.zup.edu.nossospotify.controllers;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.edu.nossospotify.models.Album;
import br.com.zup.edu.nossospotify.models.AlbumMusicaDTO;
import br.com.zup.edu.nossospotify.models.Artista;
import br.com.zup.edu.nossospotify.models.Musica;
import br.com.zup.edu.nossospotify.models.MusicaDTO;
import br.com.zup.edu.nossospotify.models.MusicaResponseDTO;
import br.com.zup.edu.nossospotify.repositories.AlbumRepository;
import br.com.zup.edu.nossospotify.repositories.ArtistaRepository;
import br.com.zup.edu.nossospotify.repositories.MusicaRepository;

@RestController
@RequestMapping
public class MusicaController {

    public final static String BASE_URI = "/musicas";

    private final MusicaRepository musicaRepository;
    private final ArtistaRepository artistaRepository;
    private final AlbumRepository albumRepository;

    public MusicaController(MusicaRepository musicaRepository, ArtistaRepository artistaRepository,
                            AlbumRepository albumRepository) {
        this.musicaRepository = musicaRepository;
        this.artistaRepository = artistaRepository;
        this.albumRepository = albumRepository;
    }

    @PostMapping(ArtistaController.BASE_URI + "/{artistaId}" + BASE_URI)
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

        Musica musica = musicaDTO.toModel(artista);
        musica = musicaRepository.save(musica);
        artistaRepository.save(artista);

        URI location = ucb.path(ArtistaController.BASE_URI + "/{artistaId}" + BASE_URI + "/{id}")
                          .buildAndExpand(artistaId, musica.getId())
                          .toUri();

        return ResponseEntity.created(location).build();

    }

    @GetMapping(BASE_URI + "/{id}")
    public ResponseEntity<MusicaResponseDTO> show(@PathVariable Long id) {
        Musica musica = musicaRepository.findById(id)
                                        .orElseThrow(
                                            () -> new ResponseStatusException(
                                                HttpStatus.NOT_FOUND,
                                                "Não existe uma música com o id informado."
                                            )
                                        );

        return ResponseEntity.ok(new MusicaResponseDTO(musica));
    }

    @Transactional
    @PostMapping(AlbumController.BASE_URI + "/{albumId}" + BASE_URI)
    public ResponseEntity<Void> albumCreate(@PathVariable Long albumId,
                                            @RequestBody @Valid AlbumMusicaDTO albumMusicaDTO,
                                            UriComponentsBuilder ucb) {
        Album album = albumRepository.findById(albumId)
                                     .orElseThrow(
                                         () -> new ResponseStatusException(
                                             HttpStatus.NOT_FOUND,
                                             "Não existe um álbum com o id informado."
                                         )
                                     );

        Musica musica = albumMusicaDTO.toModel(artistaRepository);
        album.adicionar(musica);

        albumRepository.flush();

        URI location = ucb.path(AlbumController.BASE_URI + "/{albumId}" + BASE_URI + "/{id}")
                          .buildAndExpand(albumId, musica.getId())
                          .toUri();

        return ResponseEntity.created(location).build();
    }

    @Transactional
    @DeleteMapping(BASE_URI + "/{musicaId}" + ArtistaController.BASE_URI + "/{artistaId}")
    public ResponseEntity<Void> deleteParticipante(@PathVariable Long musicaId,
                                                   @PathVariable Long artistaId) {
        Musica musica = musicaRepository.findById(musicaId)
                                        .orElseThrow(
                                            () -> new ResponseStatusException(
                                                HttpStatus.NOT_FOUND,
                                                "Não existe uma música com o id informado."
                                            )
                                        );
        Artista participante = artistaRepository.findById(artistaId)
                                                .orElseThrow(
                                                    () -> new ResponseStatusException(
                                                        HttpStatus.NOT_FOUND,
                                                        "Não existe um artista com o id informado."
                                                    )
                                                );

        musica.remover(participante);

        return ResponseEntity.noContent().build();
    }

}
