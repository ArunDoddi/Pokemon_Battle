package com.pokemonBattle.Pokemon_Battle;

import java.util.Objects;

public class Pokemon {

    private String name;
    private String type;
    private int hitPoints;
    private int attack;
    private int defense;
    private int speed;

    public Pokemon(Pokemon p) {
        if (p == null) {
            throw new IllegalArgumentException("Pokemon object cannot be null");
        }
        this.name = p.name;
        this.type = p.type;
        this.hitPoints = p.hitPoints;
        this.attack = p.attack;
        this.defense = p.defense;
        this.speed = p.speed;
    }

    public String getName() {
        return name;
    }

    public Pokemon(String name, String type, int hitPoints, int attack, int defense, int speed) {
        this.name = name;
        this.type = type;
        this.hitPoints = hitPoints;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        if (hitPoints < 0) {
            this.hitPoints = 0; // Ensure HP doesn't go below 0
        } else {
            this.hitPoints = hitPoints;
        }

    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return String.format("Pokemon{name='%s', type='%s', hitPoints=%d, attack=%d, defense=%d, speed=%d}",
                name, type, hitPoints, attack, defense, speed);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pokemon pokemon = (Pokemon) obj;
        return hitPoints == pokemon.hitPoints &&
                attack == pokemon.attack &&
                defense == pokemon.defense &&
                speed == pokemon.speed &&
                name.equals(pokemon.name) &&
                type.equals(pokemon.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, hitPoints, attack, defense, speed);
    }

}
