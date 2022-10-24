package DoublyLinkListPresentation;

import Data.AddressData;
import Exceptions.WrongPositionException;
import Interface.IList;

public class List implements IList<Position> {
    private Node head;
    private Node tail;

    public List() {
        head = null;
        tail = null;
    }

    @Override
    public void insert(AddressData d, Position p) {
        if (head == tail) { // Случай с единственным элементом
            if (head == null) { // Если список был пустой, создаем голову и хвост
                head = new Node(d);
                tail = head;
                return;
            }

            head.Next = new Node(head.Data); // Создаем следущий элемент после головы
            head.Data = d; // Обновляем данные головы
            tail = head.Next; //
            tail.Prev = head;

            return;
        }


        // Если позиция это последний элемент
        if (p.node == null) {
            tail.Next = new Node(d);
            tail.Next.Prev = tail;
            tail = tail.Next;
            return;
        }


        //Остальные случаи
        Node temp;
        if (p.node == head || exist(p))
            temp = p.node;
        else return;

        Node next = temp.Next;
        temp.Next = new Node(temp.Data);
        temp.Data = d;
        temp.Next.Next = next;
        temp.Next.Prev = temp;
        next.Prev = temp.Next;
    }

    @Override
    public Position locate(AddressData x) {
        if (x == null || head == null) return null;
        return new Position(search(x));
    }

    @Override
    public AddressData retrieve(Position p) throws WrongPositionException {
        if (head == null) throw new WrongPositionException("list is empty");
        if (p == null || !exist(p)) throw new WrongPositionException("no such position in this List");
        return p.node.Data;
    }

    @Override
    public void delete(Position p) {
        if (head == null || tail == null || p == null) return;

        //Удаление последнего элемента списка
        if (head == tail && p.node == head) {
            head = null;
            tail = null;
        }

        //Удаление головы
        if (p.node == head) {
            head = head.Next;
            head.Prev = null;
            p.node = head;
            return;
        }

        //Удаление хвоста
        if (p.node == tail) {
            tail = tail.Prev;
            tail.Next = null;
            p.node = null;
            return;
        }

        //Остальные случаи
        if (!exist(p)) return;
        Node temp = p.node;
        Node prev = temp.Prev;
        prev.Next = temp.Next;
        temp.Next.Prev = prev;
        p.node = prev.Next;
    }

    @Override
    public Position next(Position p)  {
        if (p == null || p.node == null) throw new WrongPositionException("no such pos in the list");

        if (p.node == head || exist(p))
            return new Position(p.node.Next);
        else
            throw new WrongPositionException("no such pos in the list");
    }

    @Override
    public Position previous(Position p)  {
        if (p == null || p.node == null || p.node == head) throw new WrongPositionException("no such pos in the list");;
        if (p.node == tail || exist(p))
            return new Position(p.node.Prev);
        else
            throw new WrongPositionException("no such pos in the list");
    }

    @Override
    public Position first() {
        return new Position(head);
    }

    @Override
    public void makeNull() {
        head = null;
        tail = null;
    }

    @Override
    public void printList() {
        if (head == null || head.Data == null) {
            System.out.println("The List is empty");
            return;
        }
        Node p = head;
        int i = 1;
        while (p != null) {
            System.out.print((i) + ") ");
            p.Data.printData();
            p = p.Next;
            i++;
        }
    }

    @Override
    public Position end() {
        return new Position(null);
    }

    private Boolean exist(Position p) {
        Node q = head;

        while (q != null) {
            if (p.node == q) {
                return true;
            }
            q = q.Next;
        }
        return false;
    }

    private Node search(AddressData x) {
        Node q = head;
        while (q != null) {
            if (q.Data.equals(x))
                return q;
            q = q.Next;
        }
        return null;
    }
}
