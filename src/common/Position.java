package common;

import java.util.ArrayList;

public class Position {
    public final int i;
    public final int j;
    public final int ij;
    public Position(int i, int j){
        this.i=i;
        this.j=j;
        this.ij=i*10+j;
    }
    public Position(int ij){
        this.ij=ij;
        this.i=ij/10;
        this.j=ij%10;
    }
    public boolean adjacent(Position p){
        return Math.abs(p.i-i)<=1 && Math.abs(p.j-j)<=1 && p.ij != ij;
    }
    public ArrayList<Position> getAdjacents(){
        ArrayList<Position> p = new ArrayList<>();
        if(i>=1) p.add(new Position(i-1,j));
        if(i<=8) p.add(new Position(i+1,j));
        if(j>=1) p.add(new Position(i,j-1));
        if(i<=8) p.add(new Position(i,j+1));
        return p;
    }
    public boolean equals(Object p){
        Position p1= (Position) p;
        if(p1 == null) return false;
        return p1.ij==ij;
    }
}
