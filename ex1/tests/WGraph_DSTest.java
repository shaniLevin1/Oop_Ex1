package ex1.tests;
import ex1.src.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {

    @Test
    void getNode() {
       weighted_graph g =new WGraph_DS();
       g.addNode(0);
       assertNotNull(g.getNode(0));
       assertNull(g.getNode(1));
    }

    @Test
    void hasEdge() {
        weighted_graph g =new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        assertFalse(g.hasEdge(1,0));
        g.connect(0,1,33);
        assertTrue(g.hasEdge(1,0));
    }

    @Test
    void getEdge() {
        weighted_graph g =new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.connect(0,1,2);
        assertEquals(2,g.getEdge(0,1));
        g.connect(1,2,0);
        assertEquals(0,g.getEdge(1,2));
        g.connect(1,2,3);
        assertEquals(3,g.getEdge(1,2));
    }

    @Test
    void addNode() {
        weighted_graph g =new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        assertTrue(g.getV().contains(g.getNode(0)));
        g.addNode(0);
        assertEquals(3,g.nodeSize());
    }

    @Test
    void connect() {
        weighted_graph g =new WGraph_DS();
        assertFalse(g.hasEdge(1,2));
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
        assertTrue(g.hasEdge(2,5));
        assertFalse(g.hasEdge(2,3));
    }

    @Test
    void getV() {
        weighted_graph g =new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        weighted_graph g1 =new WGraph_DS();
        g1.addNode(0);
        g1.addNode(1);
        g1.addNode(2);
        g1.addNode(3);
        g1.addNode(4);
        g1.addNode(5);
        assertEquals(g.getV().size(),g1.getV().size());
    }

    @Test
    void removeNode() {
        weighted_graph g =new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.removeNode(0);
        assertFalse(g.getV().contains(g.getNode(0)));
        assertTrue(g.getV().contains(g.getNode(1)));
        g.connect(2,3,4);
        assertTrue(g.hasEdge(2,3));
        g.removeNode(2);
        assertFalse(g.hasEdge(2,3));
    }

    @Test
    void removeEdge() {
        weighted_graph g =new WGraph_DS();
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
    }

    @Test
    void nodeSize() {
        weighted_graph g =new WGraph_DS();
        assertEquals(0,g.nodeSize());
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        assertEquals(6,g.nodeSize());
        g.removeNode(0);
        g.removeNode(1);
        assertEquals(4,g.nodeSize());
    }

    @Test
    void edgeSize() {
        weighted_graph g =new WGraph_DS();
        assertEquals(0,g.edgeSize());
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
        assertEquals(5,g.edgeSize());
        g.removeEdge(0,1);
        g.removeEdge(0,2);
        assertEquals(3,g.edgeSize());
    }

    @Test
    void getMC() {
        weighted_graph g =new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.connect(0,1,4);
        g.connect(0,2,5);
        g.removeNode(1);
        assertEquals(4,g.getMC());
    }
}