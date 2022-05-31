package br.com.zup.edu.nossospotify.musica;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
public class DetalharMusicaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ArtistaRepository artistaRepository;

    @Autowired
    private MusicaRepository musicaRepository;

    @BeforeEach
    void setUp() {
        musicaRepository.deleteAll();
        artistaRepository.deleteAll();
    }

    @Test
    void deveDetalharUmaMusica() throws Exception {
        // cenário (given)
        //
        Artista coryWong = new Artista("Cory Wong", "Poughkeepsie", "NY");
        coryWong = artistaRepository.save(coryWong);

        Musica crisis = new Musica("Crisis", coryWong);
        crisis.adicionar(Set.of(coryWong));
        coryWong.adicionar(crisis);
        coryWong.participou(crisis);

        coryWong = artistaRepository.save(coryWong);
        crisis = musicaRepository.save(crisis);

        MockHttpServletRequestBuilder request = get("/musicas/{id}", crisis.getId()).contentType(
            APPLICATION_JSON
        );

        // ação (when) e corretude (then)
        //
        String responseString = mockMvc.perform(request)
                                       .andExpect(status().isOk())
                                       .andReturn()
                                       .getResponse()
                                       .getContentAsString(UTF_8);

        DetalharMusicaResponse response = objectMapper.readValue(
            responseString, DetalharMusicaResponse.class
        );

        assertThat(response).extracting("nome", "dono")
                            .contains(crisis.getNome(), crisis.getDono().getNome());
    }

    @Test
    void naoDeveDetalharUmaMusicaQueNaoEstaCadastrada() throws Exception {
        // cenário (given)
        //
        MockHttpServletRequestBuilder request = get("/musicas/{id}", Integer.MAX_VALUE).contentType(
            APPLICATION_JSON
        );

        // ação (when) e corretude (then)
        //
        Exception resolvedException = mockMvc.perform(request)
                                             .andExpect(status().isNotFound())
                                             .andReturn()
                                             .getResolvedException();

        assertNotNull(resolvedException);
        assertEquals(ResponseStatusException.class, resolvedException.getClass());
        assertEquals(
            "Musica nao cadastrada.", ((ResponseStatusException) resolvedException).getReason()
        );
    }

}
