package com.isla.ecosystem.entity;

/**
 * Enum representing different types of entities in the ecosystem.
 */
public enum EntityType {
    ANIMAL('A'),
    PLANT('*'),
    EMPTY('.');

    private final char symbol;

    EntityType(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public static EntityType fromSymbol(char symbol) {
        for (EntityType type : values()) {
            if (type.symbol == symbol) {
                return type;
            }
        }
        return EMPTY;
    }
}
