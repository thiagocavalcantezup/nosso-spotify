package br.com.zup.edu.nossospotify.musica;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MusicaRepository extends JpaRepository<Musica, Long> {

    @Query("SELECT m FROM Musica m JOIN FETCH m.participantes WHERE m.id = :id")
    Optional<Musica> findByIdAndParticpantes(Long id);

}
