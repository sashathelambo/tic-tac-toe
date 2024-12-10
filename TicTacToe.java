import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    public static final int BOARD_SIZE = 3;
    static final char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
    static char currentPlayer;
    static char playerOne;
    static char playerTwo;
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

                Player player1 = new HumanPlayer(playerOne, scanner);
                Player player2 = playAgainstComputer ? new ComputerPlayer(playerTwo) : new HumanPlayer(playerTwo, scanner);

                while (true) {
                    if (currentPlayer == playerOne) {
                        player1.makeMove(board);
                    } else {
                        player2.makeMove(board);
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
        System.out.println("2. Players take turns placing their mark in an empty cell.");
        System.out.println("3. To make a move, enter the number corresponding to the cell (1-9).");
        System.out.println("4. The first player to get 3 of their marks in a row (horizontally, vertically, or diagonally) wins.");
        System.out.println("5. If all 9 cells are filled and no player has 3 marks in a row, the game is a draw.");
        System.out.println("Let's start the game!");
    }

    private static void choosePlayerSymbols(Scanner scanner) {
        while (true) {
            System.out.println("Player one, choose your mark (one character only, no whitespace):");
            String input = scanner.nextLine();
            if (isValidMark(input)) {
                playerOne = input.charAt(0);
                break;
            } else {
                System.out.println("Invalid mark! Please enter a single non-whitespace character.");
            }
        }

        while (true) {
            System.out.println("Player " + (playAgainstComputer ? "computer" : "two") + 
                             ", choose your mark (one character only, no whitespace):");
            String input = scanner.nextLine();
            if (isValidMark(input) && input.charAt(0) != playerOne) {
                playerTwo = input.charAt(0);
                currentPlayer = playerOne;
                return;
            } else if (input.length() == 1 && input.charAt(0) == playerOne) {
                System.out.println("This mark is already taken! Please choose a different mark.");
            } else {
                System.out.println("Invalid mark! Please enter a single non-whitespace character.");
            }
        }
    }

    private static boolean isValidMark(String input) {
        return input.length() == 1 && !Character.isWhitespace(input.charAt(0));
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
                if (board[i][j] != playerOne && board[i][j] != playerTwo) {
                    return false;
                }
            }
        }
        return true;
    }
}

abstract class Player {
    protected char symbol;

    public Player(char symbol) {
        this.symbol = symbol;
    }

    public abstract void makeMove(char[][] board);
}

class HumanPlayer extends Player {
    private Scanner scanner;

    public HumanPlayer(char symbol, Scanner scanner) {
        super(symbol);
        this.scanner = scanner;
    }

    @Override
    public void makeMove(char[][] board) {
        while (true) {
            System.out.println("Player " + (symbol == TicTacToe.playerOne ? "one" : "two") + " - where would you like to move?");
            try {
                int move = Integer.parseInt(scanner.nextLine());
                int row = (move - 1) / TicTacToe.BOARD_SIZE;
                int col = (move - 1) % TicTacToe.BOARD_SIZE;

                if (move < 1 || move > TicTacToe.BOARD_SIZE * TicTacToe.BOARD_SIZE) {
                    System.out.println("That move is invalid!");
                } else if (board[row][col] == TicTacToe.playerOne || board[row][col] == TicTacToe.playerTwo) {
                    System.out.println("That move is invalid!");
                } else {
                    board[row][col] = symbol;
                    TicTacToe.currentPlayer = symbol;
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
}

class ComputerPlayer extends Player {
    private Random random;

    public ComputerPlayer(char symbol) {
        super(symbol);
        this.random = new Random();
    }

    @Override
    public void makeMove(char[][] board) {
        int move;
        while (true) {
            move = random.nextInt(TicTacToe.BOARD_SIZE * TicTacToe.BOARD_SIZE) + 1;
            int row = (move - 1) / TicTacToe.BOARD_SIZE;
            int col = (move - 1) % TicTacToe.BOARD_SIZE;
            if (board[row][col] != TicTacToe.playerOne && board[row][col] != TicTacToe.playerTwo) {
                board[row][col] = symbol;
                TicTacToe.currentPlayer = symbol;
                System.out.println("Computer moves to " + move);
                break;
            }
        }
    }
}
