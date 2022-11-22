package TreeATD;

import TreeATD.Interface.Label;
import TreeATD.SonsLists.Tree;

public class Test {
    public static void main(String[] args) {
        Tree t1 = new Tree();
        t1.create(new Label('a'));

        Tree t = new Tree();
        t.create(new Label('b'));

        t1.create(new Label('c'), t);

        t = new Tree();
        t.create(new Label('d'));

        Tree t2 = new Tree();
        t2.create(new Label('e'));

        t.create(new Label('f'), t2);

        t1.create(new Label('G'), t);

        int l1 = t1.leftMostChild(t1.root());
        t1.label(l1).print();
        t1.label(t1.rightSibling(l1)).print();
        t1.label(t1.parent(t1.rightSibling(l1))).print();

        int l = t1.leftMostChild(l1);
        t1.label(l).print();
        t1.label(t1.rightSibling(l)).print();
        t1.label(t1.parent(l)).print();
        t1.label(t1.parent(t1.rightSibling(l))).print();

        int l2 = t1.rightSibling(l1);
        t1.label(t1.leftMostChild(l2)).print();
        t1.label(t1.rightSibling(t1.leftMostChild(l2))).print();
        t1.label(t1.parent(t1.leftMostChild(l2))).print();
        t1.label(t1.parent(t1.rightSibling(t1.leftMostChild(l2)))).print();

        System.out.println("\n\n\n");
        t1.print();
        t1.makeNull();
        t1.print();
    }
}
