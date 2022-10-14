package world;

import common.Instruction;
import common.PQ;
import common.Position;
import gui.GUIFrame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {

    private final String inputFilePath = "input2.txt";
    private final boolean random = false;

    private final int dimension=10;
    private final AgentBlock[][] blocks;
    private AgentBlock currentBlock;
    private int numberOfGold;
    private int goldFound;


    private static Board instance = null;
    private Board() {
        blocks = new AgentBlock[dimension][dimension];

        File inputFile = new File(inputFilePath);
        Scanner scanner;
        try {
             scanner= new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        int[][] informationArray = new int[dimension][dimension];
        final int AGENT = 1;
        final int WUMPUS = 2;
        final int SAFE = 0;
        final int GOLD = 4;
        final int PIT = 3;
        if(!random){
            for (int i = 0; scanner.hasNextLine(); i++) {
                String[] line = scanner.nextLine().split(",", 0);
                for (int j = 0; j < line.length; j++) {
                    switch (line[j]) {
                        case "a", "A" -> informationArray[i][j] = AGENT;
                        case "w", "W" -> informationArray[i][j] = WUMPUS;
                        case "s", "S" -> informationArray[i][j] = SAFE;
                        case "g", "G" -> informationArray[i][j] = GOLD;
                        default -> informationArray[i][j] = PIT;
                    }
                }
            }
        }
        else {
            Position agentPosition = new Position((int)(Math.random()*100));
            int numberOfPit = (int) (Math.random()*10+5);
            int numberOfWumpus = (int)(Math.random()*2+1);
            int numberOfGold = (int)(Math.random()*5+5);
            ArrayList<Position> goldPositions = new ArrayList<>();
            for (int i=0;i<numberOfGold;i++){
                Position p= new Position((int)(Math.random()*100));
                if(!goldPositions.contains(p) && !p.equals(agentPosition)) goldPositions.add(p);
                else i--;
            }
            ArrayList<Position> pitPositions = new ArrayList<>();
            for (int i=0;i<numberOfPit;i++){
                Position p= new Position((int)(Math.random()*100));
                if(!goldPositions.contains(p)
                        && !pitPositions.contains(p)
                        && !p.equals(agentPosition)
                        && !p.adjacent(agentPosition)
                )
                    pitPositions.add(p);
                else i--;
            }
            ArrayList<Position> wumpusPositions = new ArrayList<>();
            for (int i=0;i<numberOfWumpus;i++){
                Position p= new Position((int)(Math.random()*100));
                if(!goldPositions.contains(p)
                        && !pitPositions.contains(p)
                        && !wumpusPositions.contains(p)
                        && !p.equals(agentPosition)
                        && !p.adjacent(agentPosition)
                )
                    wumpusPositions.add(p);
                else i--;
            }
            for(Position p:goldPositions){
                int blocked =0;
                for (int i=0; i<p.getAdjacents().size();i++){
                    if(pitPositions.contains(p.getAdjacents().get(i))
                            || wumpusPositions.contains(p.getAdjacents().get(i))
                    ) blocked++;
                }
                if (blocked==p.getAdjacents().size()) goldPositions.remove(p);
            }

            for (int i=0;i<10;i++){
                for(int j=0;j<10;j++){
                    Position p = new Position(i,j);
                    if(goldPositions.contains(p))
                        informationArray[i][j] = GOLD;
                    else if(pitPositions.contains(p))
                        informationArray[i][j] = PIT;
                    else if(agentPosition.equals(p))
                        informationArray[i][j] = AGENT;
                    else if(wumpusPositions.contains(p))
                        informationArray[i][j] = WUMPUS;
                    else informationArray[i][j] = SAFE;
                }
            }

        }


        int i =0;
        int j;
        while (i<dimension)
        {
            j =0;
            while (j<dimension)
            {
                InformationBlock ib;
                AgentBlock ab;
                boolean isPit=false;
                boolean isBreeze=false;
                boolean isWumpus=false;
                boolean isStenchy=false;
                boolean isGlittery=false;

                if(informationArray[i][j]== AGENT){
                    ib = new InformationBlock(false, false, false, false, false);
                    ab = new AgentBlock(i,j,ib);
                    currentBlock =ab;
                }
                else {
                    if(i<dimension-1) {
                        if (informationArray[i + 1][j] == WUMPUS)
                            isStenchy = true;
                        else if(informationArray[i+1][j]== PIT)
                            isBreeze = true;
                    }
                    if(i>0) {
                        if (informationArray[i - 1][j] == WUMPUS)
                            isStenchy = true;
                        else if(informationArray[i-1][j]== PIT)
                            isBreeze = true;
                    }
                    if(j<dimension-1){
                        if (informationArray[i][j+1] == WUMPUS)
                            isStenchy = true;
                        else if(informationArray[i][j+1]== PIT)
                            isBreeze = true;
                    }
                    if(j>0){
                        if (informationArray[i][j-1] == WUMPUS)
                            isStenchy = true;
                        else if(informationArray[i][j-1]== PIT)
                            isBreeze = true;
                    }

                    if(informationArray[i][j]== WUMPUS) isWumpus = true;
                    else if(informationArray[i][j]== GOLD) {
                        isGlittery = true;
                        numberOfGold++;
                    }
                    else if(informationArray[i][j]== PIT) isPit = true;

                    ib = new InformationBlock(isStenchy,isWumpus,isBreeze,isPit,isGlittery);
                    ab = new AgentBlock(i,j,ib);
                }
                blocks[i][j] = ab;
                j++;
            }
            i++;
        }



    }
    public static Board getInstance(){
        if(instance == null) {
            instance = new Board();
        }
        return instance;
    }

    public void updateNeighbourListForEachBlock(){
        for (int i=0;i<dimension;i++){
            for (int j=0;j<dimension;j++){
                blocks[i][j].setNeighbours();
            }
        }
    }


    public ArrayList<AgentBlock> getNeighbours(Position p){
        ArrayList<AgentBlock> a = new ArrayList<>();
        if(p.i<dimension-1) a.add(blocks[p.i+1][p.j]);
        if(p.j<dimension-1) a.add(blocks[p.i][p.j+1]);
        if(p.i>0) a.add(blocks[p.i-1][p.j]);
        if(p.j>0) a.add(blocks[p.i][p.j-1]);
        return a;
    }
    public AgentBlock getCurrentBlock(){
        return currentBlock;
    }
    public void setCurrentBlock(Instruction instruction){
        if(instruction.equals(Instruction.LEFT)){
            currentBlock = blocks[currentBlock.position.i][currentBlock.position.j-1];
        }
        else if(instruction.equals(Instruction.RIGHT)){
            currentBlock = blocks[currentBlock.position.i][currentBlock.position.j+1];
        }
        else if(instruction.equals(Instruction.UP)){
            currentBlock = blocks[currentBlock.position.i-1][currentBlock.position.j];
        }
        else{
            currentBlock = blocks[currentBlock.position.i+1][currentBlock.position.j];
        }
    }
    public int goldFound(){
        goldFound++;
        GUIFrame.addStory("Gold Found.");
        System.out.println("Gold Found. "+ (numberOfGold-goldFound)+" gold left");
        if(goldFound>=numberOfGold) return 1;
        else return 0;
    }
    public AgentBlock[][] getBlocks(){
        return blocks;
    }

    public int goldRemaining(){
        return numberOfGold-goldFound;
    }

    public ArrayList<AgentBlock> getUnsafes(){
        PQ pq = new PQ();
        ArrayList<AgentBlock> al = new ArrayList<>();
        for (int i=0; i<dimension;i++){
            for (int j=0;j<dimension;j++){
                pq.add(blocks[i][j], blocks[i][j].getDegreeOfUnsafety());

            }
        }
        while (pq.size()!=0) {
            AgentBlock ab = pq.remove();
            if(ab.getPit() == State.Possible || ab.getWumpus() == State.Possible){
                al.add(ab);
            }
        }
        return al;
    }
    public ArrayList<AgentBlock> getSafes(){
        PQ pq = new PQ();
        ArrayList<AgentBlock> al = new ArrayList<>();
        for (int i=0; i<dimension;i++){
            for (int j=0;j<dimension;j++){
              pq.add(blocks[i][j], getDistance(i, j));

            }
        }
        while (pq.size()!=0) {
            AgentBlock ab = pq.remove();
            if(ab.isSafe() && ab.isUnvisited()){
                al.add(ab);
            }
        }
        return al;
    }

    private int getDistance(int i, int j) {
        return Math.abs(currentBlock.position.i - i) +
                Math.abs(currentBlock.position.j - j);
    }

    public void reconfirmWumpusAndPit(){
        for (int i=0;i<dimension;i++){
            for (int j=0;j<dimension;j++){
                if(blocks[i][j].getStench() == State.Exists && blocks[i][j].onlyOneNeighbourWumpusPossible())
                    blocks[i][j].confirmNeighbourWumpus();
                if(blocks[i][j].getBreeze() == State.Exists && blocks[i][j].onlyOneNeighbourPitPossible())
                    blocks[i][j].confirmNeighbourPit();
            }
        }
    }

}
