package TreeATD.Interface;

public interface ITree {
    int parent(int n);
    int leftMostChild(int n);
    int rightSibling(int n);
    Label label(int n);
    ITree create(Label label);
    ITree create(Label label, ITree tree1);
    ITree create(Label label, ITree tree1, ITree tree2);
    int root();
    void makeNull();
}
