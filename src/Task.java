import ArrayPresentation.Position;
import Data.AddressData;
import CursorPresentation.List;

public class Task {
    public static void main(String[] args) {
        List L = new List();
        CreateList(L);

        Position p,q;
        p = L.first();
        while (!p.equals(L.end())){
            q = L.next(p);
            while (!q.equals(L.end())){
                if (L.retrieve(p).equals(L.retrieve(q)))
                    L.delete(q);
                else
                    q = L.next(q);
            }
            p = L.next(p);
        }
        L.printList();
    }


    public static void CreateList(List L){
        L.insert(new AddressData("Alan", "Ufa"), L.first());
        L.insert(new AddressData("Brad", "Los-Angeles"), L.first());
        L.insert(new AddressData("Ekaterina", "Norilsk"), L.first());
        L.insert(new AddressData("Miro", "Stambul"), L.first());
        L.insert(new AddressData("Ekaterina", "Norilsk"), L.first());
        L.insert(new AddressData("Brad", "Los-Angeles"), L.first());
        L.insert(new AddressData("Alan", "Ufa"), L.first());
        L.insert(new AddressData("Miro", "Stambul"), L.first());
        L.insert(new AddressData("Ekaterina", "Norilsk"), L.first());
        L.insert(new AddressData("Alan", "Ufa"), L.first());
        L.insert(new AddressData("Brad", "Los-Angeles"), L.first());
        L.insert(new AddressData("Alan", "Ufa"), L.first());
        L.insert(new AddressData("Alan", "Ufa"), L.first());
        L.insert(new AddressData("Miro", "Stambul"), L.first());
        L.insert(new AddressData("Alan1", "Ufa"), L.end());
        L.printList();
        System.out.println();
    }
}
