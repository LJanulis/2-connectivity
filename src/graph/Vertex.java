package graph;

import java.util.ArrayList;

/**
 * @author Lukas
 */
public class Vertex{

    private String name;
    private Integer degree;
    //Color, used in DFS
    private String color = "WHITE";
    private int depthFirstIndex;

    private ArrayList<Edge> edges = new ArrayList<>();

    public Vertex(String name){
        this.name = name;
        this.degree = 0;
    }

    public void addEdge(Vertex v2, String type){
        EdgeComparators.AlphabeticalEdgeComparator comp = new EdgeComparators.AlphabeticalEdgeComparator();
        Edge e = new Edge(this, v2, type);
        if(this.edges.contains(e))
            throw new IllegalArgumentException("Graph already contains edge " + e);
        this.degree+=1;
        this.edges.add(e);
        //Sorting edges to alphabetical order
        this.edges.sort(comp);
    }

    public int getDepthFirstIndex() {
        return depthFirstIndex;
    }

    public void setDepthFirstIndex(int depthFirstIndex) {
        this.depthFirstIndex = depthFirstIndex;
    }

    public String getName() {
        return name;
    }

    public Integer getDegree() {
        return degree;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {

        StringBuilder connections = new StringBuilder();
        for(Edge e : edges){
            connections.append(e.getSecond().getName()).append(" ");
        }

        return "Vertex: " + this.name + " degree: " + this.degree + " connections: " + connections.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;

        Vertex vertex = (Vertex) o;

        if (getDepthFirstIndex() != vertex.getDepthFirstIndex()) return false;
        if (!getName().equals(vertex.getName())) return false;
        if (!getDegree().equals(vertex.getDegree())) return false;
        if (getColor() != null ? !getColor().equals(vertex.getColor()) : vertex.getColor() != null) return false;
        return getEdges() != null ? getEdges().equals(vertex.getEdges()) : vertex.getEdges() == null;
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getDegree().hashCode();
        result = 31 * result + (getColor() != null ? getColor().hashCode() : 0);
        result = 31 * result + getDepthFirstIndex();
        result = 31 * result + (getEdges() != null ? getEdges().hashCode() : 0);
        return result;
    }
}