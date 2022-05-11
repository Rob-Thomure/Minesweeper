package com.company;

import java.util.Arrays;

public class MineField {
    char[][] mineFieldState;
    
    public MineField() {
        this.mineFieldState = new char[9][9];
        setMineFieldState();
    }
    
    private void setMineFieldState() {
        for (int i = 0; i < 9; i++) {
            Arrays.fill(mineFieldState[i], '.');
        }
        mineFieldState[0][1] = 'X';
        mineFieldState[1][5] = 'X';
        mineFieldState[1][8] = 'X';
        mineFieldState[2][4] = 'X';
        mineFieldState[3][6] = 'X';
        mineFieldState[4][2] = 'X';
        mineFieldState[5][4] = 'X';
        mineFieldState[6][2] = 'X';
        mineFieldState[7][2] = 'X';
        mineFieldState[8][6] = 'X';
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
