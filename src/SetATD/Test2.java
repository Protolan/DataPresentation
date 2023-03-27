package SetATD;


import SetATD.RingedLinkedList.Set;

public class Test2 {
    public static void main(String[] args) {
        var set1 = new Set();
        set1.insert(-20);
        set1.insert(-22);
        set1.insert(-15);
        set1.print();
        var set2 = new Set();
        set2.insert(-20);
        set2.insert(-22);
        set2.insert(-15);
        set2.print();
        System.out.println(set1.max());
        System.out.println(set2.max());
        System.out.println(set1.member(-15));
        System.out.println(set1.equal(set2));
//        var unionSet = set1.union(set2);
//        unionSet.print();
//        var interSet = set1.intersection(set2);
//        interSet.print();
//        var differSet = set1.difference(set2);
//        differSet.print();
    }
}
