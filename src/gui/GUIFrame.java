package gui;

import javax.swing.*;

public class GUIFrame extends JFrame {
    GUIPanel panel;
    public GUIFrame(){
        panel = new GUIPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    public void update(){
        panel.repaint();
    }
}
