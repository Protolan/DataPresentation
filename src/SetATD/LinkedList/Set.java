package SetATD.LinkedList;

import Data.AddressData;

public class Set {
    private class Node{
        public AddressData data;
        public Node next;
        public Node(AddressData data, Node n){
            this.data = data;
            next = n;
        }
    }

    private Node _head;


    public Set() { _head = null; }
}
