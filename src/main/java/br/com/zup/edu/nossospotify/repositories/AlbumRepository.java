package br.com.zup.edu.nossospotify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.edu.nossospotify.models.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {

}
