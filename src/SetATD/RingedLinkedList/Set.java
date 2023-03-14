package SetATD.RingedLinkedList;

import SetATD.Exceptions.SetException;

// Множество на кольцевом списке
public class Set {
    //Класс узла, значению и следущий элемент
    private class Node {
        public int value;
        public Node next;

        public Node(int value, Node n) {
            this.value = value;
            next = n;
        }
    }

    private Node _tail;

    // Конструктор по умолчанию, инициализирует _tail
    public Set() {
        _tail = null;
    }

    // Копирующий конструктор
    private Set(Set set) {
        copyFrom(set);
    }


    // Присваивает новое множество в исходное множество
    public void assign(Set set) {
        // Вызываем метод copyFrom
        copyFrom(set);
    }

    // Проверяет равны ли множества
    public boolean equal(Set set) {
        // Если это же множество или оба множества пустые значит они одинаковые
        if (set._tail == _tail) {
            return true;
        }
        // Иначе проверям по узлам на равенство
        Node set1Current = _tail;
        Node set2Current = set._tail;
        while (set1Current.next != _tail && set2Current.next != set._tail && set1Current.value == set2Current.value) {
            set1Current = set1Current.next;
            set2Current = set2Current.next;
        }
        return set1Current == set2Current;
    }

    // Возвращает то множество в котором есть это число
    public Set find(Set set, int value) {
        // Использует метод isMember, чтобы найти множестве в котором есть это значение
        if (isMember(value)) return this;
        else if (set.isMember(value)) return set;
        else return null;
    }

    // Определяет есть ли число в множестве
    public boolean member(int value) {
        // Возвращает результат метода isMember
        return isMember(value);
    }

    // Минимальный элемент это голова
    public int min() {
        // Если список пустой выбросить исключение
        if (_tail == null) throw new SetException("Список пустой");
        return _tail.next.value;
    }

    // Максимальный элемент это последний элемент списка
    public int max() {
        // Если список пустой выбросить исключение
        if (_tail == null) throw new SetException("Список пустой");
        return _tail.value;
    }


    // Добавляет значение в множество, если его там нет, если нет ничего не делать
    public void insert(int value) {
        // Если список пустой, инициализируем новый tail, который ссылается сам на себя
        if (_tail == null) {
            _tail = new Node(value, null);
            _tail.next = _tail;
            return;
        }
        // Если единственный элемент
        if (_tail == _tail.next) {
            // Если равен, ничего не делать
            if (_tail.value == value) return;
            // Если меньше тогда нужно вставить в начало
            if (_tail.value < value)
                _tail.next = new Node(value, _tail);
                // Если больше тогда нужно вставить в конец
            else
                _tail = new Node(value, _tail);
            return;
        }
        // Ищем узел с нужным значением помощью findClosest
        Node closestNode = findClosest(value);
        // Если уже есть в множестве, ничего не делаем
        if (closestNode == _tail) {

        }
        // Иначе вставляем новый элемент после ближайшего меньшего значения
        System.out.println("Вставляем " + value + " между " + closestNode.value + " и " + closestNode.next.value);
        closestNode.next = new Node(value, closestNode.next);
    }

    // Удаляет значение из множества, если оно там есть, если нет ничего не делать
    public void delete(int value) {
        // Если список пустой, ничего не делаем
        if (_tail == null) {
            return;
        }
        // Ищем узел с нужным значением помощью findClosest
        Node closestNode = findClosest(value);
        // Если не найден узел, ничего не делаем
        if (closestNode.value != value) {
            return;
        }
        // Иначе ищем предыдущий элемент и удаляем нужный узел
        Node previous = _tail;
        while (previous.next != closestNode) {
            previous = previous.next;
        }

        previous.next = closestNode.next;
    }


    // Обнуляет список
    public void makeNull() {
        // Обнуляем хвст
        _tail = null;
    }

    // Находит узел который находит это же значение или ближайшее меньшее его значение
    // Может вызватся если tail не равен null
    // Возвращает null значит нужно вставлять в tail.next
    private Node findClosest(int value) {
        Node previous = _tail;
        Node current = _tail.next;
        while (current != _tail && current.value <= value) {
            previous = current;
            current = current.next;
        }
        return previous;
    }

    // Определяет есть ли число в множестве
    private boolean isMember(int value) {
        // Вызываем метод findClosest для поиска узла с этим значением
        Node closest = findClosest(value);
        // Если список пустой или не найдее элемент с таким значением
        if (_tail == null || closest.value != value) return false;
        // Если все хорошо, значит принадлежит
        return true;
    }

    // Копирует множество
    private void copyFrom(Set set) {
        // Если множество пустое, то обнуляем список
        if (set._tail == null) {
            _tail = null;
            return;
        }
        // Инчае копируем значения
        Node from = set._tail;
        _tail = new Node(set._tail.value, null);
        _tail.next = _tail;
        Node to = _tail;
        while (from.next != _tail) {
            to.next = new Node(from.next.value, _tail);
            from = from.next;
            to = to.next;
        }
    }


    // Вывод
    public void print() {
        if (_tail == null) {
            System.out.println("Список пустой");
            return;
        }
        Node current = _tail.next;
        do {
            System.out.print(current.value + " ");
            current = current.next;
        }
        while (current != _tail.next);
        System.out.println();
    }
}
