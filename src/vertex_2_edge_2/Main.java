package vertex_2_edge_2;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        try {
            if (args.length == 2) {

                if (!new File(args[0]).exists()) {
                    throw new IllegalArgumentException("No such file found: " + args[0]);
                }
                Test_2connectivity graphTest = new Test_2connectivity(args[0]);
                String result = graphTest.performTest(args[1]);

                System.out.println("INPUT GRAPH:");

                graphTest.getMainGraph().getVertices().forEach(System.out::println);

                System.out.println(System.lineSeparator() + "REVERSE-EDGE DFS-SPANNING TREE WITH BACK EDGES:");

                graphTest.getReverseOrientedDFS_tree().printEdges();
                System.out.println(System.lineSeparator() + "CHAIN DECOMPOSITION: ");
                graphTest.displayChains();
                System.out.println();

                System.out.println(result);
            }
            else {
                throw new IllegalArgumentException("Illegal number of arguments!");
            }
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
}
