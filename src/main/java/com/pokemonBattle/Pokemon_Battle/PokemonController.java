package com.pokemonBattle.Pokemon_Battle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class PokemonController {

    private static final Logger logger = LoggerFactory.getLogger(PokemonController.class);

    @Resource
    private PokemonData data;

    private final Map<String, Pokemon> pokemonMap = new ConcurrentHashMap<>(); // Optimized for faster lookup

    /**
     * Load Pokémon data into memory once during startup.
     */
    @PostConstruct
    private void loadPokemonData() {
        try {
            List<String> pokemonData = Files.readAllLines(data.getFile().toPath());

            // Skip CSV headers and load Pokémon into a map
            pokemonData.stream()
                    .skip(1) // Skip headers
                    .map(this::parsePokemon) // Parse each line
                    .filter(Objects::nonNull) // Discard null results in case of invalid data
                    .forEach(pokemon -> pokemonMap.put(pokemon.getName().toLowerCase(), pokemon));

            logger.info("Loaded {} Pokémon.", pokemonMap.size());
        } catch (IOException e) {
            logger.error("Error loading Pokémon data: {}", e.getMessage());
        }
    }

    /**
     * Parse a line of CSV into a Pokémon object, with proper error handling.
     */
    private Pokemon parsePokemon(String line) {
        try {
            String[] split = line.split(",");
            return new Pokemon(
                    split[1].trim(), // Name
                    split[2].trim(), // Type
                    Integer.parseInt(split[4]), // Attack
                    Integer.parseInt(split[5]), // Defense
                    Integer.parseInt(split[6]), // Speed
                    Integer.parseInt(split[9])  // HitPoints
            );
        } catch (Exception e) {
            logger.error("Error parsing Pokémon data: {}", line, e);
            return null; // Ignore invalid lines
        }
    }

    /**
     * Fetch a list of all available Pokémon.
     */
    @GetMapping("pokemon/data")
    public List<Pokemon> getPokemonData() {
        return new ArrayList<>(pokemonMap.values());
    }

    /**
     * Fight simulation between two Pokémon.
     */
    @GetMapping("attack")
    public Map<String, Object> attack(String pokemonA, String pokemonB) {
        logger.info("Requested fight between pokemonA: {} and pokemonB: {}", pokemonA, pokemonB);

        // Validate inputs
        if (pokemonA == null || pokemonB == null) {
            return Map.of("Error", "Both Pokémon must be specified.");
        }

        Pokemon p1 = pokemonMap.get(pokemonA.toLowerCase());
        Pokemon p2 = pokemonMap.get(pokemonB.toLowerCase());

        // Validate Pokémon existence
        if (p1 == null || p2 == null) {
            return Map.of("Error", "One or both Pokémon not found.");
        }

        // Perform the fight (make copies of the Pokémon to avoid mutating originals)
        return fight(new Pokemon(p1), new Pokemon(p2));
    }

    private Map<String, Object> fight(Pokemon p1, Pokemon p2) {
        // Detect self-battle
        if (p1.getName().equalsIgnoreCase(p2.getName())) {
            return Map.of(
                    "Winner", p1.getName(),
                    "Remaining_HitPoints", 0 // In a self-battle, the Pokémon faints
            );
        }

        Pokemon first = p1.getSpeed() >= p2.getSpeed() ? p1 : p2;
        Pokemon second = p1 == first ? p2 : p1;

        while (first.getHitPoints() > 0 && second.getHitPoints() > 0) {
            int damage = calculateDamage(first, second);
            second.setHitPoints(second.getHitPoints() - damage);

            if (second.getHitPoints() <= 0) {
                break; // End the fight
            }

            // Reverse roles
            damage = calculateDamage(second, first);
            first.setHitPoints(first.getHitPoints() - damage);
        }

        Pokemon winner = p1.getHitPoints() > 0 ? p1 : p2;

        return Map.of(
                "Winner", winner.getName(),
                "Remaining_HitPoints", winner.getHitPoints()
        );
    }

    private int calculateDamage(Pokemon attacker, Pokemon defender) {
        double effectiveness = pokemonAttribute.getPokemonAttribute(attacker.getType(), defender.getType());
        return (int) (50 * ((double) attacker.getAttack() / (double) defender.getDefense() * effectiveness));
    }
}