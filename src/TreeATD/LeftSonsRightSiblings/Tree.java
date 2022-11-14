package TreeATD.LeftSonsRightSiblings;

import TreeATD.Interface.ITree;
import TreeATD.Interface.Label;

public class Tree implements ITree {
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
