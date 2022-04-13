package br.com.zup.edu.nossospotify.models;

import javax.validation.constraints.NotBlank;

public class MusicaDTO {

    @NotBlank
    private String nome;

    public MusicaDTO() {}

    public MusicaDTO(String nome) {
        this.nome = nome;
    }

    public Musica toModel(Artista dono) {
        Musica musica = new Musica(nome, dono);
        dono.adicionar(musica);

        return musica;
    }

    public String getNome() {
        return nome;
    }

}
