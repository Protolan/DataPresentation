package ArrayPresentation;

import List.IList;
import Data.AddressData;
import Exceptions.WrongPositionException;

public class List implements IList<Position> {

    // Первый свободный
    private int _end;
    private final AddressData[] _array;

    public List() {
        _array = new AddressData[50];
        _end = 0;
    }

    @Override
    public void insert(AddressData x, Position p) {
        if (IsFull() || OutOfRange(p))
            return;

        for (int i = _end; i > p.value; i--) {
            _array[i] = _array[i - 1];
        }

        _array[p.value] = x;
        _end += 1;
    }

    @Override
    public Position locate(AddressData x) {
        for (int i = 0; i < _end; i++) {
            if (_array[i].equals(x))
                return (new Position(i));
        }
        return new Position(_end);
    }

    @Override
    public AddressData retrieve(Position p) {
        if(OutOfRange(p) || ElementNotExist(p)) {
            throw new WrongPositionException("Position: " + p +
                    " out of possible positions: " + _end);
        }
        return _array[p.value];
    }

    @Override
    public void delete(Position p) {
        if (OutOfRange(p) || ElementNotExist(p))
            return;

        for (int i = p.value; i < _end; i++)
            _array[i] = _array[i + 1];
        _end -= 1;
    }

    @Override
    public Position next(Position p) {
        if (OutOfRange(p) || ElementNotExist(p)) {
            throw new WrongPositionException("Position: " + p +
                    " out of possible positions: " + _end);
        }
        return (new Position(p.value + 1));
    }

    @Override
    public Position previous(Position p) {
        if(OutOfRange(p) || ElementNotExist(p) || IsFirst(p)) {
            throw new WrongPositionException("No position behind " + p);
        }
        return (new Position(p.value - 1));
    }

    @Override
    public Position first() {
        return new Position(0);
    }

    @Override
    public void makeNull() {
        _end = 0;
    }

    @Override
    public Position end() {
        return new Position(_end);
    }

    @Override
    public void printList() {
        for (int i = 0; i < _end; i++) {
            System.out.print((i + 1) + ") ");
            _array[i].printData();
        }
    }

    private Boolean IsFull() {
        return _end >= _array.length;
    }

    private Boolean OutOfRange(Position p) {
        return p.value > _end;
    }

    private Boolean ElementNotExist(Position p) {
        return _array[p.value] == null;
    }

    private Boolean IsFirst(Position p) {
        return p.value == 0;
    }




}
