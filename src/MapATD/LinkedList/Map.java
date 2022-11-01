package MapATD.LinkedList;

import Data.AddressData;
import Data.Extensions;
import MapATD.IMap;

public class Map implements IMap {

    private static class Node{
        public AddressData data;
        public Node next;

        public Node(char[] name, char[] address,  Node node){
            next = node;
            data = new AddressData(name, address);
        }

        public void setValue(char[] a) {
            this.data.setAddress(a);
        }
    }

    private Node _head;
    public Map() {
        _head = null;
    }

    @Override
    public void makeNull() {
        _head = null;
    }

    @Override
    public void assign(char[] d, char[] r) {
        //Если список пустой, создаем голову, нет смысла искать ключ
        if (_head == null) {
            _head = new Node(d, r, null);
            return;
        }

        //Поиск по ключу. Если найден, вставить значение
        Node node = findByKey(d);
        if(node != null) {
            node.setValue(r);
            return;
        }

        //Если ключ не найдет, то вставляем сразу после головы, чтобы было быстро
        if (_head.next == null){
            _head.next = new Node(d, r, null);
            return;
        }

        _head.next = new Node(d, r, _head.next);
    }



    @Override
    public boolean compute(char[] d, char[] r) {
      Node node = findByKey(d);
      if(node == null)
          return false;
      node.setValue(r);
      return true;
    }

    private Node findByKey(char[] d) {
        Node key = _head;
        while (key != null){
            if (Extensions.compareCharArrays(key.data.getName(), d)){
                return key;
            }
            key = key.next;
        }
        return null;
    }
}
