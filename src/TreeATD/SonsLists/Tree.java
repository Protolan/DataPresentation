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
        _root = LAMBDA;
    }

    @Override
    public int parent(int n) {
        if(n == _root || n == LAMBDA || n > LEN) throw new TreeException("Incorrect index");
        return findParent(n, _root);
    }

    @Override
    public int leftMostChild(int n) {
        if (_array[n].next == null) throw new TreeException("Leave has no children");
        // Если нет в дереве
        if(!isInTree(n)) return LAMBDA;
        return _array[n].next.name;
    }

    @Override
    public int rightSibling(int n) {
        if (n == _root) throw new TreeException("Root has no siblings");
        // Проверка, находится ли он в дереве
        int parent = findParent(n, _root);
        if(parent == LAMBDA) return LAMBDA;
        //Если у родителя больше двух детей
        if(_array[parent].next != null && _array[parent].next.next != null)
            return _array[parent].next.next.name;
        return LAMBDA;
    }

    @Override
    public Label label(int n) {
        if(isInTree(n)) return _array[n].label;
        else throw new TreeException("No such element in tree");
    }

    @Override
    public ITree create(Label label) {
        if(_space == LAMBDA) throw new TreeException("No free memory");
        //Если дерево пустое, инициализируем корневой узел
        if(_root != LAMBDA) {
            _array[_space].next = new Node(_root,null);
        }
        _array[_space].label = label;
        _root = _space;
        _space = _array[_space].name;
        return this;
    }

    @Override
    public ITree create(Label label, ITree tree1) {
        if(_space == LAMBDA) throw new TreeException("No free memory");
        // Если базовое дерево пустое
        if(_root == LAMBDA) return tree1.create(label);
        // Если добавляемое дерево пустое, добавляем метку
        if(tree1.root() == LAMBDA) return create(label);

        _array[_space].label = label;
        _array[_space].next = new Node(_root, new Node(tree1.root(), null));
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

    //Есть ли такой элемент в дереве
    private boolean isInTree(int n) {
        if(n == _root) return true;
        if(findParent(n, _root) == LAMBDA)
            return false;
        return true;
    }

    //Обнуляет дерево, использует обратный обход
    private void clearTree(int from){
        Node current = _array[from].next;

        while (current != null){

            //Доходи до листа
            if (_array[current.name].next != null) {
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

    public void print(){
        System.out.println();
        if (_root == LAMBDA) return;
        System.out.println(_root);
        printByPass(_root);
        System.out.println();
    }

    private void printByPass(int r){
        Node current = _array[r].next;

        while (current != null){
            System.out.println(current.name);
            if (_array[current.name].next != null) {
                printByPass(current.name);
            }
            current = current.next;
        }
    }

    //


}
