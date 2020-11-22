package ex1.tests;

import ex1.src.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {

    @Test
    void init() {
        weighted_graph g =new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        weighted_graph_algorithms go= new WGraph_Algo();
        weighted_graph_algorithms g1= new WGraph_Algo();
        g1.init(g);
        go.init(g);
        assertEquals(g1,go);
        boolean f = true;
        for (node_info i : g.getV()) {
            if (i != go.getGraph().getNode(i.getKey()))
                f = false;
        }
        assertTrue(f);
    }

    @Test
    void getGraph() {
        weighted_graph g =new WGraph_DS();
        weighted_graph_algorithms go= new WGraph_Algo();
        assertEquals(g,go.getGraph());
    }

    @Test
    void copy() {
        weighted_graph g =new WGraph_DS();
        weighted_graph_algorithms go= new WGraph_Algo();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.connect(0,2,3);
        g.connect(1,2,4);
        g.connect(0,1,3);
        go.init(g);
        weighted_graph g1= new WGraph_DS();
        g1=go.copy();
        boolean f=true;
        for (node_info i : g.getV()) {
            if (i != go.getGraph().getNode(i.getKey()))
                f = false;
        }
        if(go.getGraph().edgeSize()!=g1.edgeSize()) f=false;
        if(go.getGraph().nodeSize()!=g1.nodeSize()) f=false;
        assertTrue(f);
    }

    @Test
    void isConnected() {
        weighted_graph g =new WGraph_DS();
        weighted_graph_algorithms go= new WGraph_Algo();
        go.init(g);
        assertTrue(go.isConnected());
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.connect(0,2,3);
        g.connect(1,2,4);
        g.connect(0,1,3);
        go.init(g);
        assertTrue(go.isConnected());
        g.addNode(4);
        go.init(g);
        assertFalse(go.isConnected());
    }

    @Test
    void shortestPathDist() {
        weighted_graph g =new WGraph_DS();
        weighted_graph_algorithms go= new WGraph_Algo();
        double d0=go.shortestPathDist(0,3);
        assertNotEquals(d0,4);
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.connect(0,2,3);
        g.connect(1,2,4);
        g.connect(0,1,3);
        g.connect(2,3,5);
        g.connect(0,4,2);
        g.connect(3,5,6);
        g.connect(1,3,1);
        go.init(g);
        double d1=go.shortestPathDist(0,3);
        assertEquals(d1,4);
        double d2=go.shortestPathDist(2,5);
        assertNotEquals(d2,12);
        assertEquals(d2,11);
        double d3=go.shortestPathDist(6,5);
        assertEquals(d3,-1);
    }
    @Test
    void shortestPath() {
        weighted_graph g =new WGraph_DS();
        weighted_graph_algorithms go= new WGraph_Algo();
        List<node_info> A1 = new LinkedList<node_info>();
        List<node_info> B1 = new LinkedList<node_info>();
        B1=go.shortestPath(3,6);
        A1.add(g.getNode(1));
        A1.add(g.getNode(2));
        assertNotEquals(A1,B1);
        assertEquals(null,B1);
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.connect(0,2,3);
        g.connect(1,2,4);
        g.connect(0,1,3);
        g.connect(2,3,5);
        g.connect(0,4,2);
        g.connect(3,5,6);
        g.connect(1,3,1);
        go.init(g);
        double d1=go.shortestPathDist(0,3);
        List<node_info> A = new LinkedList<node_info>();
        List<node_info> B = new LinkedList<node_info>();
        B=go.shortestPath(0,3);
        A.add(g.getNode(0));
        A.add(g.getNode(1));
        A.add(g.getNode(3));
        assertEquals(A,B);

    }

    @Test
    void saveLoad() {
        weighted_graph g =new WGraph_DS();
        weighted_graph_algorithms go= new WGraph_Algo();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.connect(0,1,4);
        g.connect(0,2,5);
        g.connect(2,5,2);
        g.connect(3,4,6);
        g.connect(3,5,1);
        go.init(g);
        String s = "g000.txt";
        go.save(s);
        s = "g111.txt";
        g.addNode(7);
        g.addNode(8);
        go.init(g);
        go.save(s);
        go.load("g000.txt");
        weighted_graph st1 = new WGraph_DS(go.getGraph());
        go.load("g111.txt");
        weighted_graph st2 = new WGraph_DS(go.getGraph());
        assertNotEquals(st1, st2);
        g.removeNode(7);
        g.removeNode(8);
        go.init(g);
        String s1 = "st2";
        go.save(s1);
        weighted_graph st3 = new WGraph_DS(go.getGraph());
        assertEquals(st1, st3);
    }
}