package gui;

import world.AgentBlock;
import world.Board;

import javax.swing.*;
import java.awt.*;

public class GUIPanel extends JPanel /*implements ActionListener*/ {
    final int PANEL_HEIGHT = 650;
    final int PANEL_WIDTH  = 950;

    private final Image x = new ImageIcon("arts/allPossibleCombination/0.x.png").getImage();
    private final Image v = new ImageIcon("arts/allPossibleCombination/1.v.png").getImage();
    private final Image vg = new ImageIcon("arts/allPossibleCombination/2.vg.png").getImage();
    private final Image vb = new ImageIcon("arts/allPossibleCombination/3.vb.png").getImage();
    private final Image vs = new ImageIcon("arts/allPossibleCombination/4.vs.png").getImage();
    private final Image vgb = new ImageIcon("arts/allPossibleCombination/5.vgb.png").getImage();
    private final Image vgs = new ImageIcon("arts/allPossibleCombination/6.vgs.png").getImage();
    private final Image vgbs = new ImageIcon("arts/allPossibleCombination/7.vgbs.png").getImage();
    private final Image av = new ImageIcon("arts/allPossibleCombination/8.av.png").getImage();
    private final Image avg = new ImageIcon("arts/allPossibleCombination/9.avg.png").getImage();
    private final Image avb = new ImageIcon("arts/allPossibleCombination/10.avb.png").getImage();
    private final Image avs = new ImageIcon("arts/allPossibleCombination/11.avs.png").getImage();
    private final Image avgb = new ImageIcon("arts/allPossibleCombination/12.avgb.png").getImage();
    private final Image avgs = new ImageIcon("arts/allPossibleCombination/13.avgs.png").getImage();
    private final Image avgbs = new ImageIcon("arts/allPossibleCombination/14.avgbs.png").getImage();
    private final Image b = new ImageIcon("arts/allPossibleCombination/15.b.png").getImage();
    private final Image s = new ImageIcon("arts/allPossibleCombination/16.s.png").getImage();
    private final Image bs = new ImageIcon("arts/allPossibleCombination/17.bs.png").getImage();
    private final Image p = new ImageIcon("arts/allPossibleCombination/18.p.png").getImage();
    private final Image w = new ImageIcon("arts/allPossibleCombination/19.w.png").getImage();

    private final AgentBlock[][] agentBlocks;


    public GUIPanel(){
        setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        agentBlocks = Board.getInstance().getBlocks();
    }


    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int i=0;i<10;i++){
            for(int j=0; j<10;j++){
                String state = agentBlocks[i][j].stateForImage();
                switch (state) {
                    case "x" -> g2d.drawImage(x, j * 65, i * 65, 65, 65, null);
                    case "v" -> g2d.drawImage(v, j * 65, i * 65, 65, 65, null);
                    case "vg" -> g2d.drawImage(vg, j * 65, i * 65, 65, 65, null);
                    case "vb" -> g2d.drawImage(vb, j * 65, i * 65, 65, 65, null);
                    case "vs" -> g2d.drawImage(vs, j * 65, i * 65, 65, 65, null);
                    case "vgb" -> g2d.drawImage(vgb, j * 65, i * 65, 65, 65, null);
                    case "vgs" -> g2d.drawImage(vgs, j * 65, i * 65, 65, 65, null);
                    case "vgbs" -> g2d.drawImage(vgbs, j * 65, i * 65, 65, 65, null);
                    case "av" -> g2d.drawImage(av, j * 65, i * 65, 65, 65, null);
                    case "avg" -> g2d.drawImage(avg, j * 65, i * 65, 65, 65, null);
                    case "avb" -> g2d.drawImage(avb, j * 65, i * 65, 65, 65, null);
                    case "avs" -> g2d.drawImage(avs, j * 65, i * 65, 65, 65, null);
                    case "avgb" -> g2d.drawImage(avgb, j * 65, i * 65, 65, 65, null);
                    case "avgs" -> g2d.drawImage(avgs, j * 65, i * 65, 65, 65, null);
                    case "avgbs" -> g2d.drawImage(avgbs, j * 65, i * 65, 65, 65, null);
                    case "b" -> g2d.drawImage(b, j * 65, i * 65, 65, 65, null);
                    case "s" -> g2d.drawImage(s, j * 65, i * 65, 65, 65, null);
                    case "bs" -> g2d.drawImage(bs, j * 65, i * 65, 65, 65, null);
                    case "p" -> g2d.drawImage(p, j * 65, i * 65, 65, 65, null);
                    case "w" -> g2d.drawImage(w, j * 65, i * 65, 65, 65, null);
                }
            }
        }
//        g2d.drawString(GUIFrame.story,670,100);
        g2d.setFont(new Font("Tahoma",Font.BOLD,20));
        g2d.setPaint(Color.RED);
        int y=100;
        if(!GUIFrame.move.equals("")) {
            g2d.drawString(GUIFrame.move,670,y);
            y+=50;
        }
        if(!GUIFrame.gold.equals("")) {
            g2d.drawString(GUIFrame.gold,670,y);
            y+=50;
        }
        if(!GUIFrame.breeze.equals("")) {
            g2d.drawString(GUIFrame.breeze,670,y);
            y+=50;
        }
        if(!GUIFrame.stench.equals("")) {
            g2d.drawString(GUIFrame.stench,670,y);
            y+=50;
        }
        if(!GUIFrame.result.equals("")) {
            g2d.drawString(GUIFrame.result,670,y);
            y+=50;
        }
        if(!GUIFrame.nextMove.equals("")) {
            g2d.drawString(GUIFrame.nextMove,670,y);
        }
    }
    
}
