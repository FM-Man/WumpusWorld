package common;

import world.AgentBlock;
import world.Board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.PriorityQueue;

public class Dijkstra {
    static PriorityQueue<AgentBlock> queue;
    public static ArrayList<AgentBlock> getPath(AgentBlock from, AgentBlock to){

        AgentBlock[][] blocks = Board.getInstance().getBlocks();
        for (int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                blocks[i][j].refresh();
            }
        }

        queue = new PriorityQueue<>();
//        AgentBlock prev = null;
        AgentBlock node = from;
        from.consider(null);

        while (!Objects.equals(node, to)){
            ArrayList<AgentBlock> nbs = Board.getInstance().getNeighbours(node.i(),node.j());
            for (AgentBlock nb : nbs) {
                if ((!nb.isUnvisited() || nb.isSafe() || nb.equals(to)) && !nb.considered) {
                    nb.consider(node);
                    if (!queue.contains(nb))
                        queue.add(nb);
                }
            }

            node = queue.poll();
        }

        ArrayList<AgentBlock> way = new ArrayList<>();
        node = to;
        while (true){
            way.add(node);
            if(node.equals(from))
                break;

            node= node.from;
        }
        Collections.reverse(way);
        return way;
    }
}
