package agent;

import common.Dijkstra;
import common.Instruction;
import gui.GUIFrame;
import gui.PrintFrame;
import world.AgentBlock;
import world.Board;

import java.util.ArrayList;

public class Agent {

    private ArrayList<AgentBlock> safeUnvisitedBlocks = new ArrayList<>();

    public Agent(){
        int steps = 0;

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
            else if (result==-1){
                System.out.println("Game Over");
                break;
            }
            else {
                if(cb.isBreezy())
                    System.out.println("You feel a breeze");
                if(cb.isStenchy())
                    System.out.println("You smell stench");

                ArrayList<AgentBlock> neighbours = board.getNeighbours(cb.i(),cb.j());

                for (AgentBlock nb: neighbours){
                    if (nb.haveWumpus()){
                        System.out.println("Shoot the wumpus at "+nb.i()+","+nb.j());
                        break;
                    }
                }

                PrintFrame.getInstance().updateFrame();

                boolean nonVisitedSafeFound = false;
                for (AgentBlock nb:neighbours){
                    if(nb.isSafe() && nb.isUnvisited() ){
                        if(!nonVisitedSafeFound) {
                            Instruction instruction = getInstruction(cb, nb);
                            System.out.println("Move " + instruction.name());
                            board.setCurrentBlock(instruction);
                            nonVisitedSafeFound = true;
                        }
                        else {
                            if(!safeUnvisitedBlocks.contains(nb))
                                safeUnvisitedBlocks.add(nb);
                        }
                    }
                }

                if(!nonVisitedSafeFound){
                    if(safeUnvisitedBlocks.size()>=1){
                        AgentBlock t = safeUnvisitedBlocks.get(0);
                        safeUnvisitedBlocks.remove(0);
                        ArrayList<AgentBlock> way = Dijkstra.getPath(cb, t);
                        System.out.println(way);
                        while (way.size() > 2) {
                            gf.update();
                            Instruction instruction = getInstruction(way.get(0), way.get(1));
                            way.remove(0);
                            System.out.println("Move " + instruction.name());
                            board.setCurrentBlock(instruction);

                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            gf.update();
                            steps++;

                            cb = board.getCurrentBlock();
                            System.out.print("STEP-"+ steps +": ");
                            result = cb.visit();

                            if(result==1){
                                break;
                            }
                            else if (result==-1){
                                System.out.println("Game Over");
                                break;
                            }
                            else {
                                if (cb.isBreezy())
                                    System.out.println("You feel a breeze");
                                if (cb.isStenchy())
                                    System.out.println("You smell stench");
                                PrintFrame.getInstance().updateFrame();
                            }
                            gf.update();
                        }
                        Instruction instruction = getInstruction(way.get(0), way.get(1));
                        way.remove(0);
                        System.out.println("Move " + instruction.name());
                        board.setCurrentBlock(instruction);
                    }
                    else{
                        for (AgentBlock nb:neighbours){
                            if(nb.isUnvisited()){
                                Instruction instruction = getInstruction(cb,nb);
                                System.out.println("Move "+instruction.name());
                                Board.getInstance().setCurrentBlock(instruction);
//                                nonVisitedUnsafeFound = true;
                                break;
                            }
                        }
                    }
                }

//                boolean safeFound = false;
//                if(!nonVisitedSafeFound){
//                    for (AgentBlock nb:neighbours){
//                        if(nb.isSafe()){
//                            Instruction instruction = getInstruction(cb,nb);
//                            System.out.println("Move "+instruction.name());
//                            board.setCurrentBlock(instruction);
//                            safeFound = true;
//                            break;
//                        }
//                    }
//                }
//                boolean nonVisitedUnsafeFound = false;
//                if(!safeFound && !nonVisitedSafeFound){
//                    for (AgentBlock nb:neighbours){
//                        if(nb.isUnvisited()){
//                            Instruction instruction = getInstruction(cb,nb);
//                            System.out.println("Move "+instruction.name());
//                            Board.getInstance().setCurrentBlock(instruction);
//                            nonVisitedUnsafeFound = true;
//                            break;
//                        }
//                    }
//                }
                
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
