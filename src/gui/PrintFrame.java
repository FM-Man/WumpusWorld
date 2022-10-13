package gui;

import world.AgentBlock;
import world.Board;

public class PrintFrame {
    private AgentBlock[][] agentBlocks;

    private static PrintFrame instance=null;
    public static PrintFrame getInstance(){
        if(instance==null)
            instance = new PrintFrame();
        return instance;
    }


    private PrintFrame(){
        System.out.println("===============================================================================================");

        agentBlocks = Board.getInstance().getBlocks();
        for (int i=0;i<10;i++){
            for (int j=0;j<10;j++){

                if(i==Board.getInstance().getCurrentBlock().i() &&
                    j==Board.getInstance().getCurrentBlock().j())
                {
                    System.out.print("a");
                }
                else System.out.print(" ");
                System.out.print(agentBlocks[i][j].stateForPrinting());
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
                System.out.print(agentBlocks[i][j].stateForPrinting());
                System.out.print("|");

            }
            System.out.println();
        }

    }
}
