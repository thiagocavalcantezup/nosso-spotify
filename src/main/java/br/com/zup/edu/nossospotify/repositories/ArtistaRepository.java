package br.com.zup.edu.nossospotify.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.zup.edu.nossospotify.models.Artista;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {

    @Query("SELECT a FROM Artista a JOIN FETCH a.albuns JOIN FETCH a.musicas WHERE a.id = :id")
    Optional<Artista> findWithAlbunsAndMusicasById(Long id);

}
