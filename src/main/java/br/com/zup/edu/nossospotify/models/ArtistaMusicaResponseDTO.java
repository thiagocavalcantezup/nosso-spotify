package br.com.zup.edu.nossospotify.models;

public class ArtistaMusicaResponseDTO {

    private String nome;
    private String album;

    public ArtistaMusicaResponseDTO() {}

    public ArtistaMusicaResponseDTO(Musica musica) {
        this.nome = musica.getNome();
        this.album = musica.getAlbum().getNome();
    }

    public String getNome() {
        return nome;
    }

    public String getAlbum() {
        return album;
    }

}
