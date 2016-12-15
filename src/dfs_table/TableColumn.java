package dfs_table;

/**
 * @author Lukas
 */
/**
 * Column for DFS_Table
 */
public class TableColumn implements Comparable{

    private String vertex;
    //vertex predecessor
    private String pred;
    //depth first index
    private int dfi;

    public int getDfi() { return dfi; }

    public void setDfi(int dfi) { this.dfi = dfi; }

    public String getPred() {
        return pred;
    }

    public void setPred(String pred) {
        this.pred = pred;
    }

    public String getVertex() {
        return vertex;
    }

    public void setVertex(String vertex) {
        this.vertex = vertex;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof TableColumn){
            if(this.getDfi() < ((TableColumn) o).getDfi()){
                return - 1;
            }
            if(this.getDfi() > ((TableColumn) o).getDfi()){
                return 1;
            }
            else
                return 0;
        }
        throw new IllegalArgumentException("Could not compare table rows using opening time");
    }
}