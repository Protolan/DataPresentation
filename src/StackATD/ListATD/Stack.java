package StackATD.ListATD;

import Data.AddressData;
import ListATD.Array.List;
import StackATD.Interface.IStack;

public class Stack implements IStack {

    private List _list;

    public Stack() { _list = new List();}

    @Override
    public void push(AddressData d) {
        _list = new List();
    }

    @Override
    public AddressData pop()
    {
        AddressData data = _list.retrieve(_list.first());
        _list.delete(_list.first());
        return data;
    }

    @Override
    public AddressData top() {
        return _list.retrieve(_list.first());
    }

    @Override
    public boolean full() {
        return false;
    }

    @Override
    public boolean empty() {return _list.first().equals(_list.end()); }

    @Override
    public void makeNull() {
        _list.makeNull();
    }
}
