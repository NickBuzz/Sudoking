package Controller;

import javax.swing.*;
import java.util.Random;

public class SudokuLogic {

    public static int[][] fillSudokuBoard(JButton[][] toggleButtons, int level) {
        Random random = new Random();
        int[][] solvedBoard = new int[9][9];
        createSudoku(solvedBoard);

        // Contador de casillas marcadas
        int markedCount = 0;

        // Marcar al menos 17 casillas
        while (markedCount < 17) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);

            if (toggleButtons[row][col].getText().isEmpty()) {
                int number = solvedBoard[row][col];
                toggleButtons[row][col].setText(String.valueOf(number));
                toggleButtons[row][col].setFocusable(false);
                markedCount++;
            }
        }

        // Marcar otras casillas según el nivel de dificultad
        if (level != 12) {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    if (toggleButtons[row][col].getText().isEmpty() && random.nextInt(level) == 0) {
                        int number = solvedBoard[row][col];
                        toggleButtons[row][col].setText(String.valueOf(number));
                        toggleButtons[row][col].setFocusable(false);
                    }
                }
            }
        }
        return solvedBoard;
    }

    private static void createSudoku(int[][] board){
        Random random = new Random();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int num = random.nextInt(9) + 1;

                if ((i < 3 && j < 3) || (i >= 3 && i < 6 && j >= 3 && j < 6) || (i >= 6 && j >= 6)) {
                    // Solo genera números en los cuadrantes 1, 5 y 9
                    int startRow = i - i % 3;
                    int startCol = j - j % 3;

                    if (usedInBox(board, startRow, startCol, num)) {
                        board[i][j] = num;
                    } else {
                        j--;
                    }
                }
            }
        }
        solveSudoku(board);
    }

    private static boolean solveSudoku(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValidMove(board, row, col, num)) {
                            board[row][col] = num;
                            if (solveSudoku(board)) {
                                return true;
                            }
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValidMove(int[][] board, int row, int col, int num) {
        // Verifica si es seguro colocar el número en la posición dada
        return usedInRow(board, row, num) &&
                usedInCol(board, col, num) &&
                usedInBox(board, row - row % 3, col - col % 3, num);
    }

    private static boolean usedInRow(int[][] board, int row, int num) {
        for (int col = 0; col < 9; col++) {
            if (board[row][col] == num) {
                return false;
            }
        }
        return true;
    }

    private static boolean usedInCol(int[][] board, int col, int num) {
        for (int row = 0; row < 9; row++) {
            if (board[row][col] == num) {
                return false;
            }
        }
        return true;
    }

    private static boolean usedInBox(int[][] board, int startRow, int startCol, int num) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row + startRow][col + startCol] == num) {
                    return false;
                }
            }
        }
        return true;
    }


}
