package TreeATD.LeftSonsRightSiblings;

import TreeATD.Interface.ITree;
import TreeATD.Interface.Label;

public class Tree implements ITree {

    private static class Node{
        int name;
        int rightSibling;
        int leftSon;
        char label;
        public Node(int n, int sib, int son, char l){
            name = n;
            rightSibling = sib;
            leftSon = son;
            label = l;
        }
    }

    private static Node[] _array;
    private static int _space;
    private static final int LEN = 10;
    private int _root;

    static {
        _array = new Node[LEN];
        for (int i = 0; i < LEN - 1; i ++){
            _array[i] = new Node(i + 1, -1, -1, '0');
        }
        _array[LEN - 1] = new Node(-1,-1,-1,'0');
        _space = 0;
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
        return 0;
    }

    @Override
    public Label label(int n) {
        return null;
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
}
