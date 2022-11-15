package QueueATD.RingLinkedList;

import Data.AddressData;
import QueueATD.IQueue;

public class Queue implements IQueue {
    private class Node {
        public AddressData data;
        public Node next;

        public Node(AddressData d, Node nxt) {
            data = d;
            next = nxt;
        }
    }

    private Node _tail;

    public Queue() {
        _tail = null;
    }

    @Override
    public void enqueue(AddressData data) {
        // Вставка в пустой список
        if (_tail == null) {
            _tail = new Node(data, null);
            _tail.next = _tail;
            return;
        }
        // Остальные случаи
        _tail.next = new Node(data, _tail.next);
        _tail = _tail.next;
    }

    @Override
    public AddressData dequeue() {
        AddressData data = _tail.next.data;

        //Когда в очереди единственный элемент
        if (_tail.next == _tail) {
            _tail = null;
            return data;
        }

        //Остальные случаи
        _tail.next = _tail.next.next;
        return data;
    }

    @Override
    public AddressData front() {
        return _tail.next.data;
    }

    @Override
    public boolean full() {
        return false;
    }

    @Override
    public boolean empty() {
        return _tail == null;
    }

    @Override
    public void makeNull() {
        _tail = null;
    }
}
