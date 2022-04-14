package br.com.zup.edu.nossospotify.models;

import javax.validation.constraints.NotBlank;

public class ArtistaPatchDTO {

    @NotBlank
    private String nome;

    public ArtistaPatchDTO() {}

    public ArtistaPatchDTO(@NotBlank String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

}
