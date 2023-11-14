package Components;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SudokuMarks extends JPanel {
    private JLabel[][][] grid;
    private final Color TC = new Color(0,0,0,0);
    public static final Font FONT = new Font("Arial", Font.BOLD, 15);
    public SudokuMarks() {
        grid = new JLabel[9][9][9];
        buildGrid();
    }
    public void buildGrid(){
        setLayout(new GridLayout(3, 3));
        setOpaque(false);
        setBackground(TC);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Subpanel para cada área de 3x3 //

                JPanel boxPanel = new JPanel(new GridLayout(3, 3));
                boxPanel.setBorder(new LineBorder(TC,2,true));
                boxPanel.setOpaque(true);
                boxPanel.setBackground(TC);
                add(boxPanel);

                for (int row = i * 3; row < i * 3 + 3; row++) {
                    for (int col = j * 3; col < j * 3 + 3; col++) {
                        // Subpanel para cada boton //

                        JPanel butonPanel = new JPanel(new GridLayout(3,3));
                        butonPanel.setBorder(new LineBorder(TC,3,true));
                        butonPanel.setOpaque(true);
                        butonPanel.setBackground(new Color(0,0,0,0));
                        boxPanel.add(butonPanel);

                        for (int label = 0; label < 9; label++){
                            // Label para cada numero en el boton //

                            JLabel number = new JLabel("",JLabel.CENTER);
                            number.setForeground(Color.CYAN);
                            number.setFont(FONT);
                            grid[row][col][label] = number;
                            butonPanel.add(number);
                        }
                    }
                }
            }
        }
    }

    public void setMarksInCell(int row, int col, int mark) {
        String text = String.valueOf(mark+1);
        if (grid[row][col][mark].getText().isEmpty()) {
            grid[row][col][mark].setText(text);
        }else {
            grid[row][col][mark].setText("");
        }
        repaint();
    }

    public void clearMarksInCell(int row, int col){
        for (int i = 0; i < 9; i++) {
            grid[row][col][i].setText("");
        }
        repaint();
    }

    public void setMarkDimension(Dimension gridDimension) {
        setSize(gridDimension);
    }
}
