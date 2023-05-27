package SetATD;


import SetATD.LinkedListPlan.Set;

public class Test2 {
    public static void main(String[] args) {
        Set set1 = new Set();
        set1.insert(-20);
        set1.insert(22);
        set1.insert(-15);
        set1.print();
        Set set2 = new Set();
        set2.insert(-20);
        set2.insert(-22);
        set2.insert(-15);
        set2.print();
        set1.haveCommonElements(set2);
        System.out.println(set1.equal(set2));
        Set unionSet = set1.union(set2);
        unionSet.print();
        Set intersectionSet = set1.intersection(set2);
        intersectionSet.print();
        Set differenceSet = set1.difference(set2);
        differenceSet.print();

//        var unionSet = set1.union(set2);
//        unionSet.print();
//        var interSet = set1.intersection(set2);
//        interSet.print();
//        var differSet = set1.difference(set2);
//        differSet.print();
    }
}
