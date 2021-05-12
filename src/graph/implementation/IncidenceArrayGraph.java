package graph.implementation;

import graph.*;
import graph.edge.DirectedEdge;
import graph.edge.Edge;
import graph.edge.EdgeKind;
import graph.edge.UndirectedEdge;

import java.io.Serializable;

public class IncidenceArrayGraph implements Graph, Serializable {

    private int maxVertices;
    private Vertex[] vertices;
    private Edge[] edges;
    private Edge[][] incidenceArray;


    /**
     * Constructs a new graph accepting a limited number of vertices.
     * @param maxVertices
     */
    public IncidenceArrayGraph(int maxVertices) {
        this.maxVertices = maxVertices;
        this.vertices = new Vertex[maxVertices];
        this.edges = new Edge[maxVertices*maxVertices*2];
        this.incidenceArray = new Edge[maxVertices][maxVertices*2];
    }

    /*
    Getters
     */

    @Override
    public Edge[] getEdges() {
        return this.edges;
    }

    @Override
    public Vertex[] getVertices() {
        return this.vertices;
    }

    /*
    Computed getters
     */

    /**
     * @return the number of vertices in the current graph
     */
    @Override
    public int nbOfVertices() {
        return Utils.getArraySize(this.vertices);
    }

    /**
     * @return the number of edges in the current graph
     */
    @Override
    public int nbOfEdges() {
        return Utils.getArraySize(this.edges);
    }

    /**
     * Returns the edges that connects the two given vertices. (could be multiple edges if there
     * are two directed edges in opposite ways)
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return an array of the edges connecting the two vertices
     */
    @Override
    public Edge[] getEdges(Vertex vertex1, Vertex vertex2) {
        //TODO
        return new Edge[0];
    }

    /**
     * Returns all of the edges connected to a given vertex.
     * @param vertex the vertex we want the neighbor edges of
     * @return an array containing the neighbor edges
     */
    @Override
    public Edge[] getNeighborEdges(Vertex vertex) {
        return this.incidenceArray[vertex.getId()];
    }


    /*
    Methods
     */

    /**
     * Adds a vertex to the graph. Does nothing if the number of maximum vertices has already been reached.
     * @param vertex the vertex to add
     */
    public void addVertex (Vertex vertex) {
        if (vertex.getId() < this.maxVertices) {
            this.vertices[vertex.getId()] = vertex;
        } else {
            System.out.println("This vertex has an ID to high for this graph. (id = " + vertex.getId() + " but max vertices allowed = " + this.maxVertices + ").");
        }
    }

    /**
     * Adds an edge to the current graph. If the edge is of kind directed, then the source is vertex1 and the sink is vertex2.
     * @param vertex1 the first vertex (the source in case of a directed edge)
     * @param vertex2 the second vertex (the sink in case of a directed edge)
     * @param edgeKind the type of edge to add (directed or undirected)
     */
    @Override
    public void addEdge(Vertex vertex1, Vertex vertex2, EdgeKind edgeKind) {
        if (vertex1.getId() < this.maxVertices && vertex2.getId() < this.maxVertices) {
            if (edgeKind == EdgeKind.DIRECTED) {
                // Adds a directed edge to the edges array
                DirectedEdge directedEdge = new DirectedEdge(vertex1, vertex2, 0); // by default the source is the first vertex
                this.addEdge(directedEdge);
            }
            if (edgeKind == EdgeKind.UNDIRECTED) {
                // Adds an undirected edge to the edges array
                UndirectedEdge undirectedEdge = new UndirectedEdge(vertex1, vertex2);
                this.addEdge(undirectedEdge);
            }
        } else {
            System.out.println("One of the passed vertices has an ID too high for the current graph.");
        }
    }

    /**
     * Adds the given edge to the edges array and to the incidence array.
     * @param edge the edge to add
     */
    private void addEdge(Edge edge) throws GraphOverflowException {

        if (Utils.getFirstEmptyIndex(this.edges) == -1) throw new GraphOverflowException("Too many edges in the graph.");

        this.edges[edge.getId()] = edge;
        Vertex vertex1 = edge.getEnds()[0];
        Vertex vertex2 = edge.getEnds()[1];

        // Adds the edge to the vertex1 incidence list
        int edgeIndex1 = Utils.getFirstEmptyIndex(this.incidenceArray[vertex1.getId()]);
        this.incidenceArray[vertex1.getId()][edgeIndex1] = edge;

        // Adds the edge to the vertex2 incidence list
        int edgeIndex2 = Utils.getFirstEmptyIndex(this.incidenceArray[vertex2.getId()]);
        this.incidenceArray[vertex2.getId()][edgeIndex2] = edge;
    }

    /**
     * Given one end of an edge (a vertex), returns the other end.
     * @param edge the edge we want the other end of
     * @param vertex the first end of the edge
     * @return a vertex which is the other end of the edge
     */
    private Vertex otherEnd(Edge edge, Vertex vertex) {
        Vertex[] ends = edge.getEnds();
        if (ends[0] == vertex) {
            return ends[1];
        } else {
            return ends[0];
        }
    }

    /**
     * @return true if the graph is connected; false otherwise
     */
    @Override
    public boolean isConnected() {
        boolean isC = true;
        Vertex v1 = this.vertices[0];
        int i = 1;
        while (isC && i< this.nbOfVertices()) {
            isC = isConnected(v1, this.vertices[i]);
            i++;
        }

        return isC;
    }


    /**
     * Checks if a given vertex1 is connected with vertex2.
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return
     */
    @Override
    public boolean isConnected(Vertex vertex1, Vertex vertex2) throws RuntimeException {
        if (vertex1 == vertex2){
            return true;
        }

        int[] vertex_states = new int[this.maxVertices];
        for (int i = 0; i<this.maxVertices ; i++) {
            vertex_states[i] = 0;
        }
        int current = vertex1.getId();
        vertex_states[current] = 1;

        while (vertex_states[vertex2.getId()] != 1 && current != -1) {
            int i = 0;
            while (i < this.maxVertices && this.incidenceArray[current][i] != null){
                Vertex otherEnd = this.otherEnd(this.incidenceArray[current][i], this.vertices[current]);
                if (vertex_states[otherEnd.getId()] == 0) {
                    vertex_states[otherEnd.getId()] = 1;
                }
                i++;
            }
            vertex_states[current] = 2;

            boolean found = false;
            int j = 0;
            current = -1;
            while (j < vertex_states.length && !found) {
                if (vertex_states[j] == 1){
                    current = j;
                    found = true;
                }
                j++;
            }
        }
        return vertex_states[vertex2.getId()] == 1;
    }
}
