package com.company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("How many mine do you want on the field? ");
        Scanner scanner = new Scanner(System.in);
        int numMines = scanner.nextInt();
        scanner.nextLine();
        MineField mineField = new MineField(numMines);
        System.out.println(mineField);

        boolean winner = false;
        while (!winner) {
            System.out.print("Set/delete mines marks (x and y coordinates): ");
            String[] coordinates = scanner.nextLine().split("\\s");
            if (coordinates.length != 2 || coordinates[0].matches("\\D") || coordinates[1].matches("\\D") ||
                    Integer.parseInt(coordinates[0]) < 1 || Integer.parseInt(coordinates[0]) > 9 ||
                    Integer.parseInt(coordinates[1]) < 1 || Integer.parseInt(coordinates[1]) > 9) {
                System.out.println("Please enter digits between 1 and 9 for x and y coordinates");
            } else if (!mineField.setMineMarks(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]))) {
                System.out.println("There is a number here!");
            } else {
                System.out.println(mineField);
                winner = mineField.isWinner();
                if (winner) {
                    System.out.println("Congratulations! You found all the mines!");
                }
            }
        }
    }
}
