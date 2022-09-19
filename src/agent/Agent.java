package agent;

import common.Instruction;
import gui.GUI_Frame;
import world.AgentBlock;
import world.Board;

import java.util.ArrayList;

public class Agent {
    public Agent(){
        int steps = 0;
//        GUI_Frame.getInstance();
        while (true){

            GUI_Frame.getInstance().updateFrame();

            steps++;
            AgentBlock cb = Board.getInstance().getCurrentBlock();
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
                else if(cb.isStenchy())
                    System.out.println("You smell stench");

                ArrayList<AgentBlock> neighbours = Board.getInstance().getNeighbours(cb.i(),cb.j());
//                Collections.sort(neighbours);

                for (AgentBlock nb: neighbours){
                    if (nb.haveWumpus()){
                        System.out.println("Shoot the wumpus at "+nb.i()+","+nb.j());
                        break;
                    }
                }
                boolean nonVisitedSafeFound = false;
                for (AgentBlock nb:neighbours){
                    if(nb.isSafe() && nb.isUnvisited()){
                        Instruction instruction = getInstruction(cb,nb);
                        System.out.println("Move "+instruction.name());
                        Board.getInstance().setCurrentBlock(instruction);
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
                            Board.getInstance().setCurrentBlock(instruction);
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

            GUI_Frame.getInstance().updateFrame();
        }
    }

    private Instruction getInstruction(AgentBlock cb, AgentBlock nb){
        if(nb.j()==cb.j()+1) return Instruction.RIGHT;
        else if(nb.j()==cb.j()-1) return Instruction.LEFT;
        else if(nb.i()==cb.i()+1) return Instruction.DOWN;
        else return Instruction.UP;
    }

}
