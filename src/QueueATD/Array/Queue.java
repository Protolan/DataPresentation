package QueueATD.Array;

import Data.AddressData;
import QueueATD.IQueue;

public class Queue implements IQueue {

    private final AddressData[] _array;
    private final int _length = 10;
    private int _front;
    private int _rear;

    public Queue() {
        _array = new AddressData[_length];
        _rear = _length - 1;
        _front = 0;
    }
    @Override
    public void enqueue(AddressData data) {
        _rear = next(_rear);
        _array[_rear] = data;
    }

    @Override
    public AddressData dequeue() {
        AddressData data = _array[_front];
        _front = next(_front);
        return data;
    }

    @Override
    public AddressData front() {
        return _array[_front];
    }

    @Override
    public boolean full() {
        return next(next(_rear)) == _front;
    }

    @Override
    public boolean empty() {
        return next(_rear) == _front;
    }


    @Override
    public void makeNull() {
        _rear = _length - 1;
        _front = 0;
    }

    private int next(int from) {return (from + 1) % _length;}
}
