package SetATD;

import SetATD.Array.Set2;

public class Test {
    public static void main(String[] args) {
        var set1 = new Set2(-50, -15);
        set1.insert(-20);
        set1.insert(-22);
        set1.insert(-15);
        set1.printSet();
        var set2 = new Set2(-32, -1);
        set2.insert(-20);
        set2.insert(-22);
        set2.insert(-1);
//        set2.insert(-32);
        set2.printSet();
//        System.out.println(set2.member(-32));
//        set2.delete(-32);
//        System.out.println(set2.member(-32));
        System.out.println("Min " + set2.min());
        System.out.println("Max " + set1.min());
        var union = set1.union(set2);
        var intersection = set1.intersection(set2);
        var difference = set1.difference(set2);
        union.printSet();
        intersection.printSet();
        difference.printSet();
//        set1.assign(set2);
//        set1.printSet();
    }
}
