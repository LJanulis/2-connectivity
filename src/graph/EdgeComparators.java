package graph;

import java.util.Comparator;

/**
 * @author Lukas
 */
public class EdgeComparators {

    /**
     * Used to sort vertex's connected vertices in ascending order alphabetically
     * A D B C F G -> A B C D F G
     * First vertex is source vertex, rest its connections
     */
    static class AlphabeticalEdgeComparator implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            if(o1 instanceof Edge && o2 instanceof Edge){
                char c1 = ((Edge) o1).getSecond().getName().charAt(0);
                char c2 = ((Edge) o2).getSecond().getName().charAt(0);

                if(c1 < c2)
                    return -1;
                if(c1 > c2)
                    return 1;
                return 0;
            }
            throw new IllegalArgumentException("Comparator could not find out vertex ordering");
        }
    }

    static class NumericalEdgeComparator implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            if(o1 instanceof Edge && o2 instanceof Edge){
                int c1 = Integer.parseInt(((Edge) o1).getSecond().getName());
                int c2 = Integer.parseInt(((Edge) o2).getSecond().getName());

                if(c1 < c2)
                    return -1;
                if(c1 > c2)
                    return 1;
                return 0;
            }
            throw new IllegalArgumentException("Comparator could not find out vertex ordering");
        }
    }

    /**
     * Used to sort vertex's connected vertices to ascending DFI order
     */
    static class DFIEdgeComparator implements Comparator{

        @Override
        public int compare(Object o1, Object o2) {
            if(o1 instanceof Edge && o2 instanceof Edge){
                int dfi1 = ((Edge) o1).getSecond().getDepthFirstIndex();
                int dfi2 = ((Edge) o2).getSecond().getDepthFirstIndex();

                if(dfi1 < dfi2)
                    return -1;
                if(dfi1 > dfi2)
                    return 1;
                return 0;
            }
            throw new IllegalArgumentException("Comparator could not find out vertex ordering");
        }
    }
}
