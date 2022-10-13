package common;

import world.AgentBlock;

import java.util.ArrayList;

public class PQ {
    private final ArrayList<AgentBlock> list = new ArrayList<>();
    private final ArrayList<Integer> priority = new ArrayList<>();
    public int size(){return list.size();}
    public void add(AgentBlock ab,int p){
        int i=0;
        for ( i=0;i<priority.size();i++){
            if(p<priority.get(i)) break;
        }
        list.add(i,ab);
        priority.add(i,p);
    }
    public AgentBlock remove(){
        priority.remove(0);
        return list.remove(0);
    }
    public AgentBlock get(int i){return list.get(i);}
}
