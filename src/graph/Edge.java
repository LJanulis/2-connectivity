package graph;

/**
 * @author Lukas
 */
public class Edge implements Comparable{

    private Vertex v1;
    private Vertex v2;

    //Type of edge: back edge, tree edge
    private String type;

    public Edge(Vertex v1, Vertex v2, String type){
        this.v1 = v1;
        this.v2 = v2;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Vertex getFirst(){
        return this.v1;
    }

    public Vertex getSecond(){
        return this.v2;
    }

    public String toString(){
        return "First: " + v1.getName() + " second: " + v2.getName() + " type: " + this.type;
    }

    public void setFirst(Vertex v1) {
        this.v1 = v1;
    }

    public void setSecond(Vertex v2) {
        this.v2 = v2;
    }

    @Override
    public int compareTo(Object o) {

        if(o instanceof  Edge){
            char v12 = this.v2.getName().charAt(0);
            char v22 = ((Edge) o).getSecond().getName().charAt(0);
            if(v12 < v22)
                return -1;
            if(v12 > v22)
                return 1;
            return 0;
        }
        throw new IllegalArgumentException("Error sorting edge order");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;

        Edge edge = (Edge) o;

        if (!v1.equals(edge.v1)) return false;
        if (!v2.equals(edge.v2)) return false;
        return getType() != null ? getType().equals(edge.getType()) : edge.getType() == null;
    }

    @Override
    public int hashCode() {
        int result = v1.hashCode();
        result = 31 * result + v2.hashCode();
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        return result;
    }
}