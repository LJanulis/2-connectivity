package graph;

import java.util.*;

/**
 * @author Lukas
 */

public class Graph {

    public ArrayList<Vertex> vertices = new ArrayList<>();

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertex> vertices) {
        this.vertices = vertices;
    }

    public Vertex getVertex(String vertexName){
        for(Vertex v : vertices){
            if(v.getName().equals(vertexName))
                return v;
        }
        return null;
    }

    public void printVertices(){
        vertices.forEach(System.out::println);
    }

    public void printEdges(){
        for(Vertex v : this.vertices){
            for(Edge e : v.getEdges()){
                System.out.println(v.getName() + "--" + e.getType() + "-->" + e.getSecond().getName());
            }
        }
    }

    public int edgeCount(){

        int i = 0;
        for(Vertex v : this.vertices){
            i+=v.getEdges().size();
        }
        return i;
    }

    public void clear(){
        for(Vertex v : this.vertices){
            v.getEdges().clear();
        }
        this.vertices.clear();
    }

    public void DFI_sortEdges(){
        for(Vertex v : this.getVertices()){
            v.getEdges().sort(new EdgeComparators.DFIEdgeComparator());
        }
    }
}
