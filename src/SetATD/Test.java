package SetATD;

import SetATD.Array.Set2;

public class Test {
    public static void main(String[] args) {
        var set1 = new Set2(20, 38);
        set1.insert(35);
        set1.insert(38);
        set1.insert(20);
        set1.printBinary();
        set1.printSet();
        var set2 = new Set2(-32, -1);
        set2.insert(-20);
        set2.insert(-22);
        set2.insert(-1);
        set2.insert(-32);
        set2.printBinary();
        set2.printSet();
//        System.out.println(set2.member(-3));
//        set2.print();
//        System.out.println(set2.min());
//        System.out.println(set1.max());
//        System.out.println();
//        var difference = set1.difference(set2);
        var union = set1.union(set2);
//        var intersection = set1.intersection(set2);
//        difference.print();
        union.printSet();
//        intersection.print();
//        set1.assign(set2);
//        set1.print();
    }
}
