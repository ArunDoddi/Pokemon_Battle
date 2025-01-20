package com.pokemonBattle.Pokemon_Battle;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.util.Map;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PokemonControllerTest {

    private final TestRestTemplate rest;

    PokemonControllerTest(@LocalServerPort int port) {
        rest = new TestRestTemplate(new RestTemplateBuilder().rootUri(format("http://localhost:%d", port)));
    }

    @Test
    void testAttackPicksWinnerWithHitPoints() {
        Map<String, Object> response = rest.getForObject("/attack", Map.class);
        assertEquals(1, response.size());
        assertEquals(null, response.get("Winner"));
        assertEquals(null, response.get("Remaining_HitPoints"));
    }

    @Test
    void testAttackWithInvalidPokemonName() {
        Map<String, Object> response = rest.getForObject("/attack?pokemonA=InvalidName&pokemonB=Bulbasaur", Map.class);
        assertEquals("One or both Pokémon not found.", response.get("Error"));
    }

    @Test
    void testAttackWithValidPokemons() {
        Map<String, Object> response = rest.getForObject("/attack?pokemonA=Pikachu&pokemonB=Bellossom", Map.class);
        assertEquals(2, response.size());
        assertEquals("Bellossom", response.get("Winner")); // Adjust expected winner based on data
        assertEquals(61, response.get("Remaining_HitPoints"));    // Adjust expected hit points based on data
    }

    @Test
    void testAttackWithSamePokemon() {
        Map<String, Object> response = rest.getForObject("/attack?pokemonA=Pikachu&pokemonB=Pikachu", Map.class);
        assertEquals("Pikachu", response.get("Winner"));  // Should return Pikachu as winner
        assertEquals(0, response.get("Remaining_HitPoints"));       // Winner's HP should be 0 after self-battle
    }

    @Test
    void testAttackWithCaseInsensitiveNames() {
        Map<String, Object> response = rest.getForObject("/attack?pokemonA=pIkAcHu&pokemonB=BuLbAsAuR", Map.class);
        assertEquals("Bulbasaur", response.get("Winner")); // Case-insensitive match
    }

    @Test
    void testAttackWithTypeAdvantage() {
        Map<String, Object> response = rest.getForObject("/attack?pokemonA=Squirtle&pokemonB=Charmander", Map.class);
        assertEquals("Squirtle", response.get("Winner")); // Water is strong against Fire
    }

}
