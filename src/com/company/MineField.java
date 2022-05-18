package com.company;

import java.util.Arrays;
import java.util.Random;

public class MineField {
    private final int numMines;
    private final char[][] hiddenMineField;
    private final char[][] visibleMineField;
    private boolean winner;
    private boolean loser;

    {
        hiddenMineField = new char[9][9];
        visibleMineField = new char[9][9];
        winner = false;
        loser = false;
    }

    public MineField(int numMines) {
        this.numMines = numMines;
        setMineFieldState();
        placeMineCountsOnHiddenField();
    }

    private void setMineFieldState() {
        for (int i = 0; i < 9; i++) {
            Arrays.fill(visibleMineField[i], Symbols.UNEXPLORED_CELL.getValue());
            Arrays.fill(hiddenMineField[i], Symbols.UNEXPLORED_CELL.getValue());
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
        if (hiddenMineField[row][column] == Symbols.UNEXPLORED_CELL.getValue()) {
            hiddenMineField[row][column] = Symbols.MINE.getValue();
            return true;
        } else {
            return false;
        }
    }

    private void placeMineCountsOnHiddenField() {
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
                    hiddenMineField[row][column] = Character.forDigit(numMines, 10);
                }
            }
        }
    }

    public boolean setMineMarks(Coordinates coordinates) {
        int row = coordinates.getY() - 1;
        int column = coordinates.getX() - 1;
        if (Character.isDigit(visibleMineField[row][column])) {
            return false;
        } else {
            visibleMineField[row][column] = visibleMineField[row][column] == Symbols.UNEXPLORED_CELL.getValue() ?
                    Symbols.UNEXPLORED_MARKED_CELL.getValue() : Symbols.UNEXPLORED_CELL.getValue();
            setWinner();
            return true;
        }
    }

    private void setWinner() {
        int unexploredMarkedCells = 0;
        int unexploredCells = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (visibleMineField[i][j] == Symbols.UNEXPLORED_MARKED_CELL.getValue()) {
                    unexploredMarkedCells++;
                } else if (visibleMineField[i][j] == Symbols.UNEXPLORED_CELL.getValue()) {
                    unexploredCells++;
                }
            }
        }
        if (unexploredMarkedCells + unexploredCells == numMines) {
            winner = true;
        } else if (unexploredMarkedCells == numMines) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (visibleMineField[i][j] == Symbols.UNEXPLORED_MARKED_CELL.getValue() &&
                            hiddenMineField[i][j] != Symbols.MINE.getValue()) {
                        winner = false;
                        return;
                    }
                }
            }
            winner = true;
        }
    }

    public boolean isWinner() {
        return winner;
    }

    public boolean isLoser() {
        return loser;
    }

    public void exploreCell(Coordinates coordinates) {
        int row = coordinates.getY() - 1;
        int column = coordinates.getX() - 1;
        if (visibleMineField[row][column] == Symbols.UNEXPLORED_CELL.getValue()) {
            revealSurroundingCells(row, column);
        }
        setWinner();
    }

    private void revealSurroundingCells(int row, int column) {
        int minRow = row == 0 ? row : row - 1;
        int maxRow = row == 8 ? row : row + 1;
        int minColumn = column == 0 ? column : column - 1;
        int maxColumn = column == 8 ? column : column + 1;

        if (hiddenMineField[row][column] == Symbols.UNEXPLORED_CELL.getValue()) {
            hiddenMineField[row][column] = Symbols.EXPLORED_FREE_CELL.getValue();
            visibleMineField[row][column] = Symbols.EXPLORED_FREE_CELL.getValue();
            for (int i = minRow; i <= maxRow; i++) {
                for (int j = minColumn; j <= maxColumn; j++) {
                    if (Character.isDigit(hiddenMineField[i][j])) {
                        visibleMineField[i][j] = hiddenMineField[i][j];
                    } else if (hiddenMineField[i][j] == Symbols.UNEXPLORED_CELL.getValue()) {
                        visibleMineField[i][j] = Symbols.EXPLORED_FREE_CELL.getValue();
                    }
                    revealSurroundingCells(i, j);
                }
            }
        } else if (hiddenMineField[row][column] == Symbols.MINE.getValue()) {
            loser = true;
        } else if (Character.isDigit(hiddenMineField[row][column])) {
            visibleMineField[row][column] = hiddenMineField[row][column];
        }
    }

    public void revealMines() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (hiddenMineField[i][j] == Symbols.MINE.getValue()) {
                    visibleMineField[i][j] = hiddenMineField[i][j];
                }
            }
        }
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
