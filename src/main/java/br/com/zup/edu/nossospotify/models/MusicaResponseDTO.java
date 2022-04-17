package br.com.zup.edu.nossospotify.models;

public class MusicaResponseDTO {

    private String nome;
    private String dono;

    public MusicaResponseDTO() {}

    public MusicaResponseDTO(Musica musica) {
        this.nome = musica.getNome();
        this.dono = musica.getDono().getNome();
    }

    public String getNome() {
        return nome;
    }

    public String getDono() {
        return dono;
    }

}
