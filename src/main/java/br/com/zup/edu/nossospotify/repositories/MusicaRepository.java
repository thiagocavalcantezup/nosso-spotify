package br.com.zup.edu.nossospotify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.edu.nossospotify.models.Musica;

public interface MusicaRepository extends JpaRepository<Musica, Long> {

}
