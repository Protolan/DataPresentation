package SetATD;


import SetATD.Array.SetPlan;

public class Test {
    public static void main(String[] args) {
        SetPlan set1 = new SetPlan(-100, -30);
        set1.insert(-35);
        set1.insert(-38);
        set1.insert(-20);
        set1.print();
        SetPlan set2 = new SetPlan(-200, 0);
        set2.insert(-20);
        set2.insert(-22);
        set2.insert(-1);
        System.out.println(set2.member(-3));
        set2.print();
        System.out.println(set2.min());
        System.out.println(set1.max());
        System.out.println();
        var difference = set1.difference(set2);
        var union = set1.union(set2);
        var intersection = set1.intersection(set2);
        difference.print();
        union.print();
        intersection.print();
        set1.assign(set2);
        set1.print();
    }
}
