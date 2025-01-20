package com.pokemonBattle.Pokemon_Battle;

public class pokemonAttribute {

    public static double getPokemonAttribute(String attackerType, String defenderType) {
        return switch (attackerType) {
            case "Fire" -> defenderType.equals("Grass") ? 2.0 : defenderType.equals("Water") ? 0.5 : 1.0;
            case "Water" -> defenderType.equals("Fire") ? 2.0 : defenderType.equals("Electric") ? 0.5 : 1.0;
            case "Grass" -> defenderType.equals("Electric") ? 2.0 : defenderType.equals("Fire") ? 0.5 : 1.0;
            case "Electric" -> defenderType.equals("Water") ? 2.0 : defenderType.equals("Grass") ? 0.5 : 1.0;
            default -> 1.0;
        };
    }
}
