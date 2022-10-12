package world;

import common.Instruction;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    private final int dimension=10;
    private final AgentBlock[][] blocks;
    private AgentBlock currentBlock;
    private int numberOfGold;
    private int goldFound;

    private final int AGENT     = 0;
    private final int WUMPUS    = 1;
    private final int SAFE      = 2;
    private final int GOLD      = 3;
    private final int PIT       = 4;


    private static Board instance = null;
    private Board() {
        blocks = new AgentBlock[dimension][dimension];

        File inputFile = new File("input.txt");
        Scanner scanner;
        try {
             scanner= new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        int[][] informationArray = new int[dimension][dimension];
        for (int i=0; scanner.hasNextLine(); i++){
            String[] line = scanner.nextLine().split(",",0);
            for (int j=0; j<line.length;j++){
                switch (line[j]) {
                    case "a", "A"   -> informationArray[i][j] = AGENT;
                    case "w", "W"   -> informationArray[i][j] = WUMPUS;
                    case "s", "S"   -> informationArray[i][j] = SAFE;
                    case "g", "G"   -> informationArray[i][j] = GOLD;
                    default         -> informationArray[i][j] = PIT;
                }
            }
        }


        int i =0;
        int j=0;
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

                if(informationArray[i][j]==AGENT){
                    ib = new InformationBlock(isStenchy,isWumpus,isBreeze,isPit,isGlittery);
                    ab = new AgentBlock(i,j,ib);
                    currentBlock =ab;
                }
                else {
                    if(i<dimension-1) {
                        if (informationArray[i + 1][j] == WUMPUS)
                            isStenchy = true;
                        else if(informationArray[i+1][j]==PIT)
                            isBreeze = true;
                    }
                    if(i>0) {
                        if (informationArray[i - 1][j] == WUMPUS)
                            isStenchy = true;
                        else if(informationArray[i-1][j]==PIT)
                            isBreeze = true;
                    }
                    if(j<dimension-1){
                        if (informationArray[i][j+1] == WUMPUS)
                            isStenchy = true;
                        else if(informationArray[i][j+1]==PIT)
                            isBreeze = true;
                    }
                    if(j>0){
                        if (informationArray[i][j-1] == WUMPUS)
                            isStenchy = true;
                        else if(informationArray[i][j-1]==PIT)
                            isBreeze = true;
                    }

                    if(informationArray[i][j]==WUMPUS) isWumpus = true;
                    else if(informationArray[i][j]==GOLD) {
                        isGlittery = true;
                        numberOfGold++;
                    }
                    else if(informationArray[i][j]==PIT) isPit = true;

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


    public ArrayList<AgentBlock> getNeighbours(int i, int j){
        ArrayList<AgentBlock> a = new ArrayList<>();
        if(i<dimension-1) a.add(blocks[i+1][j]);
        if(j<dimension-1) a.add(blocks[i][j+1]);
        if(i>0) a.add(blocks[i-1][j]);
        if(j>0) a.add(blocks[i][j-1]);
        return a;
    }
    public AgentBlock getCurrentBlock(){
        return currentBlock;
    }
    public void setCurrentBlock(Instruction instruction){
        if(instruction.equals(Instruction.LEFT)){
            currentBlock = blocks[currentBlock.i()][currentBlock.j()-1];
        }
        else if(instruction.equals(Instruction.RIGHT)){
            currentBlock = blocks[currentBlock.i()][currentBlock.j()+1];
        }
        else if(instruction.equals(Instruction.UP)){
            currentBlock = blocks[currentBlock.i()-1][currentBlock.j()];
        }
        else{
            currentBlock = blocks[currentBlock.i()+1][currentBlock.j()];
        }
    }
    public int goldFound(){
        goldFound++;
        System.out.println("Gold Found. "+ (numberOfGold-goldFound)+" gold left");
        if(goldFound>=numberOfGold) return 1;
        else return 0;
    }
    public AgentBlock[][] getBlocks(){
        return blocks;
    }
}
