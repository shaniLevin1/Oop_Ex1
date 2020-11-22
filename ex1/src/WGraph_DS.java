package ex1.src;
import ex1.src.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

/**
 * This class represent a weighted_graph of nodes and implements all the characteristics
 */

public class WGraph_DS implements weighted_graph ,java.io.Serializable {
    private HashMap<Integer, node_info> Vmap;
    private HashMap<Integer, HashMap<node_info, Double>> Emap;
    private int Ecounter = 0;
    private int MCcounter = 0;

    /**
     * Empty constructor
     */
    public WGraph_DS() {  //regular constructor
        Vmap = new HashMap<Integer, node_info>();
        Emap = new HashMap<Integer, HashMap<node_info, Double>>();
        Ecounter = 0;
        MCcounter = 0;
    }
    /**
     * Copy constructor
     *
     * @param g-the weighted_graph
     */
    public WGraph_DS(weighted_graph g) { //copy constructor
        Vmap = new HashMap<Integer, node_info>();
        Emap = new HashMap<Integer, HashMap<node_info, Double>>();
        if(g==null) return;
        for (node_info t : g.getV()) {
            node_info a1 = new NodeInfo( t);
            Vmap.put(a1.getKey(), a1);
            HashMap<node_info, Double> Emap1 = new HashMap<node_info, Double>();
            Emap.put(a1.getKey(), Emap1);
            for (node_info a : g.getV(t.getKey())) {
                node_info a2 = new NodeInfo( a);
                Emap1.put(a, getEdge(a2.getKey(), a1.getKey()));
                Emap.get(a1.getKey()).putAll(Emap1);

            }
        }
        this.Ecounter = g.edgeSize();
        this.MCcounter = g.getMC();
    }

    /**
     *Comparing between two objects (weighted graphs)
     * @param o-object
     * @return boolean - the objects are equals= true, else false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        return Ecounter == wGraph_ds.Ecounter &&
                Vmap.equals(wGraph_ds.Vmap) &&
                Emap.equals(wGraph_ds.Emap);
    }

    /**
     * Not necessary function, build due to equals function.
     * @return none.
     */
    @Override
    public int hashCode() {
        return Objects.hash(Vmap, Emap, Ecounter, MCcounter);
    }

    /**
     * @param key - the node_id
     * @return node_data of the given key
     */
    @Override
    public node_info getNode(int key) {
        if(Vmap!=null) {
            return Vmap.get(key);
        }
        return null;
    }

    /**
     * Check if there is an edge between the two given keys
     *
     * @param node1-node's key
     * @param node2-node's key
     * @return boolean-true=hasEdge , else --> false
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (Vmap != null) {
            if (Vmap.containsKey(node1) && Vmap.containsKey(node2) && node1 != node2) {
                if (Emap.get(node1).containsKey(getNode(node2)) && Emap.get(node2).containsKey(getNode(node1))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * return the weight of the edge (node1, node2).
     * If there is no such edge return -1
     *
     * @param node1-key of node 1
     * @param node2-key of node 2
     * @return the weight of the given edge
     */
    @Override
    public double getEdge(int node1, int node2) {
        if(Vmap!=null) {
            if (Vmap.containsKey(node1) && Vmap.containsKey(node2)) {
                if (node1 == node2) {
                    return -1;
                }
                if (hasEdge(node1, node2)) {
                    return Emap.get(node1).get(getNode(node2));
                }
            }
        }
        return -1;
    }

    /**
     * Add the node_info by the given key to the collection of the graph
     *
     * @param key-key
     */
    @Override
    public void addNode(int key) {
        if(Vmap!=null) {
            if (!Vmap.containsKey(key)) {
                HashMap<node_info, Double> Emap2 = new HashMap<node_info, Double>();
                Emap.put(key, Emap2);
                node_info n = new NodeInfo(key);
                Vmap.put(key, n);
                MCcounter++;
            }
        }
    }

    /**
     * Connect between two nodes and create an edge between them
     * Define the weight of the edge
     *
     * @param node1- node's 1 key
     * @param node2- node's 2 key
     * @param w-weight of the edge
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if(w>=0&&Vmap!=null) {
            if (Vmap.containsKey(node1) && Vmap.containsKey(node2) && node1 != node2) {
                if (!hasEdge(node1, node2)) {
                    Ecounter++;
                }
                Emap.get(node1).put(getNode(node2), w);
                Emap.get(node2).put(getNode(node1), w);
                MCcounter++;
            }
        }
    }

    /**
     * @return the collection of weighted graph's vertices
     */
    @Override
    public Collection<node_info> getV() {
        if (Vmap != null) {
            return Vmap.values();
        }
        return null;
    }

    /**
     * @param node_id-node's key
     * @return collection of node's neighbors
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        if (Vmap != null) {
            return Emap.get(node_id).keySet();
        }
        return null;
    }

    /**
     * @param key-node's key
     * @return node_info of the removed node
     */
    @Override
    public node_info removeNode(int key) {
        if (Vmap != null) {
            if (Vmap.containsKey(key)) {
                for (node_info t : getV()) {
                    if (hasEdge(key, t.getKey())) {
                        Emap.get(t.getKey()).remove(getNode(key));
                        Ecounter--;
                    }
                }
                Emap.remove(key);
                Vmap.remove(key);
                MCcounter++;
                return getNode(key);
            }
        }
        return null;
    }

    /**
     * Remove the edge between two vertices
     *
     * @param node1-node's 1 key
     * @param node2-node's 2 key
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (Vmap != null) {
            if (hasEdge(node1, node2)) {
                Emap.get(node1).remove(getNode(node2));
                Emap.get(node2).remove(getNode(node1));
                Ecounter--;
                MCcounter++;
            }
        }
    }

    /**
     * @return size of weighted_graph's vertices collection
     */
    @Override
    public int nodeSize() {
        if (Vmap == null) {
            return 0;
        }
        return Vmap.size();
    }

    /**
     * @return number of edges in the weighted graph
     */
    @Override
    public int edgeSize() {
        return Ecounter;
    }

    /**
     * @return number of changes in the weighted graph
     */
    @Override
    public int getMC() {
        return MCcounter;
    }

    public class NodeInfo implements node_info ,java.io.Serializable {

        private int id;
        private String info;
        private double tag;

        /**
         * Copy constructor
         *
         * @param a- node_info
         */
        public NodeInfo(node_info a) { //copy constructor
            this.id = a.getKey();
            this.tag = a.getTag();
            this.info = a.getInfo();
        }

        /**
         * Constructor according to given key
         * @param key-key
         */
        public NodeInfo(int key) {
            this.id = key;
            this.info="";
            this.tag=0;
        }

        /**
         * Comparing between two objects (NodeInfo)
         * @param o-object
         * @return boolean - the objects are equals= true, else false.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NodeInfo nodeInfo = (NodeInfo) o;
            return id == nodeInfo.id;
        }

        /**
         * necessary function, build due to equals function and node info object that used when calling by hashmap.
         * @return hashcode keu.
         */
        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        /**
         * @return the id of the node
         */
        @Override
        public int getKey() {
            return this.id;
        }

        /**
         * @return meta data
         */
        @Override
        public String getInfo() {
            return this.info;
        }

        /**
         * Set the string of the Info
         *
         * @param s-string
         */
        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        /**
         * @return Tag-the sign of the node
         */
        @Override
        public double getTag() {
            return this.tag;
        }

        /**
         * Set the Tag of the node to t
         *
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            this.tag = t;
        }
        /**
         * @return toString of the node_data
         */
        @Override
        public String toString() {
            return "" + id;
        }
    }
}
