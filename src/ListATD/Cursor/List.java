package ListATD.Cursor;

import Exceptions.WrongPositionException;
import ListATD.Interface.IList;
import Data.AddressData;

public class List implements IList<Position> {
    private static final Node[] mem;
    private static final Position space;
    private int head;

    static {
        mem = new Node[50];
        space = new Position(0);
        for (int i = 0; i < mem.length - 1; i++){
            mem[i] = new Node(i + 1);
        }
        mem[mem.length - 1] = new Node(-1);
    }

    public List(){
        head = -1;
    }

    @Override
    public void insert(AddressData d, Position p) {
        if (space.x == -1) return;


        if (p.x == -1){
            // Вставка в пустой список
            if (head == -1){
                head = space.x;
                space.x = mem[space.x].Next;
                mem[head].setNode(d, -1);
                return;
            }

            // Вставка в последний элемент
            int temp = space.x;
            space.x = mem[space.x].Next;
            int pos = getLast();
            mem[pos].Next = temp;
            mem[temp].setNode(d, -1);
            return;
        }


        if(p.x != head) {
            int temp = getPrevious(p);
            if (temp == -1) return;
            mem[temp].Next = space.x;
        }

        int tempSpace = space.x;
        space.x = mem[space.x].Next;

        mem[tempSpace].setNode(mem[p.x].Data, mem[p.x].Next);
        mem[p.x].setNode(d, tempSpace);


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
        if (p.x < 0 || p.x > mem.length) throw new WrongPositionException("incorrect index");
        if (p.x == head) return mem[head].Data;

        int temp = getPrevious(p);
        if (temp == -1) throw new WrongPositionException("incorrect index");
        temp = mem[temp].Next;
        if (temp == -1) throw new WrongPositionException("incorrect index");

        return mem[temp].Data;
    }

    @Override
    public void delete(Position p) {
        if (head == -1) return;

        if (p.x == head){
            space.x = head;
            mem[space.x].Next = space.x;
            head = mem[head].Next;
            p.x = head;
            return;
        }

        int temp = getPrevious(p);
        if (temp == -1) return;
        int next = mem[temp].Next;
        if (next == -1) return;
        mem[temp].Next = mem[next].Next;
        mem[next].Next = space.x;
        space.x = next;
        p.x = mem[temp].Next;
    }

    @Override
    public Position next(Position p) {
        if (p.x > mem.length) throw new WrongPositionException("incorrect index");

        //next (head)
        if (p.x == head) return new Position(mem[head].Next);


        int temp = getPrevious(p);
        if (temp == -1) throw new WrongPositionException("incorrect index");
        temp = mem[temp].Next;
        if (temp == -1) throw new WrongPositionException("incorrect index");


        return new Position(mem[temp].Next);
    }

    @Override
    public Position previous(Position p)  {
        if (p.x > mem.length || p.x == head) throw new WrongPositionException("incorrect index");

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
        mem[getLast()].Next =(space.x);
        space.x = head;
        head = -1;
    }

    @Override
    public void printList() {
        if (head == -1 || mem[head].Data == null){
            System.out.println("The List is empty");
            return;
        }
        int q = head;
        int i = 1;
        while (true){
            System.out.print((i) + ") ");
            mem[q].Data.printData();
            if (mem[q].Next == -1){
                return;
            }
            q = mem[q].Next;
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
            q = mem[q].Next;
        }
        return -1;
    }

    private int getLast() {
        int q = head;
        int q2 = -1;

        while (q != -1){
            q2 = q;
            q = mem[q].Next;
        }
        return q2;
    }

    private Position search(AddressData x){
        int q = head;

        while (q != -1){
            if (mem[q].Data.equals(x)) {
                return new Position(q);
            }
            q = mem[q].Next;
        }
        return new Position(-1);
    }
}
