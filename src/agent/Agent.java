package agent;

import common.Dijkstra;
import common.Instruction;
import gui.GUIFrame;
import gui.PrintFrame;
import world.AgentBlock;
import world.Board;

import java.util.ArrayList;

public class Agent {
    private final int sleepTime = 1000;

    public Agent(){
        int steps = 0;

        Board board = Board.getInstance();
        board.updateNeighbourListForEachBlock();

        GUIFrame gf = new GUIFrame();
        while (true){
            board.reconfirmWumpusAndPit();

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            gf.clearStory();
            gf.update();


            steps++;

            AgentBlock cb = board.getCurrentBlock();
            GUIFrame.move = "STEP-"+steps+": ";
            System.out.print("STEP-"+ steps +": ");


            int result = cb.visit();

            GUIFrame.gold = Board.getInstance().goldRemaining()+" golds yet to be found";
            if(result==1){
                GUIFrame.result = "You WON";
                gf.update();
                break;
            }
            else if (result==-1){
                GUIFrame.result = "Game Over";
                GUIFrame.addStory("Game Over");
                System.out.println("Game Over");
                break;
            }
            else {
                if(cb.isBreezy()) {
                    GUIFrame.breeze = "You feel a breeze";
                    GUIFrame.addStory("You feel a breeze");
                    System.out.println("You feel a breeze");
                }
                if(cb.isStenchy()) {
                    GUIFrame.stench = "You smell stench";
                    GUIFrame.addStory("You smell stench");
                    System.out.println("You smell stench");
                }
                ArrayList<AgentBlock> neighbours = board.getNeighbours(cb.position);

                for (AgentBlock nb: neighbours){
                    if (nb.haveWumpus()){

                        GUIFrame.addStory("Shoot the wumpus at "+nb.position.ij);
                        System.out.println("Shoot the wumpus at "+nb.position.ij);
                        break;
                    }
                }

                PrintFrame.getInstance().updateFrame();

                boolean nonVisitedSafeFound = false;
                for (AgentBlock nb:neighbours){
                    if(nb.isSafe() && nb.isUnvisited() ){
                        Instruction instruction = getInstruction(cb, nb);
                        GUIFrame.nextMove = "Move " + instruction.name();
                        GUIFrame.addStory("Move " + instruction.name());
                        System.out.println("Move " + instruction.name());
                        board.setCurrentBlock(instruction);
                        nonVisitedSafeFound = true;
                        break;
                    }
                }

                if(!nonVisitedSafeFound){
                    ArrayList<AgentBlock> safeUnvisitedBlocks = board.getSafes();
                    if(safeUnvisitedBlocks.size()>=1){
                        steps = followSetPath(safeUnvisitedBlocks, cb, gf, board, steps);
                    }
                    else{
                        ArrayList<AgentBlock> unsafes = board.getUnsafes();
                        steps = followSetPath(unsafes, cb, gf, board, steps);
                    }
                }

            }

            gf.update();
        }
    }

    private int followSetPath(ArrayList<AgentBlock> listOfPossibilities, AgentBlock cb, GUIFrame gf, Board board, int steps) {

        AgentBlock t = listOfPossibilities.get(0);
        listOfPossibilities.remove(0);
        ArrayList<AgentBlock> way = Dijkstra.getPath(cb, t);
        System.out.println(way);
        while (way.size() > 2) {
            board.reconfirmWumpusAndPit();

            gf.update();
            Instruction instruction = getInstruction(way.get(0), way.get(1));
            way.remove(0);
            GUIFrame.nextMove = "Move " + instruction.name();
            GUIFrame.addStory("Move " + instruction.name());
            System.out.println("Move " + instruction.name());
            board.setCurrentBlock(instruction);

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            gf.clearStory();
            gf.update();
            steps++;

            cb = board.getCurrentBlock();
            GUIFrame.move = "STEP-" + steps + ": ";
            GUIFrame.addStory("Move " + instruction.name());

            System.out.print("STEP-"+ steps +": ");
            int result = cb.visit();

            GUIFrame.addStory(Board.getInstance().goldRemaining()+" golds yet to be found.");
            if(result ==1){
                GUIFrame.result = "You WON";
                gf.update();
                break;
            }
            else if (result ==-1){
                GUIFrame.result = "Game Over";
                GUIFrame.addStory("Game Over");
                System.out.println("Game Over");
                break;
            }
            else {
                if (cb.isBreezy()) {
                    GUIFrame.breeze = "You feel a breeze";
                    GUIFrame.addStory("You feel a breeze");
                    System.out.println("You feel a breeze");
                }
                if (cb.isStenchy()) {
                    GUIFrame.stench = "You smell a stench";
                    GUIFrame.addStory("You feel a breeze");
                    System.out.println("You smell stench");
                }
                PrintFrame.getInstance().updateFrame();
            }
            gf.update();
        }
        Instruction instruction = getInstruction(way.get(0), way.get(1));
        way.remove(0);
        GUIFrame.nextMove = "Move " + instruction.name();
        GUIFrame.addStory("Move " + instruction.name());
        System.out.println("Move " + instruction.name());
        board.setCurrentBlock(instruction);
        return steps;
    }

    private Instruction getInstruction(AgentBlock cb, AgentBlock nb){
        if(nb.position.j==cb.position.j+1) return Instruction.RIGHT;
        else if(nb.position.j==cb.position.j-1) return Instruction.LEFT;
        else if(nb.position.i==cb.position.i+1) return Instruction.DOWN;
        else return Instruction.UP;
    }

}
