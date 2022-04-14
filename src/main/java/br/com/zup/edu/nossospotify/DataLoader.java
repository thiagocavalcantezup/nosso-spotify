package br.com.zup.edu.nossospotify;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.zup.edu.nossospotify.models.Album;
import br.com.zup.edu.nossospotify.models.Artista;
import br.com.zup.edu.nossospotify.models.Musica;
import br.com.zup.edu.nossospotify.repositories.AlbumRepository;
import br.com.zup.edu.nossospotify.repositories.ArtistaRepository;
import br.com.zup.edu.nossospotify.repositories.MusicaRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final ArtistaRepository artistaRepository;
    private final AlbumRepository albumRepository;
    private final MusicaRepository musicaRepository;

    public DataLoader(ArtistaRepository artistaRepository, AlbumRepository albumRepository,
                      MusicaRepository musicaRepository) {
        this.artistaRepository = artistaRepository;
        this.albumRepository = albumRepository;
        this.musicaRepository = musicaRepository;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        Artista artistaThiago = new Artista("Thiago", "Recife", "PE");
        Artista artistaJose = new Artista("José", "Teresina", "PI");
        Artista artistaAlen = new Artista("Alen", "São Luis", "MA");

        artistaThiago = artistaRepository.save(artistaThiago);
        artistaJose = artistaRepository.save(artistaJose);
        artistaAlen = artistaRepository.save(artistaAlen);

        Album wongsCafe = new Album("Wong's Cafe");
        Album cracktheSkye = new Album("Crack the Skye");
        Album thePaisleyParkSession = new Album("The Paisley Park Session");

        artistaThiago.adicionar(wongsCafe);
        artistaJose.adicionar(cracktheSkye);
        artistaAlen.adicionar(thePaisleyParkSession);

        wongsCafe = albumRepository.save(wongsCafe);
        cracktheSkye = albumRepository.save(cracktheSkye);
        thePaisleyParkSession = albumRepository.save(thePaisleyParkSession);

        artistaThiago = artistaRepository.save(artistaThiago);
        artistaJose = artistaRepository.save(artistaJose);
        artistaAlen = artistaRepository.save(artistaAlen);

        Musica smokeshow = new Musica("Smokeshow");
        Musica discoDeLune = new Musica("Disco De Lune");
        Musica oblivion = new Musica("Oblivion");
        Musica divinations = new Musica("Divinations");
        Musica welcome2Minneapolis = new Musica("Welcome 2 Minneapolis");
        Musica gumshu = new Musica("Gumshü");

        smokeshow.adicionar(Set.of(artistaThiago, artistaJose));
        discoDeLune.adicionar(Set.of(artistaThiago, artistaAlen));
        oblivion.adicionar(Set.of(artistaJose, artistaThiago));
        divinations.adicionar(Set.of(artistaJose, artistaAlen));
        welcome2Minneapolis.adicionar(Set.of(artistaAlen, artistaThiago));
        gumshu.adicionar(Set.of(artistaAlen, artistaJose));

        wongsCafe.adicionar(smokeshow);
        wongsCafe.adicionar(discoDeLune);
        cracktheSkye.adicionar(oblivion);
        cracktheSkye.adicionar(divinations);
        thePaisleyParkSession.adicionar(welcome2Minneapolis);
        thePaisleyParkSession.adicionar(gumshu);

        artistaThiago.adicionar(smokeshow);
        artistaThiago.adicionar(discoDeLune);
        artistaJose.adicionar(oblivion);
        artistaJose.adicionar(divinations);
        artistaAlen.adicionar(welcome2Minneapolis);
        artistaAlen.adicionar(gumshu);

        smokeshow = musicaRepository.save(smokeshow);
        discoDeLune = musicaRepository.save(discoDeLune);
        oblivion = musicaRepository.save(oblivion);
        divinations = musicaRepository.save(divinations);
        welcome2Minneapolis = musicaRepository.save(welcome2Minneapolis);
        gumshu = musicaRepository.save(gumshu);

        wongsCafe = albumRepository.save(wongsCafe);
        cracktheSkye = albumRepository.save(cracktheSkye);
        thePaisleyParkSession = albumRepository.save(thePaisleyParkSession);

        artistaThiago = artistaRepository.save(artistaThiago);
        artistaJose = artistaRepository.save(artistaJose);
        artistaAlen = artistaRepository.save(artistaAlen);
    }

}
