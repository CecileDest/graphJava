package graph.implementation;

import graph.UndirectedEdge;
import graph.Vertex;
import graph.Edge;

import java.awt.*;

public class TestGraphImplementation {

    public static void main(String[] args) {
        Vertex v1 = new Vertex(null, Color.white);
        Vertex v2 = new Vertex(null, Color.white);
        Vertex v3 = new Vertex(null, Color.white);
        Edge e1 = new UndirectedEdge(Color.white, 0);
        e1.setEnds(v1, v2);
        IncidenceArrayGraph graph = new IncidenceArrayGraph(10);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
    }
}
