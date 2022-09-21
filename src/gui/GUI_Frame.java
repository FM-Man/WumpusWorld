package gui;

import world.AgentBlock;
import world.Board;

public class GUI_Frame {
    private AgentBlock[][] agentBlocks;

    private static GUI_Frame instance=null;
    public static GUI_Frame getInstance(){
        if(instance==null)
            instance = new GUI_Frame();
        return instance;
    }


    private GUI_Frame(){
        System.out.println("===============================================================================================");

        agentBlocks = Board.getInstance().getBlocks();
        for (int i=0;i<10;i++){
            for (int j=0;j<10;j++){

                if(
                        i==Board.getInstance().getCurrentBlock().i() &&
                                j==Board.getInstance().getCurrentBlock().j()
                ){
                    System.out.print("a");
                }
                else System.out.print(" ");
                System.out.print(agentBlocks[i][j].state());
                System.out.print("|");

            }
            System.out.println();
        }


    }

    public void updateFrame(){


        System.out.println("===========================================================================================");

        agentBlocks = Board.getInstance().getBlocks();
        for (int i=0;i<10;i++){
            for (int j=0;j<10;j++){

                if(
                        i==Board.getInstance().getCurrentBlock().i() &&
                                j==Board.getInstance().getCurrentBlock().j()
                ){
                    System.out.print("a");
                }
                else System.out.print(" ");

                System.out.print(agentBlocks[i][j].state());
                System.out.print("|");

            }
            System.out.println();
        }

    }
}
