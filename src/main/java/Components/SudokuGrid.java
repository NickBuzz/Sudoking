package Components;

import Controller.SudokuLogic;
import Views.SudokuDashboard;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class SudokuGrid extends JPanel {
    private final JButton[][] buttons;
    private final ButtonGroup buttonGroup;
    private JButton clickedButton;
    private int[][] solvedBoard;
    private final SudokuMarks marks;
    private boolean pencilMark;

    private final Color colorNumber = new Color(26, 233, 253);
    private final Color BG = new Color(124, 134, 145);
    private final Color colorHighlight = new Color(94, 104, 115);
    public SudokuGrid(SudokuMarks marks) {
        this.marks = marks;
        pencilMark = false;
        clickedButton = new JButton();
        buttons = new JButton[9][9];
        solvedBoard = new int[9][9];
        buttonGroup = new ButtonGroup();
        initializeUI();
        newGame(2);
        SwingUtilities.invokeLater(this::requestFocusInWindow);
    }
    private void initializeUI() {
        setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JPanel subPanel = new JPanel(new GridLayout(3, 3));
                subPanel.setBorder(new LineBorder(Color.black,3,true));
                add(subPanel);

                for (int row = i * 3; row < i * 3 + 3; row++) {
                    for (int col = j * 3; col < j * 3 + 3; col++) {

                        JButton button = new JButton("");
                        button.setFont(new Font("Arial Black", Font.PLAIN, 25));
                        button.addActionListener(createButtonActionListener());
                        button.addKeyListener(createButtonKeyListener());
                        button.setBackground(BG);
                        button.setForeground(Color.BLACK);
                        buttons[row][col] = button;
                        buttonGroup.add(button);
                        subPanel.add(button);
                    }
                }
            }
        }
    }

    private ActionListener createButtonActionListener() {
        return e -> {

            JButton clickB = (JButton) e.getSource();
            clickedButton = clickB;
            outerLoop:
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    if (buttons[row][col] == clickB) {
                        highlight(row, col);
                        highlightNumber(clickB);
                        break outerLoop;
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

                        outerLoop:
                        for (int row = 0; row < 9; row++) {
                            for (int col = 0; col < 9; col++) {
                                if (buttons[row][col] == sourceButton) {
                                    if (!pencilMark) {
                                        sourceButton.setText(String.valueOf(typedChar));
                                        sourceButton.setForeground(colorNumber);
                                        clearHighlights();
                                        marks.clearMarksInCell(row, col);
                                        highlight(row, col);
                                        highlightNumber(sourceButton);
                                        break outerLoop;
                                    }else {
                                        if (sourceButton.getText().isEmpty()) {
                                            int mark = Integer.parseInt(String.valueOf(typedChar))-1;
                                            marks.setMarksInCell(row,col,mark);
                                        }
                                    }
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
        requestFocus();
        clickedButton = null;
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
            for (JButton[] button : buttons) {
                for (JButton jButton : button) {
                    if (jButton.getText().isEmpty()) {
                        incomplete = true;
                        break;
                    }
                }
                if (incomplete) {
                    break;
                }
            }

            if (incomplete) {
                JOptionPane.showMessageDialog(null, "U are doing grate, keep playing","",JOptionPane.PLAIN_MESSAGE,null);
            } else {
                JOptionPane.showMessageDialog(null, "Congrats!! u won the game","",JOptionPane.PLAIN_MESSAGE,null);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Something looks wrong","",JOptionPane.PLAIN_MESSAGE,null);
        }
    }

    public void highlightNumber(JButton button) {
        for (JButton[] jButtons : buttons) {
            for (JButton jButton : jButtons) {
                if (button.getText().equals(jButton.getText()) && !button.getText().isEmpty())
                    jButton.setBackground(colorHighlight);
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
        for (JButton[] button : buttons) {
            for (JButton jButton : button) {
                jButton.setBackground(BG);
            }
        }
    }

    public JButton[][] getButtons() {
        return buttons;
    }

    public JButton getClickedButton() {
        return clickedButton;
    }

    public boolean isPencilMark() {
        return pencilMark;
    }

    public void setPencilMark(boolean pencilMark) {
        this.pencilMark = pencilMark;
    }

    public void setGridDimension(Dimension gridDimension) {
        setSize(gridDimension);
    }
}