package br.com.zup.edu.nossospotify.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "albuns")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne(optional = false)
    private Artista dono;

    @OneToMany(mappedBy = "album", cascade = CascadeType.REMOVE)
    private Set<Musica> musicas = new HashSet<>();

    /**
     * @deprecated Construtor de uso exclusivo do Hibernate
     */
    @Deprecated
    public Album() {}

    public Album(String nome) {
        this.nome = nome;
    }

    public void adicionar(Musica musica) {
        musica.setAlbum(this);
        this.musicas.add(musica);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setDono(Artista dono) {
        this.dono = dono;
    }

}
