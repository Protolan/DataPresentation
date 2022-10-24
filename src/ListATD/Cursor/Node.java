package ListATD.Cursor;

import Data.AddressData;

public class Node {
    public AddressData Data;
    public int Next = -1;

    public void setNode(AddressData d, int next) {
        Data = d;
        Next = next;
    }

    public Node(AddressData d) {
        Data = d;
    }

    public Node(int n) {
        Next = n;
    }
}
