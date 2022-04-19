package br.com.zup.edu.nossospotify.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.edu.nossospotify.repositories.ArtistaRepository;

public class AlbumMusicaDTO {

    @NotBlank
    private String nome;

    @NotNull
    @Positive
    private Long donoId;

    public AlbumMusicaDTO() {}

    public AlbumMusicaDTO(@NotBlank String nome, @NotNull @Positive Long donoId) {
        this.nome = nome;
        this.donoId = donoId;
    }

    public Musica toModel(ArtistaRepository artistaRepository) {
        Artista artista = artistaRepository.findById(donoId)
                                           .orElseThrow(
                                               () -> new ResponseStatusException(
                                                   HttpStatus.NOT_FOUND,
                                                   "Não existe um artista com o id informado."
                                               )
                                           );

        Musica musica = new Musica(nome);
        artista.adicionar(musica);

        return musica;
    }

    public String getNome() {
        return nome;
    }

    public Long getDonoId() {
        return donoId;
    }

}
