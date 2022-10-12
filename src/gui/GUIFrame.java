package gui;

import javax.swing.*;

public class GUIFrame extends JFrame {
    GUIPanel panel;
    GUIFrame(){
        panel = new GUIPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
