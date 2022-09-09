package world;

public class InformationBlock {
    private final boolean stench;
    private final boolean withWumpus;
    private final boolean breezy;
    private final boolean withPit;
    private final boolean withGold;

    public InformationBlock(boolean withStench, boolean withWumpus, boolean breezy, boolean withPit, boolean withGold) {
        this.stench = withStench;
        this.withWumpus = withWumpus;
        this.breezy = breezy;
        this.withPit = withPit;
        this.withGold = withGold;
    }

    public boolean isStench() {
        return stench;
    }
    public boolean isWithWumpus() {
        return withWumpus;
    }
    public boolean isBreezy() {
        return breezy;
    }
    public boolean isWithPit() {
        return withPit;
    }
    public boolean isWithGold() {
        return withGold;
    }


}
