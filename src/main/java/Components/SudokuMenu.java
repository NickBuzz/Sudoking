package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuMenu extends JPanel {

    private final SudokuGrid sudokuGrid;
    private final JButton[] buttons;
    private final ButtonGroup buttonGroup;
    public SudokuMenu(SudokuGrid sudokuGrid){
        this.sudokuGrid = sudokuGrid;
        buttons = new JButton[5];
        buttonGroup = new ButtonGroup();
        initializeUI();
        buttons[0].setText("Difficulty");
        buttons[1].setText("New Game");
        buttons[2].setText("Validate");
        buttons[3].setText("EXIT");
    }

    public void initializeUI(){
        //setSize(100,500);
        setLayout(new GridLayout(4,1));

        for (int i = 0; i < 4; i++) {
            JButton button = new JButton("");
            button.setFont(new Font("Arial", Font.BOLD, 30));
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
                        System.out.println("elija la dificultad");
                        break;
                    case "New Game":
                        System.out.println("Juego nuevo");
                        break;
                    case "Validate":
                        sudokuGrid.checkWin();
                        break;
                    case "EXIT":
                        System.out.println("Adios");
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