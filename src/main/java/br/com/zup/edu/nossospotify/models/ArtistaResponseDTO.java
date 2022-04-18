package br.com.zup.edu.nossospotify.models;

import java.util.Set;
import java.util.stream.Collectors;

public class ArtistaResponseDTO {

    private String nome;
    private Set<ArtistaAlbumResponseDTO> albuns;
    private Set<ArtistaMusicaResponseDTO> musicas;

    public ArtistaResponseDTO() {}

    public ArtistaResponseDTO(Artista artista) {
        this.nome = artista.getNome();
        this.albuns = artista.getAlbuns()
                             .stream()
                             .map(ArtistaAlbumResponseDTO::new)
                             .collect(Collectors.toSet());
        this.musicas = artista.getMusicas()
                              .stream()
                              .map(ArtistaMusicaResponseDTO::new)
                              .collect(Collectors.toSet());
    }

    public String getNome() {
        return nome;
    }

    public Set<ArtistaAlbumResponseDTO> getAlbuns() {
        return albuns;
    }

    public Set<ArtistaMusicaResponseDTO> getMusicas() {
        return musicas;
    }

}
