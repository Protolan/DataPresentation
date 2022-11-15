package TreeATD.SonsLists;

import Exceptions.TreeException;
import TreeATD.Interface.ITree;
import TreeATD.Interface.Label;

public class Tree implements ITree {

    //------------------------------------------------------------------------------------------------//
    private static class ArrayNode {
        private int name;
        private Node next;
        private Label label;
        public ArrayNode(int n){
            name = n;
        }
    }

    private static class Node {

        private final int name;
        private final Node next;

        private Node(int name, Node next) {
            this.name = name;
            this.next = next;
        }
    }

    //------------------------------------------------------------------------------------------------//

    private int _root;
    private static final ArrayNode[] _array;
    private static int _space;
    private final static int LAMBDA = -1;
    private final static int LEN = 10;

    static {
        _array = new ArrayNode[LEN];
        for (int i = 0; i < LEN -1; i++) {
            _array[i] = new ArrayNode(i+1);
        }
        _space = 0;
        _array[LEN -1] = new ArrayNode(-1);
    }

    //------------------------------------------------------------------------------------------------//
    public Tree() {
        _root = -1;
    }

    @Override
    public int parent(int n) {
        return 0;
    }

    @Override
    public int leftMostChild(int n) {
        return 0;
    }

    @Override
    public int rightSibling(int n) {
        if (n == _root) throw new TreeException("Root has no siblings");
        int parent = findParent(n, _root);
        //Если не родителя, нет и брата
        if(parent == LAMBDA) return LAMBDA;
        //Если у родителя больше двух детей
        if(_array[parent].next != null && _array[parent].next.next != null)
            return _array[parent].next.next.name;
        return LAMBDA;
    }

    @Override
    public Label label(int n) {
        if(n == _root) return _array[n].label;
        if(isInTree(n)) return _array[n].label;
        else throw new TreeException("No such element in tree");
    }

    @Override
    public ITree create(Label label) {
        return null;
    }

    @Override
    public ITree create(Label label, ITree tree1) {
        return null;
    }

    @Override
    public ITree create(Label label, ITree tree1, ITree tree2) {
        return null;
    }

    @Override
    public int root() {
        return 0;
    }

    @Override
    public void makeNull() {

    }

    //Находит родителя узла
    private int findParent(int of, int in){
        Node current = _array[in].next;

        while (current != null){
            if (current.name == of) return in;

            int nextParent = findParent(of, current.name);
            if (nextParent == LAMBDA)
                current = current.next;
            else return nextParent;
        }
        return LAMBDA;
    }

    private boolean isInTree(int n) {
        if(findParent(n, _root) == LAMBDA)
            return false;
        return true;
    }
}
