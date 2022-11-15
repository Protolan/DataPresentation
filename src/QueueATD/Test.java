package QueueATD;

import Data.AddressData;
import QueueATD.Array.Queue;

public class Test {
    public static void main(String[] args) {
        Queue queue = new Queue();
        queue.enqueue(new AddressData("Alan", "Ufa"));
        queue.front().printData();
        queue.enqueue(new AddressData("Mark", "Peter"));
        queue.front().printData();
        queue.enqueue(new AddressData("Volodya", "Moscow"));
        queue.dequeue().printData();
        queue.enqueue(new AddressData("Kirill", "Kazan"));
        queue.dequeue().printData();
        queue.enqueue(new AddressData("Pharan", "Paran"));
        queue.front().printData();
    }
}
