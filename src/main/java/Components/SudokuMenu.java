package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SudokuMenu extends JPanel {

    private final String[] difficulty = {"EASY", "NORMAL", "HARD"};
    private int level;

    private final SudokuGrid sudokuGrid;
    private final JButton[] buttons;
    private final ButtonGroup buttonGroup;
    public SudokuMenu(SudokuGrid sudokuGrid){
        level = 0;
        this.sudokuGrid = sudokuGrid;
        buttons = new JButton[5];
        buttonGroup = new ButtonGroup();
        initializeUI();

        buttons[0].setText("Difficulty");
        buttons[1].setText("New Game");
        buttons[2].setText("Validate");
        buttons[3].setText("Solve Game");
    }

    public void initializeUI(){
        //setSize(100,500);
        setLayout(new GridLayout(4,1));

        for (int i = 0; i < 4; i++) {
            JButton button = new JButton("");
            button.setFont(new Font("Arial", Font.BOLD, 24));
            button.addActionListener(createButtonActionListener());
            button.addKeyListener(createButtonKeyListener());
            button.setBackground(new Color(124, 134, 145));
            button.setForeground(Color.BLACK);
            //button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
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

                switch (sourceButton.getText()){
                    case "Difficulty":
                        int selectedOption = JOptionPane.showOptionDialog(null,
                                "Selecciona la dificultad:",
                                "Dificultad",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                difficulty,
                                difficulty[level]);
                        System.out.println("selectedOption = " + selectedOption);
                        if (selectedOption != -1) level = selectedOption;

                        break;
                    case "New Game":
                        sudokuGrid.newGame(level+2);
                        break;
                    case "Validate":
                        sudokuGrid.checkWin();
                        break;
                    case "Solve Game":
                        int response = JOptionPane.showConfirmDialog(
                                null,
                                "Are you sure?",
                                "Confirm",
                                JOptionPane.YES_NO_OPTION
                        );
                        if (response == JOptionPane.YES_OPTION) {
                            sudokuGrid.solveTable();
                        }
                        break;
                }
            }
        };
    }

    private KeyListener createButtonKeyListener() {
        return new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {


            }
        };
    }
}
