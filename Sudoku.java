import java.util.Random;
import java.util.Scanner;

public class Sudoku {

    private static final int SIZE = 9;
    private static final int SUBGRIDSIZE = 3;
    private int[][] board = new int[SIZE][SIZE]; // Sudoku board
    private boolean[][] fixedCells = new boolean[SIZE][SIZE]; // keep track of which cells are fixed 

    public static void main(String[] args) {
        Sudoku game = new Sudoku();
        game.startGame();
    }

    private void startGame() {
        fillBoard(); // Fill the Sudoku board
        makePuzzle(); // create puzzle remove some numbres
        Scanner scanner = new Scanner(System.in);
        boolean solved = false;

        while (!solved) {
            printBoard();
            System.out.println("Enter row (1-9), column (1-9), and number (1-9) to fill a cell, separated by spaces:");
            int row = scanner.nextInt() - 1;
            int col = scanner.nextInt() - 1;
            int num = scanner.nextInt();

            if (row >= 0 && row < SIZE && col >= 0 && col < SIZE && num > 0 && num <= SIZE && !fixedCells[row][col]) {
                board[row][col] = num; // fill cell via input from user
                solved = checkSolution(); // check if the puzzle is solved
                if (solved) {
                    System.out.println("Congratulations! You've solved the puzzle!");
                    printBoard(); // Display the solved puzzle
                }
            } else {
                System.out.println("Invalid input or cell is fixed. Try again.");
            }
        }
        scanner.close();
    }

    private void fillBoard() {
        fillBoard(0, 0); // Start filling the board from the top-left corner
    }

    // Recursive method to fill the Sudoku board
    private boolean fillBoard(int row, int col) {
        if (row == SIZE) {
            row = 0;
            if (++col == SIZE) {
                return true; // return true if all cells are filled
            }
        }

        // Skip filled cells
        if (board[row][col] != 0) {
            return fillBoard(row + 1, col);
        }

        for (int num = 1; num <= SIZE; num++) {
            if (isSafe(row, col, num)) {
                board[row][col] = num;
                if (fillBoard(row + 1, col)) {
                    return true; // If filled successfully, return true
                }
                board[row][col] = 0; // Backtrack if the board cannot be filled with the current number
            }
        }

        return false; // If no number can be placed in the current cell, return false
    }

    // check if safe to place a number
    private boolean isSafe(int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num || board[row - row % SUBGRIDSIZE + i % SUBGRIDSIZE][col - col % SUBGRIDSIZE + i / SUBGRIDSIZE] == num) {
                return false;
            }
        }
        return true;
    }

    // create the puzzle by randomly removing some numbers
    private void makePuzzle() {
        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (random.nextInt(SIZE) < 5) { // 50% chance to remove a number
                    fixedCells[i][j] = true;
                } else {
                    board[i][j] = 0;
                    fixedCells[i][j] = false;
                }
            }
        }
    }

    // check if the current state of the board is a solution
    private boolean checkSolution() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                int num = board[row][col];
                if (num == 0 || !isSafe(row, col, num)) {
                    return false; 
                }
            }
        }
        return true; 
    }

    // Print the current state of the board
    private void printBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }
}
