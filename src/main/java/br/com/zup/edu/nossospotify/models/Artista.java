package br.com.zup.edu.nossospotify.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

    @ManyToMany(mappedBy = "participantes")
    private Set<Musica> participacoes = new HashSet<>();

    @OneToMany(mappedBy = "dono")
    private Set<Album> albuns = new HashSet<>();

    @OneToMany(mappedBy = "dono")
    private Set<Musica> musicas = new HashSet<>();

    public Artista(String nome, String cidade, String estado) {
        this.nome = nome;
        this.cidade = cidade;
        this.estado = estado;
    }

    /**
     * @deprecated construtor para uso exclusivo do Hibernate
     */
    @Deprecated
    public Artista() {}

    public Long getId() {
        return this.id;
    }

    public void participou(Musica musica) {
        this.participacoes.add(musica);
    }

    public void adicionar(Musica musica) {
        this.musicas.add(musica);
    }

    public void adicionar(Album album) {
        this.albuns.add(album);
    }

}
