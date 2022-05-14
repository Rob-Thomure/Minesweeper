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
        placeMineCountOnField();
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



    private void placeMineCountOnField() {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                int numMines = 0;
                int i = row == 0 ? row : row - 1;
                int j = column == 0 ? column : column - 1;
                int rowLimit = row == 8 ? row : row + 1;
                int columnLimit = column == 8 ? column : column + 1;
                while (i <= rowLimit) {
                    while (j <= columnLimit) {
                        if ((i != row || j != column) && mineFieldState[i][j] == 'X' ) {
                            numMines++;
                        }
                        j++;
                    }
                    i++;
                    j = column == 0 ? column : column - 1;
                }
                if (numMines != 0 && mineFieldState[row][column] != 'X') {
                    mineFieldState[row][column] = Character.forDigit(numMines, 10);
                }
            }
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
