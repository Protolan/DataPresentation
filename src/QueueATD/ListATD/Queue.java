package QueueATD.ListATD;

import Data.AddressData;
import ListATD.LinkedList.List;
import ListATD.LinkedList.Position;
import QueueATD.IQueue;

public class Queue implements IQueue {

    private List _list;

    public Queue() {
        _list = new List();
    }
    @Override
    public void enqueue(AddressData data) {
        _list.insert(data, _list.first());
    }

    @Override
    public AddressData dequeue() {
        AddressData data =  _list.retrieve(_list.first());
        _list.delete(_list.first());
        return data;
    }

    @Override
    public AddressData front() { return _list.retrieve(_list.first()); }

    @Override
    public boolean full() {
        return false;
    }

    @Override
    public boolean empty() {
        return _list.first().equals(_list.end());
    }

    @Override
    public void makeNull() {
        _list.makeNull();
    }
}
