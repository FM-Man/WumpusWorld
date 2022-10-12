package world;

import java.util.ArrayList;

public class AgentBlock{
    private final int i;
    private final int j;
    private final InformationBlock block;

    private ArrayList<AgentBlock> neighbours;

    private boolean visited;
//    private boolean pitPossible;
//    private boolean wumpusPossible;
//    private boolean sureOfPit;
//    private boolean sureOfWumpus;

    private int breeze = State.NotKnown;
    private int stench = State.NotKnown;
    private int pit = State.NotKnown;
    private int wumpus = State.NotKnown;

    public AgentBlock(int i, int j, InformationBlock block) {
        this.i = i;
        this.j = j;
        this.block = block;
        visited = /*pitPossible = wumpusPossible = sureOfPit = sureOfWumpus =*/ false;

//        neighbours = Board.getInstance().getNeighbours(this.i,this.j);
    }

    public void setNeighbours(){
        neighbours = Board.getInstance().getNeighbours(this.i,this.j);
    }

//    public void neighbourUpdate(boolean hasBreeze, boolean hasStench){
//        pitPossible = ((visited || sureOfPit) && pitPossible) || (!visited && !sureOfPit && hasBreeze);
//        wumpusPossible = ((visited || sureOfWumpus) && wumpusPossible) || (!(visited || sureOfWumpus) && hasStench);
//
//        sureOfPit = !hasBreeze || sureOfPit;
//        sureOfWumpus = !hasStench || sureOfWumpus;
//
//
//
////        if (hasBreeze) neighbourBreeze(State.Exists);
////        else neighbourBreeze(State.Impossible);
////        if(hasStench) neighbourStench(State.Exists);
////        else neighbourStench(State.Impossible);
//
//
//
//        if(sureOfPit && pitPossible) pit = State.Exists;
//        else if(sureOfPit) pit = State.Impossible;
//        else if(pitPossible) pit = State.Possible;
//        else pit = State.NotKnown;
//
//        if(sureOfWumpus && wumpusPossible) wumpus = State.Exists;
//        else if(sureOfWumpus) wumpus = State.Impossible;
//        else if(wumpusPossible) wumpus = State.Possible;
//        else wumpus = State.NotKnown;
//    }

    public int visit(){
        visited = true;
//        sureOfWumpus = true;
//        sureOfPit = true;
//        pitPossible = block.isWithPit();
//        wumpusPossible = block.isWithWumpus();



        if(block.isWithPit()) pit=State.Exists;
        else pit=State.Impossible;
        if(block.isWithWumpus()) wumpus=State.Exists;
        else wumpus = State.Impossible;

        if(isBreezy()) breeze=State.Exists;
        else breeze=State.Impossible;
        if(isStenchy()) stench = State.Exists;
        else stench = State.Impossible;




        System.out.println(i+","+j+" visited.");
        if(block.isWithGold())
            if(Board.getInstance().goldFound()==1)
                return 1;
//        if(pitPossible || wumpusPossible)
        if(pit == State.Exists || wumpus == State.Exists)
            return -1;
        else {
            for (AgentBlock a:neighbours){
//                a.neighbourUpdate(isBreezy(), isStenchy());
                if(isBreezy())
                    a.neighbourBreeze(State.Exists,this);
                else a.neighbourBreeze(State.Impossible,this);
                if(isStenchy())
                    a.neighbourStench(State.Exists,this);
                else a.neighbourStench(State.Impossible,this);
            }
            return 0;
        }
    }




    public boolean isSafe(){
//        return !(pitPossible || wumpusPossible);
        return pit == State.Impossible && wumpus == State.Impossible;
    }
    public boolean haveWumpus(){
//        return sureOfWumpus && wumpusPossible;
        return wumpus == State.Exists;
    }
    public boolean hasPit(){
//        return sureOfPit && pitPossible;
        return pit == State.Exists;
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
        if(Board.getInstance().getCurrentBlock().i() == i &&
            Board.getInstance().getCurrentBlock().j() == j)
        {
            retString += "a";
        }
        if(visited){
            retString += "v";
        }
        if (block.isWithGold()){
            retString+= "g";
        }
        if(isBreezy()){
            retString += "b";
        }
        if(isStenchy()){
            retString += "s";
        }
        if(pit == State.Exists){
            retString += "p";
        }
        if(wumpus == State.Exists){
            retString += "w";
        }


//
//        switch (retString) {
//            case "":
//                retString = "0.x";
//                break;
//            case "v":
//                retString = "1." + retString;
//                break;
//            case "vg":
//                retString = "2." + retString;
//                break;
//            case "vb":
//                retString = "3." + retString;
//                break;
//            case "vs":
//                retString = "4." + retString;
//                break;
//            case "vgb":
//                retString = "5." + retString;
//                break;
//            case "vgs":
//                retString = "6." + retString;
//                break;
//            case "vgbs":
//                retString = "7." + retString;
//                break;
//            case "av":
//                retString = "8." + retString;
//                break;
//            case "avg":
//                retString = "9." + retString;
//                break;
//            case "avb":
//                retString = "10." + retString;
//                break;
//            case "avs":
//                retString = "11." + retString;
//                break;
//            case "avgb":
//                retString = "12." + retString;
//                break;
//            case "avgs":
//                retString = "13." + retString;
//                break;
//            case "avgbs":
//                retString = "14." + retString;
//                break;
//            case "b":
//                retString = "15." + retString;
//                break;
//            case "s":
//                retString = "16." + retString;
//                break;
//            case "bs":
//                retString = "17." + retString;
//                break;
//            case "p":
//                retString = "18." + retString;
//                break;
//            case "w":
//                retString = "19." + retString;
//                break;
//            default:
//                try {
//                    throw new Exception("Unknown State at " + i + " " + j);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//        }

//        return retString+".png";


        if(retString.equals(""))
            retString = "x";

        return retString;
    }


//
//    public int getBreeze() {
//        return breeze;
//    }
//    public int getStench() {
//        return stench;
//    }
    public int getPit() {
        return pit;
    }
    public int getWumpus() {
        return wumpus;
    }



    public boolean onlyOneNeighbourPitPossible(){
        int pp = 0;
        for(AgentBlock n:neighbours){
            if(n.getPit()==State.Possible)
                pp++;
        }
        return pp==1;
    }
    public void confirmNeighbourPit(){
        for (AgentBlock n:neighbours){
            if(n.getPit()==State.Possible){
                n.pit = State.Exists;
            }
        }
    }
    public boolean onlyOneNeighbourWumpusPossible(){
        int pp = 0;
        for(AgentBlock n:neighbours){
            if(n.getWumpus()==State.Possible)
                pp++;
        }
        return pp==1;
    }
    public void confirmNeighbourWumpus(){
        for (AgentBlock n:neighbours){
            if(n.getWumpus()==State.Possible){
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
                if(isBreezy() && onlyOneNeighbourPitPossible())
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
                if(isStenchy() && onlyOneNeighbourWumpusPossible())
                    confirmNeighbourWumpus();
            }
            case State.Possible ->
                    stench = State.Possible;
        }
    }

}
