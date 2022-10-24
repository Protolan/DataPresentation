package StackATD.LinkedList;

import Data.AddressData;
import StackATD.Interface.IStack;

public class Stack implements IStack {
    private class Node{
        public AddressData data;
        public Node next;
        public Node(AddressData data, Node n){
            this.data = data;
            next = n;
        }
    }

    private Node _head;

    public Stack() { _head = null; }
    @Override
    public void push(AddressData d) {
        _head = new Node(d, _head);
    }

    @Override
    public AddressData pop() {
        AddressData data = _head.data;
        _head = _head.next;
        return data;
    }

    @Override
    public AddressData top() {
        return _head.data;
    }

    @Override
    public boolean full() {
        return false;
    }

    @Override
    public boolean empty() {
        return _head == null;
    }

    @Override
    public void makeNull() {
        _head = null;
    }
}
