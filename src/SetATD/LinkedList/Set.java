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
