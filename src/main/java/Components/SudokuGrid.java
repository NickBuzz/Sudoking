package Components;

import Controller.SudokuLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuGrid extends JPanel {
    private final JButton[][] buttons;
    private final ButtonGroup buttonGroup;
    private int[][] solvedBoard;
    private final Color colorNumber = new Color(0, 255, 0);
    private final Color colorHighlight = new Color(77, 95, 110);

    public SudokuGrid() {
        buttons = new JButton[9][9];
        solvedBoard = new int[9][9];
        buttonGroup = new ButtonGroup();
        initializeUI();
        newGame(2);
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

                outerLoop:
                for (int row = 0; row < 9; row++) {
                    for (int col = 0; col < 9; col++) {
                        if (buttons[row][col] == clickedButton) {
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

                if (Character.isDigit(typedChar) && typedChar != '0') {
                    if (sourceButton.getText().isEmpty() || sourceButton.getForeground().equals(colorNumber)) {
                        sourceButton.setText(String.valueOf(typedChar));
                        sourceButton.setForeground(colorNumber);


                        outerLoop:
                        for (int row = 0; row < 9; row++) {
                            for (int col = 0; col < 9; col++) {
                                if (buttons[row][col] == sourceButton) {
                                    clearHighlights();
                                    highlight(row, col);
                                    highlightNumber(sourceButton);
                                    break outerLoop;
                                }
                            }
                        }

                    }
                } else if (typedChar == KeyEvent.VK_BACK_SPACE || typedChar == KeyEvent.VK_DELETE) {
                    if (sourceButton.getForeground().equals(colorNumber)) {
                        sourceButton.setText("");

                    }
                }
            }
        };
    }

    public void newGame(int level){
        clearTable();
        clearHighlights();
        solvedBoard = SudokuLogic.fillSudokuBoard(buttons, level);

    }

    public void clearTable(){
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons.length; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setFocusable(true);
                buttons[row][col].setForeground(Color.BLACK);
                solvedBoard[row][col] = 0;
            }
        }
    }

    public void solveTable(){
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (buttons[i][j].getText().isEmpty()){
                    buttons[i][j].setForeground(colorNumber);
                }
                buttons[i][j].setText(String.valueOf(solvedBoard[i][j]));
            }
        }
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
                JOptionPane.showMessageDialog(null, "U are doing grate, keep playing");
            } else {
                JOptionPane.showMessageDialog(null, "Congrats!! u won the game");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Something looks wrong");
        }
    }

    public void highlightNumber(JButton button) {
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                if (button.getText().equals(buttons[row][col].getText()) && !button.getText().isEmpty())
                    buttons[row][col].setBackground(colorHighlight);
            }
        }
    }

    public void highlight(int row, int col) {
        clearHighlights();
        // Resalta la fila
        for (int i = 0; i < 9; i++) {
            buttons[i][col].setBackground(colorHighlight);
        }
        // Resalta la columna
        for (int j = 0; j < 9; j++) {
            buttons[row][j].setBackground(colorHighlight);
        }
        // Resalta la zona 3x3
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                buttons[i][j].setBackground(colorHighlight);
            }
        }
    }

    public void clearHighlights() {
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                if (!buttons[row][col].getBackground().equals(Color.RED))
                    buttons[row][col].setBackground(new Color(124, 134, 145));
            }
        }
    }

//    public void printSolvedBoard(int[][] board) {
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//                System.out.print(board[i][j] + " - ");
//            }
//            System.out.println();
//        }
//    }
}