package SetATD;


import SetATD.LinkedList.Set;

public class Test2 {
    public static void main(String[] args) {
        var set1 = new Set();
        set1.insert(20);
        set1.insert(-22);
        set1.insert(-15);
        set1.print();
        var set2 = new Set();
        set2.insert(-20);
        set2.insert(-42);
        set2.insert(-1);
        set2.print();
        System.out.println(set1.max());
        System.out.println(set2.min());
    }
}
