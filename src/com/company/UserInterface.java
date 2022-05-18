package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface {
    MineField mineField = null;
    UserTurn userTurn = null;

    public void startGame() {
        setUpGameField();
        while (!mineField.isWinner() || !mineField.isLoser()) {
            System.out.println(mineField);
            setupTurn();
            switch (userTurn.getActionType()) {
                case MINE:
                    if (!mineField.setMineMarks(userTurn.getCoordinates())) {
                        System.out.println("There is a number here!");
                    }
                    break;
                case FREE:
                    mineField.exploreCell(userTurn.getCoordinates());
                    break;
                default:
                    System.out.println("Invalid input");
            }
            if (mineField.isWinner() || mineField.isLoser()) {
                break;
            }
        }
        if (mineField.isLoser()) {
            mineField.revealMines();
        }
        System.out.println(mineField);
        System.out.println(mineField.isWinner() ? "Congratulations! You found all the mines!" :
                "You stepped on a mine and failed!");

    }

    private void setupTurn() {
        while (true) {
            System.out.print("Set/unset mine marks or claim a cell as free: ");
            Scanner scanner = new Scanner(System.in);
            String[] userInput = scanner.nextLine().split("\\s");
            try {
                Coordinates coordinates = new Coordinates(Integer.parseInt(userInput[0]),
                        Integer.parseInt(userInput[1]));
                ActionType actionType = ActionType.valueOf(userInput[2].toUpperCase());
                userTurn = new UserTurn(coordinates, actionType);
                return;
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter digits between 1 and 9 for x and y coordinates");
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Error: please enter X Y coordinates and (mine or free)");
            }
        }
    }

    private void setUpGameField() {
        while (true) {
            System.out.print("How many mines do you want on the field? ");
            Scanner scanner = new Scanner(System.in);
            try {
                mineField = new MineField(scanner.nextInt());
                return;
            } catch (InputMismatchException e) {
                System.out.println("Error: enter a number only.");
            }
        }
    }
}
