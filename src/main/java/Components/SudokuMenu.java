package Components;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;

public class SudokuMenu extends JPanel {

    private final String[] difficulty = {"EASY", "NORMAL", "HARD", "EXPERT"};
    private int level;
    private final SudokuGrid sudokuGrid;
    private final JButton[] buttons;
    private final ButtonGroup buttonGroup;
    private final Color BG = new Color(124, 134, 145);

    public SudokuMenu(SudokuGrid sudokuGrid){
        setLayout(new GridLayout(4,1));
        level = 0;
        this.sudokuGrid = sudokuGrid;
        buttons = new JButton[5];
        buttonGroup = new ButtonGroup();
        setBorder(new BevelBorder(BevelBorder.RAISED));
        buildButtons();

        buttons[0].setText("Difficulty");
        buttons[1].setText("New Game");
        buttons[2].setText("Validate");
        buttons[3].setText("Solve Game");
    }

    public void buildButtons(){
        for (int i = 0; i < 4; i++) {
            JButton button = new JButton("");
            button.setFont(new Font("Arial Black", Font.PLAIN, 20));
            button.addActionListener(createButtonActionListener());
            button.addKeyListener(createButtonKeyListener());
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

            switch (sourceButton.getText()){
                case "Difficulty":
                    int selectedOption = JOptionPane.showOptionDialog(null,
                            "Select difficulty:",
                            "",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            difficulty,
                            difficulty[level]);
                    System.out.println("selectedOption = " + selectedOption);
                    if (selectedOption != -1) level = selectedOption;

                    break;
                case "New Game":
                    sudokuGrid.newGame((level+1)*3);
                    break;
                case "Validate":
                    sudokuGrid.checkWin();
                    break;
                case "Solve Game":
                    int response = JOptionPane.showConfirmDialog(
                            null,
                            "  Are you sure?",
                            "",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.PLAIN_MESSAGE,
                            null
                    );
                    if (response == JOptionPane.YES_OPTION) {
                        sudokuGrid.solveTable();
                    }
                    break;
            }
        };
    }

    private KeyListener createButtonKeyListener() {
        return new KeyAdapter() {
        };
    }
}
