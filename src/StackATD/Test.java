package StackATD;

import Data.AddressData;
import StackATD.LinkedList.Stack;

public class Test {
    public static void main(String[] args) {
        Stack stack = new Stack();
        stack.push(new AddressData("Alan", "Ufa"));
        stack.top().printData();
        stack.push(new AddressData("Mark", "Peter"));
        stack.top().printData();
        stack.push(new AddressData("Volodya", "Moscow"));
        stack.pop();
        stack.pop();
        stack.push(new AddressData("Kirill", "Kazan"));
        stack.pop().printData();
        stack.push(new AddressData("Pharan", "Paran"));
        stack.top().printData();
    }
}
