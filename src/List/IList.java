package List;

import Data.AddressData;
import Exceptions.WrongPositionException;

public interface IList<T> {
    void insert(AddressData x, T p);

    void delete(T p);

    T end();

    T locate(AddressData x);

    AddressData retrieve(T p);


    T next(T p);

    T previous(T p);


    void makeNull();

    T first();

    void printList();
}
