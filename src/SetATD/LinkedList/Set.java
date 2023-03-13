package SetATD.LinkedList;
import SetATD.Exceptions.SetException;

public class Set {
    private class Node{
        public int value;
        public Node next;
        public Node(int value, Node n){
            this.value = value;
            next = n;
        }
    }

    private Node _head;



    public Set() { _head = null; }

    public Set union(Set set) {
        if(set == this) return set;
        return null;
    }

    public Set intersection(Set set) {
        if(set == this) return set;
        return null;
    }

    public Set difference(Set set) {
        if(set == this) return new Set();
        return null;
    }

    public Set merge(Set set) {
        return null;
    }


    // Присваивает новое множество
    // Необходимо скопировать ячейки то есть создать новые ноды
    public void assign(Set set) {
        // Если множество пустое, то обнуляет список
        if(set._head == null) {
           _head = null;
           return;
        }
        Node from = set._head;
        _head = new Node(set._head.value, null);
        Node to = _head;
        while (from.next != null) {
            to.next = new Node(from.next.value, null);
            from = from.next;
            to = to.next;
        }
    }

    public boolean equal(Set set) {
        Node set1Current = _head;
        Node set2Current = set._head;
        while (set1Current != null && set2Current != null && set1Current.value == set2Current.value) {
            set1Current = set1Current.next;
            set2Current = set2Current.next;
        }
        return set1Current == set2Current;
    }

    public Set find(Set set, int value) {
        if (isMember(value)) return this;
        else if (set.isMember(value)) return set;
        else return null;
    }

    public boolean member(int value) {
        return isMember(value);
    }

    // Минимальный элемент это голова
    public int min() {
        // Если список пустой выбросить исключение
        if(_head == null) throw new SetException("Список пустой");
        return _head.value;
    }

    // Максимальный элемент это последний элемент списка
    public int max() {
        // Если список пустой выбросить исключение
        if(_head == null) throw new SetException("Список пустой");
        Node current = _head;
        while (current.next != null) {
            current = current.next;
        }
        return current.value;
    }


    // Добавляет значение в множество, если его там нет, если нет ничего не делать
    public void insert(int value) {
        Node closestNode = findClosest(value);
        // Если список пустой или новое значение самое маленькое
        if(closestNode == null) {
            _head = new Node(value, _head);
            return;
        }
        // Если уже есть в множестве возвращаем
        if(closestNode.value == value) {
            return;
        }
        closestNode.next = new Node(value, closestNode.next);
    }

    // Удаляет значение из множества, если оно там есть, если нет ничего не делать
    public void delete(int value) {
        Node closestNode = findClosest(value);
        // Если элемента нет в множестве
        if(closestNode == null || closestNode.value != value) {
            return;
        }
        // Если элемент голова
        if(closestNode == _head) {
            _head = _head.next;
            return;
        }

        // Ищем предыдущий элемент
        Node previous = _head;
        while (previous.next != closestNode) {
            previous = previous.next;
        }
        previous.next = closestNode.next;
    }

    public void makeNull() {
        _head = null;
    }

    // Находит узел который находит это же значение или ближайшее меньшее его значение
    private Node findClosest(int value) {
        Node previous = null;
        Node current = _head;
        while (current != null && current.value <= value) {
            previous =  current;
            current = current.next;
        }
        return previous;
    }

    private boolean isMember(int value) {
        Node closest = findClosest(value);
        if(closest == null || closest.value != value) return false;
        return true;
    }



    // Вывод
    public void print() {
        if(_head == null) {
            System.out.println("Список пустой");
            return;
        }
        Node current = _head;
        while (current != null) {
            System.out.print(current.value + " ");
            current = current.next;
        }
        System.out.println();
    }
}
