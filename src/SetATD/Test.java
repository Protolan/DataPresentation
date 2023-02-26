package SetATD;


import SetATD.Array.SetPlan;

public class Test {
    public static void main(String[] args) {
        SetPlan set1 = new SetPlan(-100, -30);
        set1.insert(-35);
        set1.insert(-38);
        set1.insert(-20);
        set1.Print();
        SetPlan set2 = new SetPlan(-35, 0);
        set2.insert(-20);
        set2.insert(-22);
        set2.insert(-1);
        System.out.println(set2.member(-3));
        System.out.println(set2.member(-1));
        System.out.println(set2.member(-22));
        set2.Print();
        System.out.println(set2.min());
        System.out.println(set1.min());
        System.out.println();
        var unSet = set1.difference(set2);
        unSet.Print();
    }
}
