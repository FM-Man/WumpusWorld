package world;

public class AgentBlock {
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
        pitPossible = (visited || sureOfPit) && pitPossible;
        pitPossible =!(visited || sureOfPit) && hasBreeze;

        wumpusPossible = (visited || sureOfWumpus) && wumpusPossible;
        wumpusPossible =!(visited || sureOfWumpus) && hasStench;

        sureOfPit = !hasBreeze || sureOfPit;
        sureOfWumpus = !hasStench || sureOfWumpus;
    }

    public int visit(){
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
            for (AgentBlock a:
                    Board.getInstance().
                    getNeighbours(i,j)
            ){
                a.neighbourUpdate(
                        block.isBreezy(),
                        block.isStench()
                );
            }
            return 0;
        }
    }

}
