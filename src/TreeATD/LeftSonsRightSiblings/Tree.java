package TreeATD.LeftSonsRightSiblings;

import TreeATD.TreeException;
import TreeATD.Interface.ITree;
import TreeATD.Interface.Label;

public class Tree implements ITree {

    private static class Node{
        int name;
        int rightSibling;
        int leftSon;
        Label label;
        public Node(int n, int sib, int son){
            name = n;
            rightSibling = sib;
            leftSon = son;
            label = new Label(' ');
        }
    }

    private static final Node[] _array;
    private static int _space;
    private static final int LEN = 10;
    private final static int LAMBDA = -1;
    private int _root;

    static {
        _array = new Node[LEN];
        for (int i = 0; i < LEN - 1; i ++){
            _array[i] = new Node(i + 1, LAMBDA, LAMBDA);
        }
        _array[LEN - 1] = new Node(LAMBDA,LAMBDA, LAMBDA);
        _space = 0;
    }

    public Tree() {_root = LAMBDA; };

    @Override
    public int parent(int n) {
        if(n == _root || n == LAMBDA || n > LEN) return LAMBDA;
        return findParent(n, _root);
    }

    @Override
    public int leftMostChild(int n) {
        if(_root == LAMBDA || _array[_root].leftSon == LAMBDA) return LAMBDA;
        if(_array[_root].leftSon == n) return _root;
        int parent = findParent(n, _root);
        if(parent == LAMBDA) {
            System.out.println("parentNotFound");
            return LAMBDA;
        }
        return _array[parent].leftSon;
    }

    @Override
    public int rightSibling(int n) {
        if(_root == LAMBDA || _array[_root].rightSibling == LAMBDA) return LAMBDA;
        if(_array[_root].rightSibling == n) return _root;
        int parent = findParent(n, _root);
        if(parent == LAMBDA) return LAMBDA;
        return _array[parent].rightSibling;
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
        if (_root != LAMBDA) {
            _array[_space].leftSon = _root;
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
        if (_root == LAMBDA) return tree1.create(label);
        // Если добавляемое дерево пустое, добавляем метку или Если добавляемое это же дерево возрвашаем наше дерево
        if(tree1.root() == LAMBDA || tree1 == this) return create(label);
        _array[_space].label = label;
        _array[_space].leftSon = _root;
        _array[_root].rightSibling = tree1.root();
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
        //Если дерево пустое
        if (_root == LAMBDA) return;
        clearTree(_root);
        _array[_root].name = _space;
        _space = _root;
        _root = LAMBDA;
    }

    //Находит родителя узла, использует прямой обход
    private int findParent(int of, int in){
        int current = _array[in].leftSon;
        while (current != LAMBDA){
            if (of == current) return in;
            int temp = findParent(of, current);
            if (temp != LAMBDA)
                return temp;
            current = _array[current].rightSibling;
        }
        return LAMBDA;
    }

//    private int findParent(int leaf, int child) {
//        if (is_child(child, leaf)) {
//            return leaf;
//        }
//        if (_array[leaf].leftSon != -1) {
//            int temp = findParent(_array[leaf].leftSon, child);
//            if (temp != LAMBDA) {
//                return temp;
//            }
//            if (_array[_array[leaf].leftSon].rightSibling != -1) {
//                temp = findParent(_array[_array[leaf].leftSon].rightSibling, child);
//                if (temp != LAMBDA) {
//                    return temp;
//                }
//            }
//        }
//        return LAMBDA;
//    }

    //Обнуляет дерево, использует обратный обход
    private void clearTree(int from){
        int current = _array[from].leftSon;

        while (current != LAMBDA){
            //Доходи до листа
            if (_array[current].leftSon != LAMBDA) {
                clearTree(current);
            }
            //Обнуление
            _array[current].name = _space;
            _space = current;
            ///Переход к следущему
            current = _array[current].rightSibling;

        }
    }


    // PRINT

    public static void print() {
        System.out.printf("%2s  %5s  %s  %n","LS", "Label", "RS");
        for (int i = 0; i < LEN - 1; i++) {
            System.out.printf("%2d |%3c  | %2d %n",
                    _array[i].leftSon,
                    _array[i].label.value,
                    _array[i].rightSibling
            );
        }
        System.out.printf("%2d |%3s  | %2d %n",
                _array[LEN - 1].leftSon,
                _array[LEN - 1].label.value,
                _array[LEN - 1].rightSibling
        );
        System.out.println();
    }

    public void inorder(int index) {
        if(index == LAMBDA) return;
        if(_array[index].leftSon == LAMBDA) {
            System.out.print(_array[index].label.value + " ");
        }
        else {
            inorder(_array[index].leftSon);
            System.out.print(_array[index].label.value + " ");
            inorder(_array[_array[index].leftSon].rightSibling);

        }


    }

    //
}
