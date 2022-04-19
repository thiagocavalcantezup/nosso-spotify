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

import br.com.zup.edu.nossospotify.exceptions.ParticipanteNaoEncontradoException;

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
    @JoinTable(name = "musica_participante", joinColumns = @JoinColumn(name = "musica_id"), inverseJoinColumns = @JoinColumn(name = "artista_id"))
    private Set<Artista> participantes = new HashSet<>();

    @ManyToOne
    private Album album;

    /**
     * @deprecated Construtor de uso exclusivo do Hibernate
     */
    @Deprecated
    public Musica() {}

    public Musica(String nome) {
        this.nome = nome;
    }

    public void adicionar(Set<Artista> artistasParticipantes) {
        artistasParticipantes.forEach(artista -> artista.getParticipacoes().add(this));
        this.participantes.addAll(artistasParticipantes);
    }

    public void adicionar(Artista artistaParticipante) {
        artistaParticipante.getParticipacoes().add(this);
        this.participantes.add(artistaParticipante);
    }

    public void remover(Artista artistaParticipante) {
        if (!this.participantes.remove(artistaParticipante)) {
            throw new ParticipanteNaoEncontradoException(
                "Não existe um participante com o id fornecido."
            );
        }
        artistaParticipante.getParticipacoes().remove(this);
    }

    public Long getId() {
        return id;
    }

    public void setDono(Artista dono) {
        this.dono = dono;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getNome() {
        return nome;
    }

    public Artista getDono() {
        return dono;
    }

    public Album getAlbum() {
        return album;
    }

}
