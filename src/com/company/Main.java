package com.company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("How many mine do you want on the field? ");
        Scanner scanner = new Scanner(System.in);
        int numMines = scanner.nextInt();
        MineField mineField = new MineField(numMines);
        System.out.println(mineField);
    }
}
