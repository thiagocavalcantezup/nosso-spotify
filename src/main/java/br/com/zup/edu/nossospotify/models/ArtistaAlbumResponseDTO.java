package br.com.zup.edu.nossospotify.models;

public class ArtistaAlbumResponseDTO {

    private String nome;

    public ArtistaAlbumResponseDTO() {}

    public ArtistaAlbumResponseDTO(Album album) {
        this.nome = album.getNome();
    }

    public String getNome() {
        return nome;
    }

}
