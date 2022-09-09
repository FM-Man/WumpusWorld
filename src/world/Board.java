package world;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    private final int dimension=4;
    private final AgentBlock[][] blocks;



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

        for (int i=0; scanner.hasNextLine(); i++){
            String[] line = scanner.nextLine().split(",",0);
            for (int j=0; j<line.length;j++){
                
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

}
