package gui;

import world.AgentBlock;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GUI_Frame {
    private final JLayeredPane frame;
    private final JLayeredPane[][] layers = new JLayeredPane[10][10];
    private AgentBlock[][] agentBlocks;

    private final ImageIcon agent = new ImageIcon("pixelArt\\pictures\\agent.png");
    private final ImageIcon breeze = new ImageIcon("pixelArt\\pictures\\Breeze.png");
    private final ImageIcon cave = new ImageIcon("pixelArt\\pictures\\EmptyCave.png");
    private final ImageIcon gold = new ImageIcon("pixelArt\\pictures\\gold.png");
    private final ImageIcon pit = new ImageIcon("pixelArt\\pictures\\pit.png");
    private final ImageIcon stench = new ImageIcon("pixelArt\\pictures\\Stench.png");
    private final ImageIcon torch = new ImageIcon("pixelArt\\pictures\\torches.png");
    private final ImageIcon wumpus = new ImageIcon("pixelArt\\pictures\\wumpus.png");

    private static GUI_Frame instance=null;
    public static GUI_Frame getInstance(){
        if(instance==null)
            instance = new GUI_Frame();
        return instance;
    }


    private GUI_Frame(){
        agentBlocks = 
        frame = new JLayeredPane();
        frame.setBounds(0,0,670,690);
        for (int i=0;i<10;i++){
            for (int j=0;j<10;j++){
                layers[i][j] = new JLayeredPane();
                layers[i][j].setBounds(j*65,i*65,65,65);
                layers[i][j].setOpaque(true);
                layers[i][j].setBackground(new Color(i*20,j*20,i*j));

                JLabel l = new JLabel(cave);
                l.setBounds(0,0,65,65);
                layers[i][j].add(l);

                frame.add(layers[i][j]);
            }
        }




        JFrame jframe = new JFrame("Wumpus World");
        jframe.add(frame);
        jframe.setSize(670,690);
        jframe.setLayout(null);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);
    }

    public void updateFrame(){

    }
}
