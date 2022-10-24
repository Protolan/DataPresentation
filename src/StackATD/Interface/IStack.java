package StackATD.Interface;


import Data.AddressData;

public interface IStack {
    void push(AddressData d);
    AddressData pop();
    AddressData top();
    boolean full();
    boolean empty();
    void makeNull();
}
