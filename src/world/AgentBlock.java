package world;

import java.util.ArrayList;

public class AgentBlock{
    private final int i;
    private final int j;
    private final InformationBlock block;
    private boolean visited;
    private boolean pitPossible;
    private boolean wumpusPossible;
    private boolean sureOfPit;
    private boolean sureOfWumpus;

    public AgentBlock(int i, int j, InformationBlock block) {
        this.i = i;
        this.j = j;
        this.block = block;
        visited = pitPossible = wumpusPossible = sureOfPit = sureOfWumpus = false;
    }


    public void neighbourUpdate(boolean hasBreeze, boolean hasStench){
        pitPossible = ((visited || sureOfPit) && pitPossible) || (!(visited || sureOfPit) && hasBreeze);
        wumpusPossible = ((visited || sureOfWumpus) && wumpusPossible) || (!(visited || sureOfWumpus) && hasStench);

        sureOfPit = !hasBreeze || sureOfPit;
        sureOfWumpus = !hasStench || sureOfWumpus;
    }

    public int visit(){
        System.out.println(i+","+j+" visited.");
        if(block.isWithGold())
            return 1;

        visited = true;
        sureOfWumpus = true;
        sureOfPit = true;
        pitPossible = block.isWithPit();
        wumpusPossible = block.isWithWumpus();

        if(pitPossible || wumpusPossible)
            return -1;
        else {
            ArrayList<AgentBlock> neighbours =Board.getInstance().getNeighbours(i,j);
            for (AgentBlock a:neighbours){
                a.neighbourUpdate(isBreezy(), isStenchy());
            }
            return 0;
        }
    }

    public boolean isSafe(){
        return !(pitPossible || wumpusPossible);
    }
    public boolean haveWumpus(){
        return sureOfWumpus && wumpusPossible;
    }
    public boolean hasPit(){
        return sureOfPit && pitPossible;
    }
    public boolean isBreezy(){
        return block.isBreezy();
    }
    public boolean isStenchy(){
        return  block.isStench();
    }
    public boolean isVisited(){return visited;}

    public int i(){return i;}
    public int j(){return j;}

}
