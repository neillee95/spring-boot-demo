package com.example.springbootdemo.servletweb.player;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@AutoConfigureMockMvc
class PlayerControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private PlayerService playerService;

    @BeforeAll
    public static void init(@Value("${web.use-custom-response:false}") boolean useCustomResponse) {
        if (useCustomResponse) {
            throw new RuntimeException("set 'web.use-custom-response=false'");
        }
    }

    @BeforeEach
    public void create() throws Exception {
        Player player = new Player();
        player.setName("Tom");
        player.setAge((short) 25);

        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/players")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsBytes(player))
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteTest() throws Exception {
        mockMvc
            .perform(MockMvcRequestBuilders.delete("/players/{1}", "2"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateTest() throws Exception {
        Player player = new Player();
        player.setName("Tom");
        player.setAge((short) 24);

        mockMvc
            .perform(
                MockMvcRequestBuilders.put("/players")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsBytes(player))
            )
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void findByIdTest() throws Exception {
        Mockito.when(playerService.find("1")).thenReturn(new Player("1", "Tom", (short) 25));

        mockMvc
            .perform(MockMvcRequestBuilders.get("/players/{1}", "1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Tom"))
            );
    }

    @Test
    void findAllTest() throws Exception {
        AtomicInteger counter = new AtomicInteger(0);

        Collection<Player> mockPlayers = Stream
            .iterate(
                new Player(String.valueOf(counter.incrementAndGet()), "Tom - " + counter.get(), (short) 21),
                it -> true,
                (it) -> new Player(
                    String.valueOf(counter.incrementAndGet()),
                    "Tom - " + counter.get(),
                    (short) (counter.get() + 20)
                )
            )
            .limit(10)
            .collect(Collectors.toList());

        Mockito.when(playerService.findAll()).thenReturn(mockPlayers);

        mockMvc
            .perform(MockMvcRequestBuilders.get("/players"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.jsonPath("$.[7].name", Matchers.is("Tom - 8")));
    }

}
