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

    private int breeze = State.NotKnown;
    private int stench = State.NotKnown;
    private int pit = State.NotKnown;
    private int wumpus = State.NotKnown;

    public AgentBlock(int i, int j, InformationBlock block) {
        this.i = i;
        this.j = j;
        this.block = block;
        visited = pitPossible = wumpusPossible = sureOfPit = sureOfWumpus = false;
    }


    public void neighbourUpdate(boolean hasBreeze, boolean hasStench){
        pitPossible = ((visited || sureOfPit) && pitPossible) || (!visited && !sureOfPit && hasBreeze);
        wumpusPossible = ((visited || sureOfWumpus) && wumpusPossible) || (!(visited || sureOfWumpus) && hasStench);

        sureOfPit = !hasBreeze || sureOfPit;
        sureOfWumpus = !hasStench || sureOfWumpus;

        if(sureOfPit && pitPossible) pit = State.Exists;
        else if(sureOfPit) pit = State.Impossible;
        else if(pitPossible) pit = State.Possible;
        else pit = State.NotKnown;

        if(sureOfWumpus && wumpusPossible) wumpus = State.Exists;
        else if(sureOfWumpus) wumpus = State.Impossible;
        else if(wumpusPossible) wumpus = State.Possible;
        else wumpus = State.NotKnown;
    }

    public int visit(){
        visited = true;
        sureOfWumpus = true;
        sureOfPit = true;
        pitPossible = block.isWithPit();
        wumpusPossible = block.isWithWumpus();


        if(isBreezy()) breeze=State.Exists;
        else breeze=State.Impossible;
        if(isBreezy()) stench = State.Exists;
        else stench = State.Impossible;


        System.out.println(i+","+j+" visited.");
        if(block.isWithGold())
            if(Board.getInstance().goldFound()==1)
                return 1;

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
    public boolean isUnvisited(){
        return !visited;
    }

    public int i(){return i;}
    public int j(){return j;}

    public String state (){
        String s ="";
        s+= visited ? "v" : " ";
        s+= breeze == State.Exists ? "b" : " ";
        s+= stench == State.Exists ? "s" : " ";

        switch (wumpus) {
            case State.Exists -> s += "w";
            case State.Possible -> s += "?";
            default -> s += " ";
        }
        switch (pit) {
            case State.Exists -> s += "p";
            case State.Possible -> s += "?";
            default -> s += " ";
        }

        return s;
    }

}
