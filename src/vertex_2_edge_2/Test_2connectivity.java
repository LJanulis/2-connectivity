package vertex_2_edge_2;

import dfs_table.*;
import graph.*;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Lukas
 */
public class Test_2connectivity {

    //Data file holding vertex adjacency lists
    private String dataFile;

    //Graph data structure
    private Graph mainGraph;
    private Graph reverseOrientedDFS_tree;
    //Table holding predecessors, opening and closing times of vertices obtained during DFS
    private DFS_Table dfs_results;

    private ArrayList<Edge> backEdges = new ArrayList<>();
    private ArrayList<ArrayList<Edge>> chains = new ArrayList<>();


    public Test_2connectivity(String dataFile) {
        this.dataFile = dataFile;
    }

    public String performTest(String rootVertex){

        //Reading adjacency lists from file
        readData();

        // Performing DFS on our graph
        DFS(mainGraph, rootVertex);

        //Checking if DFS tree is connected
        checkIfConnected(dfs_results);
        //Changing our DFS tree so that tree-edges are oriented towards root, not away from it
        reverseOrientedDFS_tree = dfs_results.reverseEdgeDFStree();
        //Adding back-edges to reverse oriented dfs tree
        for(Edge backEdge : backEdges){
            reverseOrientedDFS_tree.getVertex(backEdge.getSecond().getName()).addEdge(reverseOrientedDFS_tree.getVertex(backEdge.getFirst().getName()), "BACK EDGE");
        }

        //Sorting edges of every vertex in ascending DFI order
        reverseOrientedDFS_tree.DFI_sortEdges();

        //Chain decomposition
        chainDecomposition(reverseOrientedDFS_tree);

        //Count of both type of edges in reverse DFS tree
        int rEdgeCount = 0;
        for(Vertex v : reverseOrientedDFS_tree.getVertices()){
            rEdgeCount+= v.getEdges().size();
        }

        //Count of edges in chains
        int cEdgeCount = 0;
        for(ArrayList<Edge> chain : chains){
            cEdgeCount += chain.size();
        }

        /*If chain decomposition does not partition DFS-tree(there are edges in graph which are not in chain decomposition
        then our graph as a bridge therefore it's not 2-edge connected
         */
        //System.out.println(toRootOrientedDFS_tree.edgeCount() + " " + chainEdgeCount);
        if(rEdgeCount != cEdgeCount){
            return getResults(false);
        }
        else{
            return getResults(true);
        }
    }

    //To set opening and closing times of vertices in DFS
    private int dfi = 0;

    // DFS
    private void DFS(Graph graph, String start){

        dfs_results = new DFS_Table();

        //Adding all vertices to dfs table
        for(Vertex v : graph.getVertices()){
            dfs_results.addVertex(v.getName());
            dfs_results.setPredecessor(v.getName(), null);
        }

        for(Vertex v : graph.getVertices()){
            if(v.getName().equals(start)){
                DFS_visit(v);
            }
        }

        for(Vertex u : graph.getVertices()){
            if(u.getColor().equals("WHITE")){
                DFS_visit(u);
            }
        }
    }

    private void DFS_visit(Vertex u){

        u.setColor("GRAY");
        dfs_results.setDFI(u.getName(), ++dfi);
        for(Edge e : u.getEdges()){
            if(e.getSecond().getColor().equals("GRAY")){
                //If we found already visited vertex, but it's not a direct prececessor of current vertex, it's a back edge
                if(!dfs_results.isPredecessor(u.getName(), e.getSecond().getName())){
                    backEdges.add(new Edge(new Vertex(u.getName()), new Vertex(e.getSecond().getName()), "BACK EDGE"));
                }
            }

            if(e.getSecond().getColor().equals("WHITE")){
                //Adding predecessor
                dfs_results.setPredecessor(e.getSecond().getName(), u.getName());
                DFS_visit(e.getSecond());
            }
        }
        u.setColor("BLACK");
    }

    private void chainDecomposition(Graph DFS_tree){
        //Setting all vertices as white
        for(Vertex v : DFS_tree.getVertices()){
            v.setColor("WHITE");
        }
        for(Vertex v : DFS_tree.getVertices()){
            for(Edge e : v.getEdges()){
                if(e.getType().equals("BACK EDGE")) {
                    //If edge is back edge, setting it's first vertex as visited
                    e.getFirst().setColor("BLACK");
                    chains.add(new ArrayList<>());
                    /*adding back edge to chain no matter whether it's second vertex is visited or not, because entire
                    back edge always belongs to chain */
                    chains.get(chains.size() - 1).add(e);

                    //Current vertex is back edges second vertex
                    Vertex currVertex = e.getSecond();
                    //Until we get to a vertex which is already visited
                    while ((!currVertex.getColor().equals("BLACK"))){
                        //Setting current vertex as visited
                        currVertex.setColor("BLACK");

                        //Checking its other edges for tree edges. Edges are sorted by DFI order of their other vertex
                        for(Edge currE : currVertex.getEdges()){
                            if(currE.getType().equals("TREE EDGE")){
                                //Adding the edge to the chain
                                chains.get(chains.size() - 1).add(currE);
                                //Making edge's second vertex the current vertex
                                currVertex = currE.getSecond();
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /** Vertex data is held in file as adjacency lists. E.g:
     *
     * A B D                                A---B
     * B A D C          <=>                 |  /|
     * C B D                                | / |
     * D A C B                              |/  |
     *                                      D---C
     *
     * Graph is undirected, unweighted, |V| >= 3
     */
    private void readData(){
        mainGraph = new Graph();
        // Vertex adjacency lists
        ArrayList<ArrayList<String>> adjacencyLists = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(this.dataFile))){
            String line;

            while((line = br.readLine()) != null){
                adjacencyLists.add(Lists.newArrayList(Splitter.on(" ").split(line)));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //Adding each vertex to graph by taking first vertex of every adjacency list
        for (ArrayList<String> vertices : adjacencyLists) {
            mainGraph.getVertices().add(new Vertex(vertices.get(0)));
        }

        for(int i = 0; i < adjacencyLists.size(); ++i){

            Vertex v = mainGraph.getVertex(adjacencyLists.get(i).get(0));
            if(v == null)
                throw new IllegalArgumentException("Vertex " + v.getName() + " was not found in graph");

            //Connecting vertex with other vertices it is adjacent with(specified in file)
            for(int j = 1; j < adjacencyLists.get(i).size(); ++j){

                if(mainGraph.getVertex(adjacencyLists.get(i).get(j)) == null)
                    throw new IllegalArgumentException("Vertex " + adjacencyLists.get(i).get(j) + " was not found in graph");

                v.addEdge(mainGraph.getVertex(adjacencyLists.get(i).get(j)), "");
            }
        }
    }

    private void checkIfConnected(DFS_Table resultTable){
        int hasNoPredCount = 0;

        for(TableColumn vertexData : resultTable.getVertexData()){
            if(vertexData.getPred() == null){
                hasNoPredCount += 1;
            }
        }

        if(hasNoPredCount > 1){
            System.out.println("GRAPH IS NOT CONNECTED IN THE FIRST PLACE!");
            System.exit(1);
        }
    }

    /**
     * Returns count of cycles in chain decomposition
     * @return
     */
    private int detectCycles(){

        int cycleCount = 0;
        for(ArrayList<Edge> chain : chains){
            if(chain.get(0).getFirst().equals(chain.get(chain.size() - 1).getSecond())){
                cycleCount+=1;
            }
        }
        return cycleCount;
    }

    /**
     * Checks if graph is 2-connected if it's 2-edge connected
     * @param is2EdgeConnected
     * @return result
     */
    private String getResults(boolean is2EdgeConnected){

        //Checking additional properties if graph is 2-edge connected
        if(is2EdgeConnected) {

            //If graph has more cycles except the first chain, then graph is 2-edge connected but not 2-connected
            if(detectCycles() > 1){
                return "2-EDGE CONNECTED BUT NOT 2-CONNECTED";
            }
            //Otherwise 2-edge and 2-connected
            else
                return "2-CONNECTED";
        }
        //Graph is not 2-edge connected neither 2-connected
        else{
            return "NOT 2-EDGE CONNECTED NOR 2-CONNECTED";
        }
    }

    public Graph getMainGraph() {
        return mainGraph;
    }

    public Graph getReverseOrientedDFS_tree() {
        return reverseOrientedDFS_tree;
    }

    public void displayChains(){
        for(ArrayList<Edge> chain : this.chains){
            for(Edge e : chain){
                System.out.print(e.getFirst().getName() + "-->" + e.getSecond().getName() + " ");
            }
            System.out.println();
        }
    }
}
