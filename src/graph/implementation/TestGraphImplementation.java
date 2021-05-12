package graph.implementation;

import graph.Vertex;
import graph.edge.EdgeKind;

public class TestGraphImplementation {

    public static void main(String[] args) {
        Vertex v1 = new Vertex(null);
        Vertex v2 = new Vertex(null);
        Vertex v3 = new Vertex(null);
        IncidenceArrayGraph graph = new IncidenceArrayGraph(5);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addEdge(v1, v2, EdgeKind.DIRECTED);
        graph.addEdge(v1, v3, EdgeKind.DIRECTED);
        System.out.println("Graph created with 3 vertices and 2 edges.");
    }
}
