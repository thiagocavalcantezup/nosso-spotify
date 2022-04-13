package br.com.zup.edu.nossospotify.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "musicas")
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne(optional = false)
    private Artista dono;

    @ManyToMany
    @JoinTable(name = "participante_musica", joinColumns = @JoinColumn(name = "musica_id"), inverseJoinColumns = @JoinColumn(name = "artista_id"))
    private Set<Artista> participantes = new HashSet<>();

    @ManyToOne
    private Album album;

    /**
     * @deprecated Construtor de uso exclusivo do Hibernate
     */
    @Deprecated
    public Musica() {}

    public Musica(String nome, Artista dono) {
        this.nome = nome;
        this.dono = dono;
    }

    public void adicionar(Set<Artista> artistasParticipantes) {
        this.participantes.addAll(artistasParticipantes);
        artistasParticipantes.forEach(artista -> artista.participou(this));
    }

    public void adicionar(Album album) {
        this.album = album;
    }

    public Long getId() {
        return id;
    }

}
