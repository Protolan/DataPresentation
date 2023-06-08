package SetATD;

import SetATD.RingedLinkedList.Set;

public class Test {
    public static void main(String[] args) {
        var set1 = new Set(-100, 100);
        set1.insert(-20);
        set1.insert(-22);
        set1.insert(-1);
        set1.insert(-15);
        set1.print();
        var set2 = new Set(-32, 100);
        set2.insert(-20);
        set2.insert(-22);
        set2.insert(-1);
        set2.insert(-15);
        set2.print();
        System.out.println(set1.equal(set2));
        set1.delete(-1);
        set2.delete(-15);
        set2.insert(35);
        set1.print();
        set2.print();
        System.out.println(set2.equal(set1));
        System.out.println("Min: " + set1.min());
        System.out.println("Max: " + set2.max());
        System.out.println(set1.member(35));
        System.out.println(set2.member(35));
        set1.find(set2, 35).print();
        set1.print();
        set2.print();
        var union = set2.union(set1);
        var difference = set1.difference(set2);
        var intersection = set1.intersection(set2);
        System.out.println("Union");
        union.print();
        System.out.println("Difference");
        difference.print();
        System.out.println("Intersection");
        intersection.print();
    }


}
