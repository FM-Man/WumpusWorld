package world;

import common.Position;
import gui.GUIFrame;

import java.util.ArrayList;

public class AgentBlock implements Comparable<AgentBlock>{
    public final Position position;
//    private final int i;
//    private final int j;
    private final InformationBlock block;

    private ArrayList<AgentBlock> neighbours;

    private boolean visited;
    private boolean visitedBefore=false;

    private int breeze = State.NotKnown;
    private int stench = State.NotKnown;
    private int pit = State.NotKnown;
    private int wumpus = State.NotKnown;

    public AgentBlock(int i, int j, InformationBlock block) {
        position = new Position(i,j);
//        this.i = i;
//        this.j = j;
        this.block = block;
        visited =  false;
    }

    public void setNeighbours(){
        neighbours = Board.getInstance().getNeighbours(this.position);
    }


    public int visit(){
        visited = true;

        if(block.isWithPit()) pit=State.Exists;
        else pit=State.Impossible;
        if(block.isWithWumpus()) wumpus=State.Exists;
        else wumpus = State.Impossible;

        if(isBreezy()) breeze=State.Exists;
        else breeze=State.Impossible;
        if(isStenchy()) stench = State.Exists;
        else stench = State.Impossible;




        System.out.println(position.ij+" visited.");
        GUIFrame.move+=position.ij+" visited";
        if(block.isWithGold() && !visitedBefore)
            if(Board.getInstance().goldFound()==1) {
                visitedBefore = true;
                return 1;
            }

        if(pit == State.Exists || wumpus == State.Exists) {
            visitedBefore = true;
            return -1;
        }
        else {
            for (AgentBlock a:neighbours){
                if(isBreezy())  a.neighbourBreeze(State.Exists,this);
                else            a.neighbourBreeze(State.Impossible,this);

                if(isStenchy()) a.neighbourStench(State.Exists,this);
                else            a.neighbourStench(State.Impossible,this);
            }
            visitedBefore = true;
            return 0;
        }

    }




    public boolean isSafe(){
        return pit == State.Impossible && wumpus == State.Impossible;
    }
    public boolean haveWumpus(){
        return wumpus == State.Exists;
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


    public int getStench(){return stench;}
    public int getBreeze(){return breeze;}


//    public int i(){return i;}
//    public int j(){return j;}

    public String stateForPrinting(){
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

    public String stateForImage(){
        String retString = "";
        if(Board.getInstance().getCurrentBlock().position.equals(position))
        {
            if(!visited)
                visited = true;
            retString += "a";
        }
        if(visited){
            retString += "v";
        }
        if (visited && block.isWithGold()){
            retString+= "g";
        }
        if(visited && isBreezy()){
            retString += "b";
        }
        if(visited && isStenchy()){
            retString += "s";
        }
        if(pit == State.Exists){
            retString += "p";
        }
        if(wumpus == State.Exists){
            retString += "w";
        }

        if(retString.equals(""))
            retString = "x";

        return retString;
    }


    public int getPit() {
        return pit;
    }
    public int getWumpus() {
        return wumpus;
    }



    public boolean onlyOneNeighbourPitPossible(){
        int pp = 0;
        for(AgentBlock n:neighbours){
            if(n.getPit()!=State.Impossible)
                pp++;
        }
        return pp==1;
    }
    public void confirmNeighbourPit(){
        for (AgentBlock n:neighbours){
            if(n.getPit()!=State.Impossible){
                n.pit = State.Exists;
            }
        }
    }
    public boolean onlyOneNeighbourWumpusPossible(){
        int pp = 0;
        for(AgentBlock n:neighbours){
            if(n.getWumpus()!=State.Impossible)
                pp++;
        }
        return pp==1;
    }
    public void confirmNeighbourWumpus(){
        for (AgentBlock n:neighbours){
            if(n.getWumpus()!=State.Impossible){
                n.wumpus = State.Exists;
            }
        }
    }


    public void neighbourBreeze(int state, AgentBlock n){
        switch (state){
            case State.Impossible -> {
                pit = State.Impossible;
                for (AgentBlock neighbour: neighbours) {
                    if(!neighbour.equals(n) && neighbour.isUnvisited())
                        neighbour.neighbourPit(pit, this);
                }
            }
            case State.Exists -> {
                if(!visited && pit != State.Exists && pit != State.Impossible) {
                    pit = State.Possible;
                    for (AgentBlock neighbour : neighbours)
                        if (!neighbour.equals(n))
                            neighbour.neighbourPit(pit, this);
                }
            }
        }
    }

    public void neighbourPit(int state, AgentBlock n) {
        switch (state){
            case State.Exists -> {
                breeze = State.Exists;
                for (AgentBlock neighbour: neighbours)
                    if(!neighbour.equals(n))
                        neighbour.neighbourBreeze(breeze, this);
            }
            case State.Impossible -> {
                if(breeze==State.Exists && onlyOneNeighbourPitPossible())
                    confirmNeighbourPit();
            }
            case State.Possible ->
                breeze = State.Possible;
        }
    }

    public void neighbourStench(int state, AgentBlock n){
        switch (state){
            case State.Impossible -> {
                wumpus = State.Impossible;
                for (AgentBlock neighbour: neighbours)
                    if(!neighbour.equals(n))
                        neighbour.neighbourWumpus(wumpus,this);
            }
            case State.Exists -> {
                if(!visited && wumpus != State.Exists && wumpus != State.Impossible) {
                    wumpus = State.Possible;
                    for (AgentBlock neighbour : neighbours)
                        if (!neighbour.equals(n))
                            neighbour.neighbourWumpus(wumpus, this);
                }
            }
        }
    }

    public void neighbourWumpus(int state, AgentBlock n){
        switch (state){
            case State.Exists -> {
                stench = State.Exists;
                for (AgentBlock neighbour: neighbours)
                    if(!neighbour.equals(n))
                        neighbour.neighbourStench(stench,this);
            }
            case State.Impossible -> {
                if(stench == State.Exists && onlyOneNeighbourWumpusPossible())
                    confirmNeighbourWumpus();
            }
            case State.Possible ->
                    stench = State.Possible;
        }
    }


    public int getDegreeOfUnsafety(){
        int degree = 0;
        if(pit == State.Exists || wumpus == State.Exists) {
            return Integer.MAX_VALUE;
        }
        if(pit == State.Impossible && wumpus == State.Impossible){
            return Integer.MIN_VALUE;
        }
        if(pit == State.Possible){
            for (AgentBlock nb: neighbours){
                if(nb.breeze == State.Exists) degree++;
            }
        }
        if(wumpus == State.Possible){
            for (AgentBlock nb: neighbours){
                if(nb.wumpus == State.Exists) degree++;
            }
        }
        return degree/neighbours.size();
    }




/**           djicksta stuffs        **/

    public int distance = Integer.MAX_VALUE;
    public AgentBlock from = null;
    public boolean considered = false;

    public void consider(AgentBlock from){
        if(from != null)    distance = from.distance + 1;
        else                distance = 0;

        this.from = from;
        considered = true;
    }

    public void refresh(){
        distance = Integer.MAX_VALUE;
        from = null;
        considered = false;
    }

    @Override
    public int compareTo(AgentBlock o) {
        return distance-o.distance;
    }

    public String toString(){
        return position.i+","+position.j;
    }
}
