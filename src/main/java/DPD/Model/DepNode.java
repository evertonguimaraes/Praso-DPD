package DPD.Model;

/**
 * Created by Justice on 1/12/2017.
 */
public class DepNode {
    public int row; // classId on dependency row
    public int col; // classId on dependent row
    public String value;
    public int numValue;

    public DepNode(String data, int row, int col) {
        this.value = data;
        this.row = row;
        this.col = col;
        this.numValue = Integer.parseInt(this.value, 2);
    }
}