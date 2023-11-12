package Components;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class SudokuNumPad extends JPanel {

    private final JButton[] buttons;
    private JButton[][] tableButtons;
    private final ButtonGroup buttonGroup;
    private final SudokuGrid sudokuGrid;
    private final Color BG = new Color(124, 134, 145);
    private final Color COLOR_F = new Color(26, 233, 253);


    public SudokuNumPad(SudokuGrid sudokuGrid){
        this.sudokuGrid = sudokuGrid;
        this.tableButtons = sudokuGrid.getButtons();
        buttons = new JButton[9];
        buttonGroup = new ButtonGroup();
        setBorder(new BevelBorder(BevelBorder.RAISED));
        initUI();
    }

    private void initUI(){
        setLayout(new GridLayout(1,9));
        buildButtons();
    }

    private void buildButtons(){
        for (int i = 0; i < buttons.length; i++) {
            JButton button = new JButton(String.valueOf(i+1));
            button.setFont(new Font("Arial Black", Font.PLAIN, 25));
            button.addActionListener(createButtonActionListener());
            button.setBackground(BG);
            button.setForeground(Color.BLACK);
            button.setFocusable(false);
            buttons[i] = button;
            buttonGroup.add(button);
            add(button);
        }
    }

    private ActionListener createButtonActionListener() {
        return e -> {

            JButton sourceButton = (JButton) e.getSource();
            JButton clickedButton = sudokuGrid.getClickedButton();
            tableButtons = sudokuGrid.getButtons();

            if (sourceButton != null){
                outerLoop:
                for (int row = 0; row < 9; row++) {
                    for (int col = 0; col < 9; col++) {
                        if (tableButtons[row][col] == clickedButton) {
                            if (clickedButton.getText().isEmpty() || clickedButton.getForeground().equals(COLOR_F)) {
                                tableButtons[row][col].setText(sourceButton.getText());
                                tableButtons[row][col].setForeground(COLOR_F);
                            }
                            sudokuGrid.clearHighlights();
                            sudokuGrid.highlight(row,col);
                            sudokuGrid.highlightNumber(clickedButton);
                            clearHightlightNumber();
                            break outerLoop;
                        }
                    }
                }
            }
        };
    }

    private void clearHightlightNumber(){
        for (JButton button : buttons) {
            button.setBackground(BG);
        }
    }
}
