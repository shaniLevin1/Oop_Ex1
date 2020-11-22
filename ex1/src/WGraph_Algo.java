package ex1.src;
import ex1.src.*;

import java.io.IOException;
import java.util.*;
import java.io.*;
/**
 * This class represent WGraph_Algo and implements all the algorithms
 */
public class WGraph_Algo implements weighted_graph_algorithms ,java.io.Serializable {
    private weighted_graph gr;

    /**
     * Empty constructor
     */
    public WGraph_Algo() {
        this.gr = new WGraph_DS();
    }

    /**
     * Constructor
     * @param g-weighted_graph
     */
    public WGraph_Algo(weighted_graph g) {
        this.gr = g;
    }

    /**
     * Initialization the graph
     *
     * @param g-weighted_graph
     */
    @Override
    public void init(weighted_graph g) {
        this.gr = g;
    }

    /**
     * This function return the WGraph_DS
     *
     * @return gr-weighted_graph
     */
    @Override
    public weighted_graph getGraph() {
        return this.gr;
    }

    /**
     * Return deep copy of the graph as a new weighted_graph
     *
     * @return g-weighted_graph
     */
    @Override
    public weighted_graph copy() {
        weighted_graph g = new WGraph_DS(gr);
        return g;
    }

    /**
     * This method check if the graph isConnected or not.
     * We using the BFS algorithm to run by all the nodes in the graph and we used the Tags to marked the nodes we visited.
     * we counting the nodes we visited and in the end we check if the counter equal to the node.size.
     * If it is the graph isConnected.
     * @return boolean - the graph isConnected= true, else false.
     */
    @Override
    public boolean isConnected() {
        if (gr == null) {
            return true;
        }
        if (gr.nodeSize() == 0 || gr.nodeSize() == 1) { //if the graph is empty return true
            return true;
        }
        for (node_info n : gr.getV()) { //init all the node's Tag to 0
            n.setTag(0); //0 is not visited and 1 is visited
        }
        Queue<node_info> queue = new LinkedList<node_info>();
        int counter = 0; //the number of the visited nodes
        int n = 0;
        for (node_info x : gr.getV()) { //pick one of the key's nodes in the graph
            n = x.getKey();
            break;
        }
        node_info first = gr.getNode(n);
        queue.add(first); //adding the first node to the queue
        while (!queue.isEmpty()) {
            node_info first1 = queue.poll();//remove the first node in the queue and define first1 as the new first one
            for (node_info x : gr.getV(first1.getKey())) {//checking the neighbors of the node if were visited
                if (x.getTag() == 0) {
                    queue.add(x);
                    x.setTag(1);//setting the Tag to 1 means this node has been visited
                }
            }
            if (queue.peek() != null)
                counter++;
        }
        if (counter == gr.nodeSize()) { //if the number of the connected nodes equal to the node's size return true
            return true;
        } else {
            return false;
        }
    }

    /**
     * At first we take care of all the edge cases.
     * We set the src's tag to 0 and all the other node's tags to Double.MAX_VALUE and put
     * them in the unvisited Queue according the Dijkstra algorithm
     * We moving on all the nodes in unvisited Queue by defining the current node as first and
     * check for every node all his neighbors.
     * For each neighbor we check if first's tag plus the weight of the edge between first and the neighbor is smaller then the current neighbor's tag.
     * If it is we set the current neighbor's tag to first's tag plus the weight of the edge between
     * first and the neighbor and push it into the unvisited Queue.
     * After we moving on all the neighbors of first we remove first from the unvisited Queue and defining the new first as the peek of the Queue.
     * At the end we check if we get the destination, if we get there we return the destination's tag and if not we return -1.
     * @param src  - start node
     * @param dest - end (target) node
     * @return the shortest distance path between the two node's keys
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (this.gr == null)
            return -1;
        int counter = 0;
        for (node_info x : gr.getV()) {//check if dest is in the graph
            if (x.getKey() == dest) {
                counter++;
            }
            if (x.getKey() == src) {//check if src is in the graph
                counter++;
            }
        }
        if (counter == 2) {
            if (src == dest) {//check if the src and the dest are in the graph and that they are different from each other
                return 0;
            }
            Queue<node_info> unvisited = new LinkedList<node_info>();
            gr.getNode(src).setTag(0); //the distance from src to himself is 0
            for (node_info n : gr.getV()) {
                if (n.getKey() != src) { //except src
                    n.setTag(Double.MAX_VALUE); //define all the shortest distance from all the nodes to src to infinity
                }
            }
            node_info first = gr.getNode(src);
            int n = first.getKey();
            unvisited.add(gr.getNode(src)); //first add src node to unvisited quene
            while (!unvisited.isEmpty()) {
                for (node_info x : gr.getV(first.getKey())) { // running all first neighbors
                    if (gr.getNode(first.getKey()).getTag() + gr.getEdge(x.getKey(), first.getKey()) < x.getTag()) {
                        x.setTag(gr.getNode(first.getKey()).getTag() + gr.getEdge(x.getKey(), first.getKey()));
                        unvisited.add(x);
                    }
                }
                unvisited.remove();
                if (unvisited.peek() != null) {
                    first = unvisited.peek();//define the first in the queue to be first parameter
                    n = first.getKey();
                }
            }
            if (gr.getNode(dest).getTag() == Double.MAX_VALUE) {
                return -1;
            }
            return gr.getNode(dest).getTag();
        }
        return -1;
    }

    /**
     * At first we take care of all the edge cases.
     * We use the shortestPathDist function, and define a List to push the nodes that belong
     * to the path between the given src and dest.
     * We push the given dest to the List and moving on his neighbors and search for the right
     * node's tag and push this node to the List.
     * Then this is the next node that we check his neighbors and we moving on until we
     * get the src node and push it into the List.
     * Then we reverse the List so it starts from the src and ends in the dest.
     * @param src  - start node
     * @param dest - end (target) node
     * @return the shortest distance path between the two node's keys
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if(gr!=null) {
            double d = shortestPathDist(src, dest);
            if (d != -1) {
                List<node_info> A = new LinkedList<node_info>();
                A.add(gr.getNode(dest));
                if (d == 0) return A;
                int temp = dest;
                boolean flag = true;
                while (flag) {
                    for (node_info x : gr.getV(temp)) {
                        if (x.getKey() == src) {
                            flag = false;
                        }
                        if ((x.getTag() + gr.getEdge(x.getKey(), temp)) == d) {
                            temp = x.getKey();
                            d = x.getTag();
                            A.add(x);
                            break;
                        }
                    }
                }
                Collections.reverse(A);
                return A;
            }
        }
        return null;
    }


    /**
     * Saves this weighted (undirected) graph to the given file name.
     * Using Serializable library.
     * @param file - the file name
     * @return boolean - the graph saved= true, else false.
     */
    @Override
    public boolean save(String file) {
        try{
           FileOutputStream fileOutputStream=new FileOutputStream(file);
           ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.gr);

            fileOutputStream.close();
            objectOutputStream.close();
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Load a graph to this graph algorithm.
     * Using Serializable library.
     * @param file - file name
     * @return boolean - the graph lode= true, else false.
     */
    @Override
    public boolean load(String file) {
        try{
            FileInputStream fileInputStream=new FileInputStream(file);
            ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
            weighted_graph deserialized=(weighted_graph) objectInputStream.readObject();

            fileInputStream.close();
            objectInputStream.close();
            this.gr=deserialized;
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     *Comparing between two objects (weighted_graph_algorithms)
     * @param o-object
     * @return boolean - the objects are equals= true, else false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_Algo that = (WGraph_Algo) o;
        return gr.equals(that.gr);
    }

    /**
     * Not necessary function, build due to equals function.
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(gr);
    }
}
