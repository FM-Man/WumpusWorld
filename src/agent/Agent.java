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
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            gf.clearStory();
            gf.update();


            steps++;

            AgentBlock cb = board.getCurrentBlock();
//            gf.addStory("STEP-"+steps+": ");
            gf.move = "STEP-"+steps+": ";
            System.out.print("STEP-"+ steps +": ");


            int result = cb.visit();

//            gf.addStory(Board.getInstance().goldRemaining()+" golds yet to be found.");
            gf.gold = Board.getInstance().goldRemaining()+" golds yet to be found";
            if(result==1){
                gf.result = "You WON";
                gf.update();
                break;
            }
            else if (result==-1){
                gf.result = "Game Over";
                gf.addStory("Game Over");
                System.out.println("Game Over");
                break;
            }
            else {
                if(cb.isBreezy()) {
                    gf.breeze = "You feel a breeze";
                    gf.addStory("You feel a breeze");
                    System.out.println("You feel a breeze");
                }
                if(cb.isStenchy()) {
                    gf.stench = "You smell stench";
                    gf.addStory("You smell stench");
                    System.out.println("You smell stench");
                }
                ArrayList<AgentBlock> neighbours = board.getNeighbours(cb.i(),cb.j());

                for (AgentBlock nb: neighbours){
                    if (nb.haveWumpus()){

                        gf.addStory("Shoot the wumpus at "+nb.i()+","+nb.j());
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
                            gf.nextMove = "Move " + instruction.name();
                            gf.addStory("Move " + instruction.name());
                            System.out.println("Move " + instruction.name());
                            board.setCurrentBlock(instruction);
                            nonVisitedSafeFound = true;
                        }
                        else {
                            if(!safeUnvisitedBlocks.contains(nb))
                                safeUnvisitedBlocks.add(0,nb);
                        }
                    }
                }

                if(!nonVisitedSafeFound){
                    if(safeUnvisitedBlocks.size()>=1){
                        steps = followSetPath(safeUnvisitedBlocks, cb, gf, board, steps, result);
                    }
                    else{
                        ArrayList<AgentBlock> unsafes = new ArrayList<>();
                        for(int i=0;i<10;i++){
                            for(int j=0; j<10;j++){
                                if(!board.getBlocks()[i][j].isSafe() && board.getBlocks()[i][j].atLeastOneNeighbourIsVisited()){
                                    boolean added = false;
                                    for (int k=0;k<unsafes.size();k++){
                                        if(board.getBlocks()[i][j].getDegreeOfUnsafety() < unsafes.get(k).getDegreeOfUnsafety()) {
                                            unsafes.add(k,board.getBlocks()[i][j]);
                                            added = true;
                                            break;
                                        }
                                    }
                                    if (!added) unsafes.add(board.getBlocks()[i][j]);
                                }
                            }
                        }

                        steps = followSetPath(unsafes, cb, gf, board, steps, result);

//
//
//
//
//
//
//
//
//
//                        for (AgentBlock nb:neighbours){
//                            if(nb.isUnvisited()){
//                                Instruction instruction = getInstruction(cb,nb);
//                                gf.nextMove = "Move " + instruction.name();
//                                gf.addStory("Move " + instruction.name());
//                                System.out.println("Move "+instruction.name());
//                                Board.getInstance().setCurrentBlock(instruction);
////                                nonVisitedUnsafeFound = true;
//                                break;
//                            }
//                        }
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

    private int followSetPath(ArrayList<AgentBlock> listOfPossibilities, AgentBlock cb, GUIFrame gf, Board board, int steps, int result) {
//        int result;
        AgentBlock t = listOfPossibilities.get(0);
        listOfPossibilities.remove(0);
        ArrayList<AgentBlock> way = Dijkstra.getPath(cb, t);
        System.out.println(way);
        while (way.size() > 2) {
            gf.update();
            Instruction instruction = getInstruction(way.get(0), way.get(1));
            way.remove(0);
            gf.nextMove = "Move " + instruction.name();
            gf.addStory("Move " + instruction.name());
            System.out.println("Move " + instruction.name());
            board.setCurrentBlock(instruction);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            gf.clearStory();
            gf.update();
            steps++;

            cb = board.getCurrentBlock();
            gf.move = "STEP-" + steps + ": ";
            gf.addStory("Move " + instruction.name());

            System.out.print("STEP-"+ steps +": ");
            result = cb.visit();

            gf.addStory(Board.getInstance().goldRemaining()+" golds yet to be found.");
            if(result ==1){
                gf.result = "You WON";
                gf.update();
                break;
            }
            else if (result ==-1){
                gf.result = "Game Over";
                gf.addStory("Game Over");
                System.out.println("Game Over");
                break;
            }
            else {
                if (cb.isBreezy()) {
                    gf.breeze = "You feel a breeze";
                    gf.addStory("You feel a breeze");
                    System.out.println("You feel a breeze");
                }
                if (cb.isStenchy()) {
                    gf.stench = "You smell a stench";
                    gf.addStory("You feel a breeze");
                    System.out.println("You smell stench");
                }
                PrintFrame.getInstance().updateFrame();
            }
            gf.update();
        }
        Instruction instruction = getInstruction(way.get(0), way.get(1));
        way.remove(0);
        gf.nextMove = "Move " + instruction.name();
        gf.addStory("Move " + instruction.name());
        System.out.println("Move " + instruction.name());
        board.setCurrentBlock(instruction);
        return steps;
    }

    private Instruction getInstruction(AgentBlock cb, AgentBlock nb){
        if(nb.j()==cb.j()+1) return Instruction.RIGHT;
        else if(nb.j()==cb.j()-1) return Instruction.LEFT;
        else if(nb.i()==cb.i()+1) return Instruction.DOWN;
        else return Instruction.UP;
    }

}
