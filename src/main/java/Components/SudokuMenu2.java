package Components;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

public class SudokuMenu2 extends JPanel {

    private final JToggleButton pencil = new JToggleButton();
    private final JButton erase = new JButton();
    private final SudokuGrid sudokuGrid;
    private final SudokuMarks marks;
    private JButton[][] tableButtons;
    private final Color BG = new Color(124, 134, 145);

    public SudokuMenu2(SudokuGrid sudokuGrid, SudokuMarks marks){
        this.sudokuGrid = sudokuGrid;
        this.marks = marks;
        this.tableButtons = sudokuGrid.getButtons();
        setLayout(new GridLayout(1,4));
        buildButtons();
    }
    public void buildButtons(){
        pencil.setBackground(BG);
        pencil.setFocusable(false);
        pencil.setBorder(new LineBorder(Color.cyan,1,false));
        pencil.addActionListener(createButtonActionListener());
        add(pencil);
        erase.addActionListener(createButtonActionListener());
        erase.setBackground(BG);
        erase.setBorder(new LineBorder(Color.cyan,1,false));
        erase.setFocusable(false);
        add(erase);

        pencil.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Pencil Drawing.png"))));
        erase.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Return.png"))));
    }


    private ActionListener createButtonActionListener() {
        return e -> {
            if (e.getSource() instanceof JToggleButton){
                sudokuGrid.setPencilMark(pencil.isSelected());
            }
            if (e.getSource() instanceof JButton) {
                tableButtons = sudokuGrid.getButtons();
                JButton clickedButton = sudokuGrid.getClickedButton();
                for (int row = 0; row < 9; row++) {
                    for (int col = 0; col < 9; col++) {
                        if (tableButtons[row][col] == sudokuGrid.getClickedButton() && !clickedButton.getForeground().equals(Color.BLACK)) {
                            tableButtons[row][col].setText("");
                            marks.clearMarksInCell(row,col);
                        }
                        if (tableButtons[row][col] == sudokuGrid.getClickedButton()){
                            marks.clearMarksInCell(row,col);
                        }
                    }
                }
            }
        };
    }
}
