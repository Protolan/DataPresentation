package QueueATD.ListATD;

import Data.AddressData;
import ListATD.Array.List;
import ListATD.Array.Position;
import QueueATD.IQueue;

public class Queue implements IQueue {

    private List _list;
    private Position _tail;

    public Queue() {
        _list = new List();
        _tail = null;
    }
    @Override
    public void enqueue(AddressData data) {
        if (_tail == null){
            _list.insert(data, _list.first());
            _tail = _list.first();
            return;
        }
        if (_tail.equals(_list.first())){
            _list.insert(data, _list.first());
            _tail = _list.next(_tail);
            return;
        }
        _list.insert(data, _list.first());
    }

    @Override
    public AddressData dequeue() {
        Position p = _tail;
        if (_tail == _list.first()){
            makeNull();
            return _list.retrieve(p);
        }
        _tail = _list.previous(_tail);
        return _list.retrieve(p);
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
