package MapATD.ListATD;

import Data.AddressData;
import Data.Extensions;
import ListATD.Array.List;
import ListATD.Array.Position;
import MapATD.IMap;

public class Map implements IMap {

    private final List _list;

    public Map() {
        _list = new List();}

    @Override
    public void makeNull() {
        _list.makeNull();
    }

    @Override
    public void assign(char[] d, char[] r) {
        // Если список пустой, добавляем первым элементом
        if (_list.first().equals(_list.end())) {
            _list.insert(new AddressData(d, r), _list.first());
            return;
        }

        Position position = findByKey(d);
        if(!position.equals(_list.end())) {
            _list.retrieve(position).setAddress(r);
            return;
        }

        // Если ключ не найден, вставляем на первую позицию
        _list.insert(new AddressData(d,r), _list.first()) ;
    }

    @Override
    public boolean compute(char[] d, char[] r) {
        Position position = findByKey(d);
        if(position.equals(_list.end()))
            return false;
        _list.retrieve(position).setAddress(r);
        return true;
    }
    public void print(){
        _list.printList();
    }

    private Position findByKey(char[] key) {

        Position position = _list.first();

        while (!position.equals(_list.end())) {
            AddressData temp = _list.retrieve(position);
            if (Extensions.compareCharArrays(key, temp.getName()))
                return position;
            position = _list.next(position);
        }
        return position;

    }

}
