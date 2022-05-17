package com.company;

public enum Symbols {
    MINE('X'),
    VISIBLE_MINE('*'),
    SAFE_CELL('.');

    final char value;

    Symbols(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }
}
