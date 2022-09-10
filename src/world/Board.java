package world;

import common.Instruction;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    private final int dimension=4;
    private final AgentBlock[][] blocks;
    private AgentBlock currentBlock;

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
                    case "a", "A" -> informationArray[i][j] = 0;
                    case "w", "W" -> informationArray[i][j] = 1;
                    case "s", "S" -> informationArray[i][j] = 2;
                    case "g", "G" -> informationArray[i][j] = 3;
                    default -> informationArray[i][j] = 4;
                }
            }
        }

        for (int i=0; i<dimension;i++){
            for (int j=0;j<dimension;j++){
                InformationBlock ib;
                AgentBlock ab;
                boolean isPit=false;
                boolean isBreeze=false;
                boolean isWumpus=false;
                boolean isStenchy=false;
                boolean isGlittery=false;

                if(informationArray[i][j]==0){
                    ib = new InformationBlock(isStenchy,isWumpus,isBreeze,isPit,isGlittery);
                    ab = new AgentBlock(i,j,ib);
                    currentBlock =ab;
                }
                else {
                    if(i<dimension-1) {
                        if (informationArray[i + 1][j] == 1)
                            isStenchy = true;
                        else if(informationArray[i+1][j]==4)
                            isBreeze = true;
                    }
                    if(i>0) {
                        if (informationArray[i - 1][j] == 1)
                            isStenchy = true;
                        else if(informationArray[i-1][j]==4)
                            isBreeze = true;
                    }
                    if(j<dimension-1){
                        if (informationArray[i][j+1] == 1)
                            isStenchy = true;
                        else if(informationArray[i][j+1]==4)
                            isBreeze = true;
                    }
                    if(j>0){
                        if (informationArray[i][j-1] == 1)
                            isStenchy = true;
                        else if(informationArray[i][j-1]==4)
                            isBreeze = true;
                    }

                    if(informationArray[i][j]==1) isWumpus = true;
                    else if(informationArray[i][j]==3) isGlittery = true;
                    else if(informationArray[i][j]==4) isPit = true;

                    ib = new InformationBlock(isStenchy,isWumpus,isBreeze,isPit,isGlittery);
                    ab = new AgentBlock(i,j,ib);
                }
                blocks[i][j] = ab;
            }
        }



    }
    public static Board getInstance(){
        if(instance == null) {
            instance = new Board();
        }
        return instance;
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
}
