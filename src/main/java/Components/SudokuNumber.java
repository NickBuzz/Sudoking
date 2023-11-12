package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuNumber extends JPanel {

    private JButton[] buttons;
    private JButton[][] tableButtons;
    private ButtonGroup buttonGroup;
    private final SudokuGrid sudokuGrid;
    private final Color COLOR_D = new Color(124, 134, 145);
    private final Color COLOR_H = new Color(77, 95, 110);
    private final Color COLOR_F = new Color(0, 255, 0);


    public SudokuNumber(SudokuGrid sudokuGrid){
        this.sudokuGrid = sudokuGrid;
        this.tableButtons = sudokuGrid.getButtons();
        buttons = new JButton[9];
        buttonGroup = new ButtonGroup();
        initUI();
    }

    private void initUI(){
        setLayout(new GridLayout(1,9));
        buildButtons();
    }

    private void buildButtons(){
        for (int i = 0; i < buttons.length; i++) {
            JButton button = new JButton(String.valueOf(i+1));
            button.setFont(new Font("Arial", Font.BOLD, 30));
            button.addActionListener(createButtonActionListener());
            button.setBackground(COLOR_D);
            button.setForeground(Color.BLACK);
            button.setFocusable(false);
            buttons[i] = button;
            buttonGroup.add(button);
            add(button);
        }
    }

    private ActionListener createButtonActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
            }
        };
    }

    private void clearHightlightNumber(){
        for (JButton button : buttons) {
            button.setBackground(COLOR_D);
        }
    }
}
