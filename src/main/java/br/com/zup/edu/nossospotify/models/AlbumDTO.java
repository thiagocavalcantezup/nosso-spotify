package br.com.zup.edu.nossospotify.models;

import javax.validation.constraints.NotBlank;

public class AlbumDTO {

    @NotBlank
    private String nome;

    public AlbumDTO() {}

    public AlbumDTO(String nome) {
        this.nome = nome;
    }

    public Album toModel(Artista dono) {
        return new Album(nome, dono);
    }

    public String getNome() {
        return nome;
    }

}
