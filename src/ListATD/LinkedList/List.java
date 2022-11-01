package ListATD.LinkedList;

import ListATD.Interface.IList;
import Data.AddressData;
import Exceptions.WrongPositionException;

public class List implements IList<Position> {
    private Node _head;

    public List() {
        _head = null;
    }

    @Override
    public void insert(AddressData d, Position p) {
        // Если лист пустой новый элемент = голова
        if (_head == null) {
            if (p.node == null) {
                _head = new Node(d);
            }
            return;
        }

        // Если позиция это последний элемент
        if (p.node == null) {
            Position last = new Position(getLast());
            last.node.Next = new Node(d);
            return;
        }


        // Остальные случаи
        Position previous = new Position(getPrevious(p));
        if (p.node != _head && previous.node == null)
            return;

        Node temp = p.node.Next;
        p.node.Next = new Node(p.node.AddressData);
        p.node.Next.Next = temp;
        p.node.AddressData = d;
    }



    @Override
    public Position locate(AddressData x) {
        if (x == null) return null;
        return new Position(search(x));
    }

    @Override
    public AddressData retrieve(Position p)  {
        if (p == null) throw new WrongPositionException("no such position in this List");

        if (p.node == _head) {
            return _head.AddressData;
        }

        Node temp = getPrevious(p);
        if (temp == null) throw new WrongPositionException("no such position in this List");
        return p.node.AddressData;
    }

    @Override
    public void delete(Position p) {
        if (p == null || _head == null) return;

        //deleting head
        if (p.node == _head) {
            _head = _head.Next;
            return;
        }

        Position temp = new Position(getPrevious(p));
        if (temp.node != null) {
            Node nodeNext = temp.node.Next;
            temp.node.Next = nodeNext.Next;
            p.node = temp.node.Next;
        }
    }

    @Override
    public Position next(Position p)  {
        if (p == null) throw new WrongPositionException("No such position in the list");

        if (p.node == _head) return new Position(_head.Next);

        Node temp = getPrevious(p);
        if (temp == null) throw new WrongPositionException("No such position in the list");
        return new Position(temp.Next.Next);
    }

    @Override
    public Position previous(Position p)  {
        if (p == null || p.node == _head) throw new WrongPositionException("No such position in the list");

        Node result = getPrevious(p);
        if (result != null) return new Position(result);
        throw new WrongPositionException("No such position in the list");
    }

    @Override
    public Position first() {
        return new Position(_head);
    }

    @Override
    public void makeNull() {
        _head = null;
    }

    @Override
    public void printList() {
        if (_head == null || _head.AddressData == null) {
            System.out.println("The List is empty");
            return;
        }
        Node p = _head;
        int i = 1;
        while (p != null) {
            System.out.print((i) + ") ");
            p.AddressData.printData();
            p = p.Next;
            i++;
        }
        System.out.println();
    }

    @Override
    public Position end() {
        return new Position(null);
    }

    private Node getPrevious(Position p) {
        Node q = _head.Next;
        Node q2 = _head;

        while (q != null) {
            if (p.node == q) {
                return q2;
            }
            q2 = q;
            q = q.Next;
        }
        return null;
    }

    private Node getLast() {
        Node q = _head;
        Node q2 = null;
        while (q != null) {
            q2 = q;
            q = q.Next;
        }
        return q2;
    }

    private Node search(AddressData x) {
        Node q = _head;
        while (q != null) {
            if (q.AddressData.equals(x))
                return q;
            q = q.Next;
        }
        return null;
    }



}

