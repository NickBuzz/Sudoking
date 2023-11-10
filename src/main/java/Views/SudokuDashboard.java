package Views;

import Components.SudokuGrid;
import Components.SudokuMenu;

import javax.swing.*;
import java.awt.*;

public class SudokuDashboard extends JPanel {

    private final SudokuGrid sudokuGrid;
    SudokuDashboard(){
        sudokuGrid = new SudokuGrid();
        initializeUI();
    }

    private void initializeUI(){
        setLayout(null);
        JPanel grid = new JPanel();
        JPanel menu1 = new JPanel();
        JPanel menu2 = new JPanel();

        grid.setSize(450,450);
        grid.setLocation(20,20);
        grid.setBackground(Color.BLACK);
        grid.setLayout(new GridLayout(1,1));
        grid.add(sudokuGrid);

        menu1.setSize(200,580);
        menu1.setLocation(500,20);
        menu1.setBackground(new Color(124, 134, 145));
        menu1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        menu1.setLayout(new GridLayout(2,1));
        menu1.add(new SudokuMenu(sudokuGrid));

        menu2.setSize(450,100);
        menu2.setLocation(20,500);
        menu2.setBackground(new Color(124, 134, 145));
        menu2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));


        add(grid);
        add(menu1);
        add(menu2);
    }
}
