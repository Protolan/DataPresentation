package TreeATD.SonsLists;

import TreeATD.TreeException;
import TreeATD.Interface.ITree;
import TreeATD.Interface.Label;

public class Tree implements ITree {

    //------------------------------------------------------------------------------------------------//
    private static class ArrayNode {
        private int name;
        private Node node;
        private Label label;
        public ArrayNode(int n){
            label = new Label(' ');
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
        _root = LAMBDA;
    }

    @Override
    public int parent(int n) {
        if(n == _root || n == LAMBDA || n > LEN) return LAMBDA;
        return findParent(n, _root);
    }

    @Override
    public int leftMostChild(int n) {
        // Если нет в дереве
        if(_array[n].node == null || n == _root || findParent(n, _root) != LAMBDA) return LAMBDA;
        return _array[n].node.name;
    }

    @Override
    public int rightSibling(int n) {
        if (n == _root) return LAMBDA;
        // Проверка, находится ли он в дереве
        int parent = findParent(n, _root);
        if(parent == LAMBDA) return LAMBDA;
        //Если у родителя больше двух детей
        if(_array[parent].node != null && _array[parent].node.next != null)
            return _array[parent].node.next.name;
        return LAMBDA;
    }

    @Override
    public Label label(int n) {
        if(n == _root || findParent(n, _root) != LAMBDA) return _array[n].label;
        else throw new TreeException("No such element in tree");
    }

    @Override
    public ITree create(Label label) {
        if(_space == LAMBDA) throw new TreeException("No free memory");
        //Если дерево пустое, инициализируем корневой узел
        if(_root != LAMBDA) {
            _array[_space].node = new Node(_root,null);
        }
        _array[_space].label = label;
        _root = _space;
        _space = _array[_space].name;
        return this;
    }

    @Override
    public ITree create(Label label, ITree tree1) {
        if(_space == LAMBDA) throw new TreeException("No free memory");
        if(tree1 == this) return create(label);
        // Если исходное дерево пустое
        if(_root == LAMBDA) return tree1.create(label);
        // Если добавляемое дерево пустое, добавляем метку
        if(tree1.root() == LAMBDA) return create(label);

        _array[_space].label = label;
        _array[_space].node = new Node(_root, new Node(tree1.root(), null));
        _root = _space;
        _space = _array[_space].name;

        return this;
    }

    @Override
    public int root() {
        return _root;
    }

    @Override
    public void makeNull() {
        //Если дерев пустое
        if (_root == LAMBDA) return;
        clearTree(_root);
        _array[_root].name = _space;
        _space = _root;
        _root = LAMBDA;
    }

    //Находит родителя узла, использует прямой обход
    private int findParent(int of, int in){
        Node current = _array[in].node;

        while (current != null){
            if (current.name == of) return in;

            int nextParent = findParent(of, current.name);
            if (nextParent == LAMBDA)
                current = current.next;
            else return nextParent;
        }
        return LAMBDA;
    }


    //Обнуляет дерево, использует обратный обход
    private void clearTree(int from){
        Node current = _array[from].node;

        while (current != null){

            //Доходи до листа
            if (_array[current.name].node != null) {
                clearTree(current.name);
            }

            //Обнуление
            _array[current.name].name = _space;
            _space = current.name;
            ///Переход к следущему
            current = current.next;

        }
    }


    // PRINT

    public static void print(){
        int a, b;
        for (int i = 0; i < LEN; i++) {
            if ((int) _array[i].label.value == 0) {
                System.out.print("[" + _array[i].name + "] *");
            } else {
                System.out.print("[" + _array[i].name + "] " + _array[i].label.value);
            }
            if (_array[i].node != null) {
                a = _array[i].node.name + 1;
                System.out.print(" -> [" + a + "]");
                if (_array[i].node.next != null) {
                    b = _array[i].node.next.name + 1;
                    System.out.print(" -> [" + b + "]");
                }
            }
            System.out.println();
        }
    }

    private void printByPass(int r){
        Node current = _array[r].node;

        while (current != null){
            System.out.println(current.name);
            if (_array[current.name].node != null) {
                printByPass(current.name);
            }
            current = current.next;
        }
    }

}
