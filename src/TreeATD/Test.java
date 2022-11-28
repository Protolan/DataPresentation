package TreeATD;

import TreeATD.SonsLists.Tree;

public class Test {
     private static final int LAMBDA = -1;
    public static void main(String[] args) {
        Tree tree = new Tree();

    }

    //Симетричный обход бинарного дерева
    private void printInOrderTraversalBinary(Tree tree, int from){
        if (from == LAMBDA) return;
        int leftChild = tree.leftMostChild(from);
        printInOrderTraversalBinary(tree, leftChild);
        System.out.println(from + " ");
        printInOrderTraversalBinary(tree, tree.rightSibling(leftChild));
    }

    //Симетричный обход произвольного дерева
    private void printInOrderTraversal(Tree tree, int from){
        if (from == LAMBDA) return;
        int child = tree.leftMostChild(from);
        // Идем до предпоследнего ребенка
        if(child != LAMBDA) {
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
