package ListATD.Cursor;

import Exceptions.WrongPositionException;
import ListATD.Interface.IList;
import Data.AddressData;

public class List implements IList<Position> {
    private static final Node[] _mem;
    private static final Position _space;
    private int head;

    static {
        _mem = new Node[50];
        _space = new Position(0);
        for (int i = 0; i < _mem.length - 1; i++){
            _mem[i] = new Node(i + 1);
        }
        _mem[_mem.length - 1] = new Node(-1);
    }

    public List(){
        head = -1;
    }

    @Override
    public void insert(AddressData d, Position p) {
        if (_space.x == -1) return;


        if (p.x == -1){
            // Вставка в пустой список
            if (head == -1){
                head = _space.x;
                _space.x = _mem[_space.x].Next;
                _mem[head].setNode(d, -1);
                return;
            }

            // Вставка в последний элемент
            int temp = _space.x;
            _space.x = _mem[_space.x].Next;
            int pos = getLast();
            _mem[pos].Next = temp;
            _mem[temp].setNode(d, -1);
            return;
        }


        if(p.x != head) {
            int temp = getPrevious(p);
            if (temp == -1) return;
            _mem[temp].Next = _space.x;
        }

        int tempSpace = _space.x;
        _space.x = _mem[_space.x].Next;

        _mem[tempSpace].setNode(_mem[p.x].Data, _mem[p.x].Next);
        _mem[p.x].setNode(d, tempSpace);


//        //inserting into head
//        if (p.x == head){
//            int tempSpace = space.x;
//            space.x = mem[space.x].Next;
//
//            mem[tempSpace].setNode(mem[head].Data, mem[head].Next);
//            mem[head].setNode(d, tempSpace);
//            return;
//        }
//
//        //all others situations
//        int temp = getPrevious(p);
//        if (temp == -1) return;
//
//        int myElement = mem[temp].Next;
//        int tempSpace = space.x;
//
//        mem[tempSpace] = new ListATD.CursorPresentation.ListATD.DoublyLinkListPresentation.ListATD.LinkedListPresentation.Node(d);
//        mem[temp].Next = tempSpace;
//        mem[tempSpace].Next = myElement;
//        space.x = mem[space.x].Next;

    }

    @Override
    public Position locate(AddressData x) {
        if (x == null || head == -1) return null;
        return (search(x));
    }

    @Override
    public AddressData retrieve(Position p)  {
        if (p.x < 0 || p.x > _mem.length) throw new WrongPositionException("incorrect index");
        if (p.x == head) return _mem[head].Data;

        int temp = getPrevious(p);
        if (temp == -1) throw new WrongPositionException("incorrect index");
        temp = _mem[temp].Next;
        if (temp == -1) throw new WrongPositionException("incorrect index");

        return _mem[temp].Data;
    }

    @Override
    public void delete(Position p) {
        if (head == -1) return;

        if (p.x == head){
            _space.x = head;
            _mem[_space.x].Next = _space.x;
            head = _mem[head].Next;
            p.x = head;
            return;
        }

        int temp = getPrevious(p);
        if (temp == -1) return;
        int next = _mem[temp].Next;
        if (next == -1) return;
        _mem[temp].Next = _mem[next].Next;
        _mem[next].Next = _space.x;
        _space.x = next;
        p.x = _mem[temp].Next;
    }

    @Override
    public Position next(Position p) {
        if (p.x > _mem.length) throw new WrongPositionException("incorrect index");

        //next (head)
        if (p.x == head) return new Position(_mem[head].Next);


        int temp = getPrevious(p);
        if (temp == -1) throw new WrongPositionException("incorrect index");
        temp = _mem[temp].Next;
        if (temp == -1) throw new WrongPositionException("incorrect index");


        return new Position(_mem[temp].Next);
    }

    @Override
    public Position previous(Position p)  {
        if (p.x > _mem.length || p.x == head) throw new WrongPositionException("incorrect index");

        int temp = getPrevious(p);
        if (temp == -1) throw new WrongPositionException("incorrect index");
        return new Position(temp);
    }

    @Override
    public Position first() {
        return new Position(head);
    }

    @Override
    public void makeNull() {
        if (head == -1) return;
        _mem[getLast()].Next =(_space.x);
        _space.x = head;
        head = -1;
    }

    @Override
    public void printList() {
        if (head == -1 || _mem[head].Data == null){
            System.out.println("The List is empty");
            return;
        }
        int q = head;
        int i = 1;
        while (true){
            System.out.print((i) + ") ");
            _mem[q].Data.printData();
            if (_mem[q].Next == -1){
                return;
            }
            q = _mem[q].Next;
            i++;
        }
    }

    @Override
    public Position end() {
        return new Position(-1);
    }

    private int getPrevious(Position p) {
        int q = head;
        int q2 = -1;

        while (q != -1){
            if (p.x == q) {
                return q2;
            }
            q2 = q;
            q = _mem[q].Next;
        }
        return -1;
    }

    private int getLast() {
        int q = head;
        int q2 = -1;

        while (q != -1){
            q2 = q;
            q = _mem[q].Next;
        }
        return q2;
    }

    private Position search(AddressData x){
        int q = head;

        while (q != -1){
            if (_mem[q].Data.equals(x)) {
                return new Position(q);
            }
            q = _mem[q].Next;
        }
        return new Position(-1);
    }
}
