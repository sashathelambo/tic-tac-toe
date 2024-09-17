import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TicTacToe {
    // Create the game board as a 3x3 grid
    private static final char[][] board = new char[3][3];
    // Set the current player to 'X' (the game starts with player 'X')
    private static char currentPlayer = 'X';
    private static int i; // Declare i outside of the loop

    public static void main(String[] args) {
        // Display instructions on how to play and move
        displayInstructions();
        
        // Set up the empty board at the start of the game
        initializeBoard();
        // Display the board
        printBoard();

        // Continue playing until the game ends
        while (true) {
            // Ask the current player to make a move
            playerMove();
            // Display the board after the move
            printBoard();

            // Check if the current player has won
            if (checkWin()) {
                System.out.println("Player " + (currentPlayer == 'X' ? "one" : "two") + " wins!");
                break; // End the game if there's a winner
            }

            // Check if the board is full and the game is a draw
            if (isBoardFull()) {
                System.out.println("The game is a draw!");
                break; // End the game if no more moves can be made
            }

            // Switch to the other player for the next move
            switchPlayer();
        }
    }

    // Display instructions on how to play and move
    private static void displayInstructions() {
        System.out.println("Welcome to Tic Tac Toe!");
        System.out.println("Here's how to play:");
        System.out.println("1. The game is played on a 3x3 grid.");
        System.out.println("2. Player one (X) goes first, followed by Player two (O).");
        System.out.println("3. Players take turns placing their mark (X or O) in an empty cell.");
        System.out.println("4. To make a move, enter the number corresponding to the cell (1-9).");
        System.out.println("5. The first player to get 3 of their marks in a row (horizontally, vertically, or diagonally) wins.");
        System.out.println("6. If all 9 cells are filled and no player has 3 marks in a row, the game is a draw.");
        System.out.println("Let's start the game!");
    }

    // Set up the board with empty cells
    private static void initializeBoard() {
        int cellNumber = 1;
        for (i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = (char) (cellNumber + '0'); // Use cell numbers 1-9
                cellNumber++;
            }
        }
    }

    // Print the current state of the board
    private static void printBoard() {
        for (i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]);
                if (j < 2) System.out.print(" | ");
            }
            System.out.println();
            if (i < 2) System.out.println("-----------");
        }
    }

    // Get the player's move (cell number) and update the board
    private static void playerMove() {
        Scanner scanner = new Scanner(System.in);
        int move;

        // Keep asking for a valid move until the player makes one
        while (true) {
            System.out.println("Player " + (currentPlayer == 'X' ? "one" : "two") + " - where would you like to move?");
            try {
                move = scanner.nextInt();

                // Convert move to row and column
                int row = (move - 1) / 3;
                int col = (move - 1) % 3;

                // Check if the move is valid (within bounds and on an empty cell)
                if (move < 1 || move > 9) {
                    System.out.println("That move is invalid!");
                } else if (board[row][col] == 'X' || board[row][col] == 'O') {
                    System.out.println("That move is invalid!");
                } else {
                    // Place the player's mark ('X' or 'O') on the board
                    board[row][col] = currentPlayer;
                    break; // Move is valid, so exit the loop
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number between 1 and 9.");
                scanner.next(); // Clear the invalid input
            } catch (NoSuchElementException e) {
                System.out.println("No input received! Please enter a number between 1 and 9.");
                scanner.next(); // Clear the invalid input
            }
        }
    }

    // Switch to the other player (if 'X' switch to 'O', and vice versa)
    private static void switchPlayer() {
        if (currentPlayer == 'X') {
            currentPlayer = 'O';
        } else {
            currentPlayer = 'X';
        }
    }

    // Check if the current player has won the game
    private static boolean checkWin() {
        // Check rows and columns for a win
        for (i = 0; i < 3; i++) {
            if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
                (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)) {
                return true;
            }
        }
        // Check the two diagonals for a win
        // If no win is found, return false
        return (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
                (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer);
    }

    // Check if the board is full (meaning no more moves can be made)
    private static boolean isBoardFull() {
        for (i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != 'X' && board[i][j] != 'O') {
                    return false; // If there's an empty cell, the board is not full
                }
            }
        }
        return true; // If no empty cells are found, the board is full
    }
}
