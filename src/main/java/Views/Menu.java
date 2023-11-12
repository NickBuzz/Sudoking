package Views;

import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {

    public Menu(){
        initializeUI();

    }

    private void initializeUI() {
        setTitle("SUDOKING");
        setSize(735,610);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,1));
        setLocationRelativeTo(null);
        add(new SudokuDashboard());
    }



    public static void main(String[] args) {
        FlatArcDarkIJTheme.setup();
        SwingUtilities.invokeLater(() -> {
            Menu menu = new Menu();
            menu.setVisible(true);
        });
    }
}
