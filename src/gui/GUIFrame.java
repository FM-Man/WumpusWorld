package gui;

import javax.swing.*;

public class GUIFrame extends JFrame {
    GUIPanel panel;
    public static String story = "";
    public static String move = "";
    public static String gold = "";
    public static String result = "";
    public static String breeze = "";
    public static String stench = "";
    public static String nextMove = "";
    public GUIFrame(){
        panel = new GUIPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Wumpus World");
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    public void update(){
        panel.repaint();
    }

    public void clearStory(){
        story = "";
        move = "";
        gold = "";
        result = "";
        breeze = "";
        stench = "";
        nextMove = "";
    }

    public static void addStory(String plotLine){
        story+="\n"+plotLine;
    }
}
