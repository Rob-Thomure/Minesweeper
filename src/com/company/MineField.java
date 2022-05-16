package com.company;

import java.util.Arrays;
import java.util.Random;

public class MineField {
    private final int numMines;
    private final char[][] hiddenMineField;
    private final char[][] visibleMineField;
    private boolean winner;

    {
        hiddenMineField = new char[9][9];
        visibleMineField = new char[9][9];
        winner = false;
    }

    public MineField(int numMines) {
        this.numMines = numMines;
        setMineFieldState();
        placeMineCountOnField();
    }

    private void setMineFieldState() {
        for (int i = 0; i < 9; i++) {
            Arrays.fill(visibleMineField[i], Symbols.SAFE_CELL.getValue());
            Arrays.fill(hiddenMineField[i], Symbols.SAFE_CELL.getValue());
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
        if (hiddenMineField[row][column] == Symbols.SAFE_CELL.getValue()) {
            hiddenMineField[row][column] = Symbols.MINE.getValue();
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
                        if ((i != row || j != column) && hiddenMineField[i][j] == 'X' ) {
                            numMines++;
                        }
                        j++;
                    }
                    i++;
                    j = column == 0 ? column : column - 1;
                }
                if (numMines != 0 && hiddenMineField[row][column] != 'X') {
                    visibleMineField[row][column] = Character.forDigit(numMines, 10);
                }
            }
        }
    }

    public boolean setMineMarks(int x, int y) {
        int row = y - 1;
        int column = x - 1;
        if (Character.isDigit(visibleMineField[row][column])) {
            return false;
        } else {
            visibleMineField[row][column] = visibleMineField[row][column] == Symbols.SAFE_CELL.getValue() ?
                    Symbols.VISIBLE_MINE.getValue() : Symbols.SAFE_CELL.getValue();
            setWinner();
            return true;
        }
    }

    private void setWinner() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (visibleMineField[i][j] == Symbols.VISIBLE_MINE.getValue() &&
                        hiddenMineField[i][j] != Symbols.MINE.getValue() ||
                        hiddenMineField[i][j] == Symbols.MINE.getValue() &&
                        visibleMineField[i][j] != Symbols.VISIBLE_MINE.getValue()) {
                    winner = false;
                    return;
                }
            }
            winner = true;
        }
    }

    public boolean isWinner() {
        return winner;
    }

    @Override
    public String toString() {
        int rowCount = 1;
        StringBuilder stringBuilder = new StringBuilder("\n |123456789|\n-|---------|\n");
        for (char[] row : visibleMineField) {
            stringBuilder.append(String.format("%d|", rowCount));
            for (char column : row) {
                stringBuilder.append(column);
            }
            stringBuilder.append("|\n");
            rowCount++;
        }
        stringBuilder.append("-|---------|");
        return stringBuilder.toString();
    }
}
