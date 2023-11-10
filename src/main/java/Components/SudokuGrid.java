package Components;

import Controller.SudokuLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuGrid extends JPanel {
    private final JButton[][] buttons;
    private final ButtonGroup buttonGroup;
    private final int[][] solvedBoard;
    private final Color colorNumber = new Color(0, 255, 0);
    private final Color colorHighlight = new Color(107, 115, 124);

    public JButton[][] getButtons() {
        return buttons;
    }

    public int[][] getSolvedBoard() {
        return solvedBoard;
    }

    public SudokuGrid() {
        buttons = new JButton[9][9];
        buttonGroup = new ButtonGroup();
        initializeUI();
        solvedBoard = SudokuLogic.fillSudokuBoard(buttons);
        printSolvedBoard(solvedBoard);
        SwingUtilities.invokeLater(this::requestFocusInWindow);
    }

    private void initializeUI() {
        //setSize(500,500);
        setLayout(new GridLayout(3, 3));
        setBackground(Color.BLACK);
        //setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JPanel subPanel = new JPanel(new GridLayout(3, 3));  // Subpanel para cada área de 3x3
                subPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
                add(subPanel);

                for (int row = i * 3; row < i * 3 + 3; row++) {
                    for (int col = j * 3; col < j * 3 + 3; col++) {

                        JButton button = new JButton("");
                        button.setFont(new Font("Arial", Font.BOLD, 30));
                        button.addActionListener(createButtonActionListener());
                        button.addKeyListener(createButtonKeyListener());
                        button.setBackground(new Color(124, 134, 145));
                        button.setForeground(Color.BLACK);
                        //button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        buttons[row][col] = button;
                        buttonGroup.add(button);
                        subPanel.add(button);
                    }
                }
            }
        }
    }

    private ActionListener createButtonActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JButton clickedButton = (JButton) e.getSource();
                // Lógica del botón presionado
                int row = -1, col = -1;

                outerLoop:
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (buttons[i][j] == clickedButton) {
                            row = i;
                            col = j;

                            highlight(row, col);
                            highlightNumber(clickedButton);
                            break outerLoop;
                        }
                    }
                }
            }
        };
    }

    private KeyListener createButtonKeyListener() {
        return new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char typedChar = e.getKeyChar();
                JButton sourceButton = (JButton) e.getSource();

                if (Character.isDigit(typedChar) && typedChar != '0' && sourceButton.isFocusable()) {
                    sourceButton.setText(String.valueOf(typedChar));
                    sourceButton.setForeground(colorNumber);
                    //highlightNumber(sourceButton);

                } else if (typedChar == KeyEvent.VK_BACK_SPACE || typedChar == KeyEvent.VK_DELETE) {
                    sourceButton.setText("");
                    sourceButton.setBackground(Color.GRAY);
                }
            }
        };
    }

    public void checkWin() {
        boolean error = false;
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (!buttons[i][j].getText().isEmpty()) {
                    int value = Integer.parseInt(buttons[i][j].getText());
                    if (value != solvedBoard[i][j]) {
                        System.out.println(solvedBoard[i][j] + " + " + value);
                        error = true;
                        break;
                    }
                }
            }
            if (error){
                break;
            }
        }

        if (!error) {
            // Verifica si el sudoku está completo
            boolean incomplete = false;
            for (int i = 0; i < buttons.length; i++) {
                for (int j = 0; j < buttons[i].length; j++) {
                    if (buttons[i][j].getText().isEmpty()) {
                        incomplete = true;
                        break;
                    }
                }
                if (incomplete) {
                    break;
                }
            }

            if (incomplete) {
                // Sudoku incompleto, pero sin errores hasta el momento
                JOptionPane.showMessageDialog(null, "Vas por buen camino, pero aún no has completado el Sudoku.");
            } else {
                // Sudoku completo y sin errores
                JOptionPane.showMessageDialog(null, "¡Felicidades! Has ganado el Sudoku.");
            }
        } else {
            // Se encontró un error
            JOptionPane.showMessageDialog(null, "Hay un error en el Sudoku. Intenta de nuevo.");
        }
    }

    private boolean findCoordinate(JButton button) {
        int row = -1;
        int col = -1;

        outerloop:
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[0].length; j++) {
                if (buttons[i][j] == button) {
                    row = i;
                    col = j;
                    break outerloop;
                }
            }
        }
        return solvedBoard[row][col] == Integer.parseInt(button.getText());
    }

    public void highlightNumber(JButton button) {
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                if (button.getText().equals(buttons[row][col].getText()) && !button.getText().isEmpty())
                    if (!buttons[row][col].getBackground().equals(Color.RED)) buttons[row][col].setBackground(colorHighlight);
            }
        }
    }

    public void highlight(int row, int col) {
        clearHighlights();
        // Resalta la fila
        for (int i = 0; i < 9; i++) {
            if (!buttons[i][col].getBackground().equals(Color.RED)) buttons[i][col].setBackground(colorHighlight);
        }
        // Resalta la columna
        for (int j = 0; j < 9; j++) {
            if (!buttons[row][j].getBackground().equals(Color.RED)) buttons[row][j].setBackground(colorHighlight);
        }
        // Resalta la zona 3x3
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (!buttons[i][j].getBackground().equals(Color.RED)) buttons[i][j].setBackground(colorHighlight);
            }
        }
        if (!buttons[row][col].getBackground().equals(Color.RED)) buttons[row][col].setBackground(new Color(78, 83, 93));
    }

    public void clearHighlights() {
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                if (!buttons[row][col].getBackground().equals(Color.RED))
                    buttons[row][col].setBackground(new Color(124, 134, 145));
            }
        }
    }

    public void printSolvedBoard(int[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " - ");
            }
            System.out.println();
        }
    }
}