package Views;

import Components.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class SudokuDashboard extends JPanel {

    private final SudokuGrid sudokuGrid;
    private final SudokuMarks marks;
    //private final LineBorder BORDER = new LineBorder(Color.cyan,2,true);
    SudokuDashboard(){
        marks = new SudokuMarks();
        sudokuGrid = new SudokuGrid(marks);
        initializeUI();
    }

    private void initializeUI(){
        setLayout(new BorderLayout());
        JLayeredPane grid = new JLayeredPane();
        JPanel panelMenu = new JPanel();
        JPanel menu1 = new JPanel();
        JPanel menu2 = new JPanel();
        JPanel numPad = new JPanel();

        grid.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                changeGridDimension(e.getComponent().getSize());
            }
        });
        grid.add(sudokuGrid, JLayeredPane.DEFAULT_LAYER);
        grid.add(marks, JLayeredPane.PALETTE_LAYER);


        menu1.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));
        menu1.setLayout(new GridLayout(1,1));
        menu1.add(new SudokuMenu(sudokuGrid));


        menu2.setBorder(BorderFactory.createEmptyBorder(0, 10, 60, 10));
        menu2.setLayout(new GridLayout(1,1));
        menu2.add(new SudokuMenu2(sudokuGrid, marks));


        numPad.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 170));
        numPad.setLayout(new GridLayout(1,1));
        numPad.add(new SudokuNumPad(sudokuGrid, marks));


        panelMenu.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        panelMenu.setLayout(new BorderLayout());
        panelMenu.add(menu1,BorderLayout.NORTH);
        panelMenu.add(menu2,BorderLayout.SOUTH);

        add(grid, BorderLayout.CENTER);
        add(panelMenu, BorderLayout.EAST);
        add(numPad, BorderLayout.SOUTH);
    }

    private void changeGridDimension(Dimension dimension){
        sudokuGrid.setGridDimension(dimension);
        marks.setMarkDimension(dimension);
        revalidate();
        repaint();
    }
}
