package agent;

import common.Instruction;
import gui.GUIFrame;
import gui.GUI_Frame;
import world.AgentBlock;
import world.Board;

import java.util.ArrayList;

public class Agent {
    public Agent(){
        int steps = 0;
//        GUI_Frame.getInstance();
        Board board = Board.getInstance();
        board.updateNeighbourListForEachBlock();

        GUIFrame gf = new GUIFrame();
        while (true){

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            gf.update();


            steps++;

            AgentBlock cb = board.getCurrentBlock();
            System.out.print("STEP-"+ steps +": ");
            int result = cb.visit();

            if(result==1){
                break;
            }
            if (result==-1){
                System.out.println("Game Over");
                break;
            }
            else {
                if(cb.isBreezy())
                    System.out.println("You feel a breeze");
                if(cb.isStenchy())
                    System.out.println("You smell stench");

                ArrayList<AgentBlock> neighbours = board.getNeighbours(cb.i(),cb.j());
//                Collections.sort(neighbours);

                for (AgentBlock nb: neighbours){
                    if (nb.haveWumpus()){
                        System.out.println("Shoot the wumpus at "+nb.i()+","+nb.j());
                        break;
                    }
                }

                GUI_Frame.getInstance().updateFrame();

                boolean nonVisitedSafeFound = false;
                for (AgentBlock nb:neighbours){
                    if(nb.isSafe() && nb.isUnvisited()){
                        Instruction instruction = getInstruction(cb,nb);
                        System.out.println("Move "+instruction.name());
                        board.setCurrentBlock(instruction);
                        nonVisitedSafeFound = true;
                        break;
                    }
                }
                boolean safeFound = false;
                if(!nonVisitedSafeFound){
                    for (AgentBlock nb:neighbours){
                        if(nb.isSafe()){
                            Instruction instruction = getInstruction(cb,nb);
                            System.out.println("Move "+instruction.name());
                            board.setCurrentBlock(instruction);
                            safeFound = true;
                            break;
                        }
                    }
                }
                boolean nonVisitedUnsafeFound = false;
                if(!safeFound && !nonVisitedSafeFound){
                    for (AgentBlock nb:neighbours){
                        if(nb.isUnvisited()){
                            Instruction instruction = getInstruction(cb,nb);
                            System.out.println("Move "+instruction.name());
                            Board.getInstance().setCurrentBlock(instruction);
                            nonVisitedUnsafeFound = true;
                            break;
                        }
                    }
                }
                
            }



            gf.update();

        }
    }

    private Instruction getInstruction(AgentBlock cb, AgentBlock nb){
        if(nb.j()==cb.j()+1) return Instruction.RIGHT;
        else if(nb.j()==cb.j()-1) return Instruction.LEFT;
        else if(nb.i()==cb.i()+1) return Instruction.DOWN;
        else return Instruction.UP;
    }

}
