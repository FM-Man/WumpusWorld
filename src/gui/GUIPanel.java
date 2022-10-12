package gui;

import world.AgentBlock;
import world.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIPanel extends JPanel implements ActionListener {
    final int PANEL_HEIGHT = 650;
    final int PANEL_WIDTH  = 650;

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

//    BlockPanel[][] blocks;
    AgentBlock[][] agentBlocks;

    public GUIPanel(){
        setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        agentBlocks = Board.getInstance().getBlocks();
    }


    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
   //    private final JLayeredPane frame;
//    private final JLayeredPane[][] layers = new JLayeredPane[10][10];
//    private AgentBlock[][] agentBlocks;

//    private final ImageIcon agent = new ImageIcon("pixelArt\\pictures\\agent.png");
//    private final ImageIcon breeze = new ImageIcon("pixelArt\\pictures\\Breeze.png");
//    private final ImageIcon cave = new ImageIcon("pixelArt\\pictures\\EmptyCave.png");
//    private final ImageIcon gold = new ImageIcon("pixelArt\\pictures\\gold.png");
//    private final ImageIcon pit = new ImageIcon("pixelArt\\pictures\\pit.png");
//    private final ImageIcon stench = new ImageIcon("pixelArt\\pictures\\Stench.png");
//    private final ImageIcon torch = new ImageIcon("pixelArt\\pictures\\torches.png");
//    private final ImageIcon wumpus = new ImageIcon("pixelArt\\pictures\\wumpus.png");



//    private GUI_Frame(){
//        agentBlocks = Board.getInstance().getBlocks();
//        frame = new JLayeredPane();
//        frame.setBounds(0,0,670,690);
//        for (int i=0;i<10;i++){
//            for (int j=0;j<10;j++){
//                layers[i][j] = new JLayeredPane();
//                layers[i][j].setBounds(j*65,i*65,65,65);
//
//                if(
//                        i==Board.getInstance().getCurrentBlock().i() &&
//                        j==Board.getInstance().getCurrentBlock().j()
//                ){
//                    JLabel l = new JLabel(agent);
//                    l.setBounds(0,0,65,65);
//                    layers[i][j].add(l);
//                }
//                if(!agentBlocks[i][j].isUnvisited()){
//                    JLabel l = new JLabel(torch);
//                    l.setBounds(0,0,65,65);
//                    layers[i][j].add(l);
//                }
//                if(agentBlocks[i][j].isBreezy()){
//                    JLabel l = new JLabel(breeze);
//                    l.setBounds(0,0,65,65);
//                    layers[i][j].add(l);
//                }
//
//                if(agentBlocks[i][j].isStenchy()){
//                    JLabel l = new JLabel(stench);
//                    l.setBounds(0,0,65,65);
//                    layers[i][j].add(l);
//                }
//                if(agentBlocks[i][j].haveWumpus()){
//                    JLabel l = new JLabel(wumpus);
//                    l.setBounds(0,0,65,65);
//                    layers[i][j].add(l);
//                }
//                if(agentBlocks[i][j].hasPit()){
//                    JLabel l = new JLabel(pit);
//                    l.setBounds(0,0,65,65);
//                    layers[i][j].add(l);
//                }
//
//                JLabel l = new JLabel(cave);
//                l.setBounds(0,0,65,65);
//                layers[i][j].add(l);
//
//                frame.add(layers[i][j]);
//            }
//        }
//
//
//
//
//        JFrame jframe = new JFrame("Wumpus World");
//        jframe.add(frame);
//        jframe.setSize(670,690);
//        jframe.setLayout(null);
//        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        jframe.setVisible(true);


//    }

