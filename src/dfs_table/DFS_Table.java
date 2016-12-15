package dfs_table;

import graph.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Lukas
 */

/**
 * Table to hold data after performing DFS *
 */
public class DFS_Table{

    private ArrayList<TableColumn> vertexData = new ArrayList<>();

    /**
     * Adds vertex do the table, predecessor is null
     * @param vertex
     */
    public void addVertex(String vertex){
        TableColumn temp = new TableColumn();
        temp.setPred(null);
        temp.setVertex(vertex);
        vertexData.add(temp);
    }

    public ArrayList<TableColumn> getVertexData() {
        return vertexData;
    }

    public boolean isPredecessor(String vertexName, String predecessor){

        for(TableColumn t : this.vertexData){
            if(t.getVertex().equals(vertexName) && t.getPred().equals(predecessor)){
                return true;
            }
        }
        return false;
    }

    public void setPredecessor(String vertexName, String predecessor){

        for(TableColumn t : this.vertexData){
            if(t.getVertex().equals(vertexName)){
                t.setPred(predecessor);
                return;
            }
        }
        throw new IllegalArgumentException("No such vertex found in DFS table");
    }

    public void setDFI(String vertexName, int dfi){
        for(TableColumn t : this.vertexData){
            if(t.getVertex().equals(vertexName)){
                t.setDfi(dfi);
                return;
            }
        }
        throw new IllegalArgumentException("No such vertex found in DFS table");
    }


    public void displayTable(){

        System.out.print("v:    ");
        for(TableColumn t : this.vertexData){
            System.out.printf("%-10s", t.getVertex());
        }
        System.out.println();
        System.out.print("dfi:  ");
        for(TableColumn t : this.vertexData){
            System.out.printf("%-10s", t.getDfi());
        }
        System.out.println();
        System.out.print("π:    ");
        for(TableColumn t : this.vertexData){
            System.out.printf("%-10s", t.getPred());
        }
        System.out.println();
    }

    /**
     * Generates DFS spanning tree from current data
     * @return DFS-tree
     */
    public Graph getSpanningTree(){

        Graph spanningTree = new Graph();
        for(TableColumn t : this.vertexData){
            spanningTree.getVertices().add(new Vertex(t.getVertex()));
        }
        for(TableColumn t : this.vertexData){
            spanningTree.getVertex(t.getVertex()).setDepthFirstIndex(t.getDfi());
        }

        for(TableColumn t : this.vertexData){
            if(t.getPred() != null){

                Vertex v = new Vertex(t.getVertex());
                Vertex pred = new Vertex(t.getPred());
                spanningTree.getVertex(pred.getName()).addEdge(spanningTree.getVertex(v.getName()), "TREE EDGE");
            }
        }
        return spanningTree;
    }

    /**
     * Generates DFS spanning tree with reversed edge orientation
     * @return DFS-tree
     */
    public Graph reverseEdgeDFStree(){

        ArrayList<TableColumn> temp = this.vertexData;
        Collections.sort(this.vertexData);

        Graph spanningTree = new Graph();
        for(TableColumn t : this.vertexData){
            spanningTree.getVertices().add(new Vertex(t.getVertex()));
        }
        for(TableColumn t : this.vertexData){
            spanningTree.getVertex(t.getVertex()).setDepthFirstIndex(t.getDfi());
        }

        for(TableColumn t : this.vertexData){
            if(t.getPred() != null){

                Vertex v = new Vertex(t.getVertex());
                Vertex pred = new Vertex(t.getPred());
                spanningTree.getVertex(v.getName()).addEdge(spanningTree.getVertex(pred.getName()), "TREE EDGE");
            }
        }
        this.vertexData = temp;
        return spanningTree;
    }
}
