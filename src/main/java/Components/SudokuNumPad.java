package Components;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class SudokuNumPad extends JPanel {

    private final JButton[] buttons;
    private JButton[][] tableButtons;
    private final ButtonGroup buttonGroup;
    private final SudokuGrid sudokuGrid;
    private final SudokuMarks marks;
    private final Color BG = new Color(124, 134, 145);
    private final Color COLOR_F = new Color(26, 233, 253);


    public SudokuNumPad(SudokuGrid sudokuGrid, SudokuMarks marks){
        this.marks = marks;
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
        int index = 0;
        for (int j = 0; j < 3; j++) {
            JPanel subPanel = new JPanel(new GridLayout(1, 1));
            subPanel.setBorder(new LineBorder(Color.black,5,false));
            for (int i = 0; i < 3; i++) {
                JButton button = new JButton(String.valueOf(index + 1));
                button.setFont(new Font("Arial Black", Font.PLAIN, 25));
                button.addActionListener(createButtonActionListener());
                button.setBackground(BG);
                button.setBorder(new LineBorder(Color.CYAN,1,false));
                button.setForeground(Color.BLACK);
                button.setFocusable(false);
                buttons[index] = button;
                buttonGroup.add(button);
                subPanel.add(button);
                index++;
            }
            add(subPanel);
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
                            int mark = Integer.parseInt(sourceButton.getText())-1;
                            if (!sudokuGrid.isPencilMark()) {
                                if (clickedButton.getText().isEmpty() || clickedButton.getForeground().equals(COLOR_F)) {
                                    tableButtons[row][col].setText(sourceButton.getText());
                                    tableButtons[row][col].setForeground(COLOR_F);
                                }
                                marks.clearMarksInCell(row,col);
                                marks.clearMarks(row,col,mark);
                                sudokuGrid.clearHighlights();
                                sudokuGrid.highlight(row, col);
                                sudokuGrid.highlightNumber(clickedButton);
                                clearHightlightNumber();
                                break outerLoop;
                            }else{
                                if (clickedButton.getText().isEmpty()){

                                    marks.setMarksInCell(row,col,mark);
                                }
                            }
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
