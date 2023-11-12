package Views;

import Components.SudokuGrid;
import Components.SudokuMenu;
import Components.SudokuNumPad;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SudokuDashboard extends JPanel {

    private final SudokuGrid sudokuGrid;
    private final LineBorder BORDER = new LineBorder(Color.cyan,2,true);
    SudokuDashboard(){
        sudokuGrid = new SudokuGrid();
        initializeUI();
    }

    private void initializeUI(){
        setLayout(null);
        JPanel grid = new JPanel();
        JPanel menu1 = new JPanel();
        JPanel menu2 = new JPanel();
        JPanel numPad = new JPanel();

        grid.setSize(450,450);
        grid.setLocation(20,20);
        grid.setBorder(BORDER);
        grid.setLayout(new GridLayout(1,1));
        grid.add(sudokuGrid);

        menu1.setSize(200,300);
        menu1.setLocation(500,20);
        menu1.setBorder(BORDER);
        menu1.setLayout(new GridLayout(1,1));
        menu1.add(new SudokuMenu(sudokuGrid));

        menu2.setSize(200,200);
        menu2.setLocation(500,350);
        menu2.setBorder(BORDER);


        numPad.setSize(450,50);
        numPad.setLocation(20,500);
        numPad.setBorder(BORDER);
        numPad.setLayout(new GridLayout(1,1));
        numPad.add(new SudokuNumPad(sudokuGrid));


        add(grid);
        add(menu1);
        add(menu2);
        add(numPad);
    }
}
