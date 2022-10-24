package QueueATD;

import Data.AddressData;

public interface IQueue {
    void enqueue(AddressData data);
    AddressData dequeue();
    AddressData front();
    boolean full();
    boolean empty();
    void makeNull();

}
