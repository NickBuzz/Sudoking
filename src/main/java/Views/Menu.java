package Views;

import com.formdev.flatlaf.intellijthemes.FlatHighContrastIJTheme;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {
    public static final Color LINE = new Color(26, 233, 253);
    public static final Color BG = new Color(12, 12, 12);
    public static final Font FONT = new Font("Arial", Font.PLAIN, 25);

    public Menu(){
        initializeUI();

    }

    private void initializeUI() {
        setTitle("SUDOKING");
        setSize(735,610);
        //setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,1));
        setLocationRelativeTo(null);
        add(new SudokuDashboard());
    }



    public static void main(String[] args) {
        setupTheme();
        SwingUtilities.invokeLater(() -> {
            Menu menu = new Menu();
            menu.setVisible(true);
        });
    }

    public static void setupTheme() {
        FlatHighContrastIJTheme.setup();
        UIManager.put("TitlePane.background", BG);
        UIManager.put("TitlePane.foreground", LINE);
        UIManager.put("TitlePane.unifiedBackground", LINE);
        UIManager.put("TitlePane.closeHoverBackground", LINE);
        UIManager.put("TitlePane.closePressedBackground", LINE);
        UIManager.put("TitlePane.font", FONT);
        //UIManager.put("TitlePane.centerTitle", true);

        UIManager.put("OptionPane.background", BG);
        UIManager.put("OptionPane.messageFont", FONT);
        //UIManager.put("OptionPane.foreground", BG);

        UIManager.put("Panel.background", BG);
        UIManager.put("Button.arc", 0);
    }
}
