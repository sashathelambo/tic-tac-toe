import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    private static final int BOARD_SIZE = 3;
    private static final char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
    private static char currentPlayer;
    private static char playerOne;
    private static char playerTwo;
    private static boolean playAgainstComputer;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                displayMainMenu(scanner);
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                if (choice == 1) {
                    playAgainstComputer = false;
                } else if (choice == 2) {
                    playAgainstComputer = true;
                } else {
                    System.out.println("Invalid choice! Please enter 1 or 2.");
                    continue;
                }

                displayInstructions();
                choosePlayerSymbols(scanner);
                initializeBoard();
                printBoard();

                while (true) {
                    if (playAgainstComputer && currentPlayer == playerTwo) {
                        computerMove();
                    } else {
                        playerMove(scanner);
                    }
                    printBoard();

                    if (checkWin()) {
                        System.out.println("Player " + (currentPlayer == playerOne ? "one" : "two") + " wins!");
                        break;
                    }

                    if (isBoardFull()) {
                        System.out.println("The game is a draw!");
                        break;
                    }

                    switchPlayer();
                }

                while (true) {
                    System.out.println("Do you want to play again? (yes/no)");
                    String playAgain = scanner.nextLine().toLowerCase();
                    if (playAgain.equals("yes")) {
                        break;
                    } else if (playAgain.equals("no")) {
                        System.out.println("Thank you for playing! Goodbye!");
                        return;
                    } else {
                        System.out.println("Invalid choice! Please enter yes or no.");
                    }
                }
            }
        }
    }

    private static void displayMainMenu(Scanner scanner) {
        System.out.println("Welcome to Tic Tac Toe!");
        System.out.println("1. Play against a human");
        System.out.println("2. Play against the computer");
        System.out.print("Enter your choice: ");
    }

    private static void displayInstructions() {
        System.out.println("Here's how to play:");
        System.out.println("1. The game is played on a 3x3 grid.");
        System.out.println("2. Players take turns placing their mark (X or O) in an empty cell.");
        System.out.println("3. To make a move, enter the number corresponding to the cell (1-9).");
        System.out.println("4. The first player to get 3 of their marks in a row (horizontally, vertically, or diagonally) wins.");
        System.out.println("5. If all 9 cells are filled and no player has 3 marks in a row, the game is a draw.");
        System.out.println("Let's start the game!");
    }

    private static void choosePlayerSymbols(Scanner scanner) {
        while (true) {
            System.out.println("Player one, do you want to be X or O?");
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("x") || input.equals("o")) {
                playerOne = input.toUpperCase().charAt(0);
                playerTwo = (playerOne == 'X') ? 'O' : 'X';
                currentPlayer = playerOne;
                return;
            } else {
                System.out.println("Invalid input! Please enter X or O.");
            }
        }
    }

    private static void initializeBoard() {
        int cellNumber = 1;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = (char) (cellNumber + '0');
                cellNumber++;
            }
        }
    }

    private static void printBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j]);
                if (j < BOARD_SIZE - 1) System.out.print(" | ");
            }
            System.out.println();
            if (i < BOARD_SIZE - 1) System.out.println("-----------");
        }
    }

    private static void playerMove(Scanner scanner) {
        while (true) {
            System.out.println("Player " + (currentPlayer == playerOne ? "one" : "two") + " - where would you like to move?");
            try {
                int move = Integer.parseInt(scanner.nextLine());
                int row = (move - 1) / BOARD_SIZE;
                int col = (move - 1) % BOARD_SIZE;

                if (move < 1 || move > BOARD_SIZE * BOARD_SIZE) {
                    System.out.println("That move is invalid!");
                } else if (board[row][col] == 'X' || board[row][col] == 'O') {
                    System.out.println("That move is invalid!");
                } else {
                    board[row][col] = currentPlayer;
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number between 1 and 9.");
                scanner.nextLine(); 
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number between 1 and 9.");
                scanner.nextLine(); 
            } catch (NoSuchElementException e) {
                System.out.println("No input received! Please try again.");
              
            }
        }
    }

    private static void computerMove() {
        Random random = new Random();
        int move;
        while (true) {
            move = random.nextInt(BOARD_SIZE * BOARD_SIZE) + 1;
            int row = (move - 1) / BOARD_SIZE;
            int col = (move - 1) % BOARD_SIZE;
            if (board[row][col] != 'X' && board[row][col] != 'O') {
                board[row][col] = currentPlayer;
                System.out.println("Computer moves to " + move);
                break;
            }
        }
    }

    private static void switchPlayer() {
        currentPlayer = (currentPlayer == playerOne) ? playerTwo : playerOne;
    }

    private static boolean checkWin() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
                    (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)) {
                return true;
            }
        }
        return (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
                (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer);
    }

    private static boolean isBoardFull() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] != 'X' && board[i][j] != 'O') {
                    return false;
                }
            }
        }
        return true;
    }
}
