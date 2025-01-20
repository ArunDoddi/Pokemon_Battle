package com.pokemonBattle.Pokemon_Battle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PokemonAttributeTest {

    @Test
    void testFireVsGrass() {
        double effectiveness = pokemonAttribute.getPokemonAttribute("Fire", "Grass");
        assertEquals(2.0, effectiveness, "Fire should be super effective against Grass.");
    }

    @Test
    void testFireVsWater() {
        double effectiveness = pokemonAttribute.getPokemonAttribute("Fire", "Water");
        assertEquals(0.5, effectiveness, "Fire should be not very effective against Water.");
    }

    @Test
    void testFireVsElectric() {
        double effectiveness = pokemonAttribute.getPokemonAttribute("Fire", "Electric");
        assertEquals(1.0, effectiveness, "Fire should have normal effectiveness against Electric.");
    }

    @Test
    void testWaterVsFire() {
        double effectiveness = pokemonAttribute.getPokemonAttribute("Water", "Fire");
        assertEquals(2.0, effectiveness, "Water should be super effective against Fire.");
    }

    @Test
    void testWaterVsElectric() {
        double effectiveness = pokemonAttribute.getPokemonAttribute("Water", "Electric");
        assertEquals(0.5, effectiveness, "Water should be not very effective against Electric.");
    }

    @Test
    void testWaterVsGrass() {
        double effectiveness = pokemonAttribute.getPokemonAttribute("Water", "Grass");
        assertEquals(1.0, effectiveness, "Water should have normal effectiveness against Grass.");
    }

    @Test
    void testGrassVsElectric() {
        double effectiveness = pokemonAttribute.getPokemonAttribute("Grass", "Electric");
        assertEquals(2.0, effectiveness, "Grass should be super effective against Electric.");
    }

    @Test
    void testGrassVsFire() {
        double effectiveness = pokemonAttribute.getPokemonAttribute("Grass", "Fire");
        assertEquals(0.5, effectiveness, "Grass should be not very effective against Fire.");
    }

    @Test
    void testGrassVsWater() {
        double effectiveness = pokemonAttribute.getPokemonAttribute("Grass", "Water");
        assertEquals(1.0, effectiveness, "Grass should have normal effectiveness against Water.");
    }

    @Test
    void testElectricVsWater() {
        double effectiveness = pokemonAttribute.getPokemonAttribute("Electric", "Water");
        assertEquals(2.0, effectiveness, "Electric should be super effective against Water.");
    }

    @Test
    void testElectricVsGrass() {
        double effectiveness = pokemonAttribute.getPokemonAttribute("Electric", "Grass");
        assertEquals(0.5, effectiveness, "Electric should be not very effective against Grass.");
    }

    @Test
    void testElectricVsFire() {
        double effectiveness = pokemonAttribute.getPokemonAttribute("Electric", "Fire");
        assertEquals(1.0, effectiveness, "Electric should have normal effectiveness against Fire.");
    }

    @Test
    void testUnknownType() {
        double effectiveness = pokemonAttribute.getPokemonAttribute("Unknown", "Fire");
        assertEquals(1.0, effectiveness, "Unknown type should have normal effectiveness against any type.");
    }

    @Test
    void testFireVsUnknownType() {
        double effectiveness = pokemonAttribute.getPokemonAttribute("Fire", "Unknown");
        assertEquals(1.0, effectiveness, "Fire should have normal effectiveness against an unknown type.");
    }

}
