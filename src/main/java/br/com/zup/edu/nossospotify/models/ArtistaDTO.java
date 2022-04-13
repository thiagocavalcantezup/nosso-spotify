package br.com.zup.edu.nossospotify.models;

import javax.validation.constraints.NotBlank;

public class ArtistaDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String cidade;

    @NotBlank
    private String estado;

    public ArtistaDTO() {}

    public ArtistaDTO(String nome, String cidade, String estado) {
        this.nome = nome;
        this.cidade = cidade;
        this.estado = estado;
    }

    public Artista toModel() {
        return new Artista(nome, cidade, estado);
    }

    public String getNome() {
        return nome;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

}
