package TreeATD;

import TreeATD.Interface.Label;
import TreeATD.SonsLists.Tree;

public class Test {
    private static final int LAMBDA = -1;

    public static void main(String[] args) {
        Tree t1 = new Tree();
        t1.create(new Label('a'));
        t1.create(new Label('b'));
        Tree t2 = new Tree();
        t2.create(new Label('d'));
        t2.create(new Label('e'));
        t1.create(new Label('f'), t2);
        Tree.print();
    }

    //Симетричный обход бинарного дерева
    private static void printInOrderTraversalBinary(Tree tree, int from) {
        if (from == LAMBDA) return;
        int leftChild = tree.leftMostChild(from);
        printInOrderTraversalBinary(tree, leftChild);
        System.out.println(tree.label(from).value + " ");
        printInOrderTraversalBinary(tree, tree.rightSibling(leftChild));
    }



    //Симетричный обход произвольного дерева
    private static void printInOrderTraversal(Tree tree, int from) {
        if (from == LAMBDA) return;
        int child = tree.leftMostChild(from);
        // Идем до предпоследнего ребенка
        if (child != LAMBDA) {
            int rightSibling = tree.rightSibling(child);
            while (rightSibling != LAMBDA) {
                printInOrderTraversal(tree, child);
                child = rightSibling;
                rightSibling = tree.rightSibling(child);
            }
        }
        System.out.print(from + " ");
        // Самый правый сын
        printInOrderTraversal(tree, child);
    }

}
