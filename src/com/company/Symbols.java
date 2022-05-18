package com.company;

public enum Symbols {
    MINE('X'),
    UNEXPLORED_MARKED_CELL('*'),
    UNEXPLORED_CELL('.'),
    EXPLORED_FREE_CELL('/');

    final char value;

    Symbols(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }
}
