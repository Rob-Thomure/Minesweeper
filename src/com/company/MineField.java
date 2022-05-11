package com.company;

import java.util.Arrays;
import java.util.Random;

public class MineField {
    int numMines;
    char[][] mineFieldState;

    public MineField(int numMines) {
        this.numMines = numMines;
        this.mineFieldState = new char[9][9];
        setMineFieldState();
    }
    
    private void setMineFieldState() {
        for (int i = 0; i < 9; i++) {
            Arrays.fill(mineFieldState[i], Symbols.SAFE_CELL.getValue());
        }
        int placedMines = 0;
        while (placedMines < numMines) {
            if (placeAMine()) {
                placedMines++;
            }
        }
    }

    private boolean placeAMine() {
        Random random = new Random();
        int row = random.nextInt(9);
        int column = random.nextInt(9);
        if (mineFieldState[row][column] == Symbols.SAFE_CELL.getValue()) {
            mineFieldState[row][column] = Symbols.MINE.getValue();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (char[] row : mineFieldState) {
            for (char column : row) {
                stringBuilder.append(column);
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
