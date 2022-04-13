package br.com.zup.edu.nossospotify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.edu.nossospotify.models.Artista;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {

}
