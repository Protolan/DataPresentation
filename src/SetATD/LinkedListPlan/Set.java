package SetATD.LinkedListPlan;

import SetATD.Exceptions.SetException;

// Множество на связном списке
public class Set {
    private class Node {
        public int value;
        public Node next;

        public Node(int value, Node n) {
            this.value = value;
            next = n;
        }
    }

    private Node _head;


    public Set() {
        _head = null;
    }

    // Копирующий конструктор, задаем начало копирования и откуда копируем
    public Set(Node start, Node from) {
        copyFrom(start, from);
    }

    public Set union(Set set) {
        //----ЧАСТНЫЕ СЛУЧАИ----//
        // Если список этот же возвращает копию списка
        // Если оба списка пустых возвращаем пустой список
        // Если кто из списка пустой, возвращаем копию непустого списка
        //----ОСНОВНОЙ АЛГОРИТМ----//
        // Создаем копию из первого множества
        // Инициализируем голову нового списка, присваеваем значение наименьшой головы из двух списков
        // Идем элементам второго множества и вызываем findClosest первого, чтобы найти элемент для копирования
        // Таким образом выставляем все значения
        // Если значения равны, тогда мы берем любое, и продвигаемся дальше по двум спискам
        // Мы ищем наименьший элемент из двух список и копируем его следущим элементом
        // Если кто-то из списков остался не пустым мы должны скопировать откуда все его элементы с помощью копирующего метода
        return null;
    }

    public Set intersection(Set set) {
        //----ЧАСТНЫЕ СЛУЧАИ----//
        // Если список этот же возвращает копию списка с помошъю копирующего конструктора
        // Если хотя бы один из списков пустой возвращаем пустой список
        //----ОСНОВНОЙ АЛГОРИТМ----//
        // Создаем копию из первого множества с помощью копирущего констрктора
        // С помощью метода findVa
        // Также в процессе поиска мы должны инициализовать голову, если новый список еще не инициализрован
        // Если не равны мы продвигаем только наименьший из двух списков
        return null;
    }

    public Set difference(Set set) {
        //----ЧАСТНЫЕ СЛУЧАИ----//
        //----ОСНОВНОЙ АЛГОРИТМ----//  // Если список этот же список или входящий список пустой возвращаем пустой список
        //        // Если исходный список пустой возвращает копию вх
        // Создаем копию из первого множества с помощью копирущего констрктора
        // Если значения равны,  продвигаемся дальше по двум спискам
        // Если не равны мы продвигаем только наименьший из двух списков
        // Если значение из входящего списка становиться меньше чем в исходном значит заполняем его в новый список
        // Также в процессе поиска мы должны инициализовать голову, если новый список еще не инициализрован
        // Если входящий список остался не пустым мы должны скопировать откуда все его элементы с помощью копирующего метода
        return null;
    }

    public Set merge(Set set) {
        // Такой же алгоритм как в union, только без проверки на равенство
        return null;
    }


    // Присваивает новое множество
    // Необходимо скопировать ячейки то есть создать новые ноды
    public void assign(Set set) {
        copyFrom(set._head, set._head);
    }

    // Копирует входящее множества в исходное
    private void copyFrom(Node start, Node from) {

    }

    public boolean equal(Set set) {
        // Пробегается параллельно по двум спискам пока кто-либо и них не станет пустым
        // Каждую итерация проверяем на равенство, если хотя бы один не равен, то возвращаем false
        // Оба списка должны были закончиться, проверяем что они оба равны null
        Node set1Current = _head;
        Node set2Current = set._head;
        while (set1Current != null && set2Current != null) {
            if(set1Current.value != set2Current.value) {
                return false;
            }
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


    private boolean isMember(int value) {
        Node previousNode = findValueLocation(_head,value);
        return previousNode.next != null && previousNode.next.value == value;
    }


    // Минимальный элемент это голова
    public int min() {
        // Если список пустой выбросить исключение
        if (_head == null) throw new SetException("Список пустой");
        return _head.value;
    }

    // Максимальный элемент это последний элемент списка
    public int max() {
        // Если список пустой выбросить исключение
        if (_head == null) throw new SetException("Список пустой");
        // До ходим до последного узла и возвращаем его
        Node current = _head;
        while (current.next != null) {
            current = current.next;
        }
        return current.value;
    }


    // Добавляет значение в множество, если его там нет, если нет ничего не делать
    public void insert(int value) {
        Node previousNode = findValueLocation(_head, value);
        // Если список пустой или новое значение самое маленькое
        if (previousNode == null) {
            _head = new Node(value, _head);
            return;
        }
        // Если уже есть в множестве ничего не делаем
        if (previousNode.next != null && previousNode.next.value == value) {
            return;
        }
        // Если нет элемент, то вставляем следущим элементом
        previousNode.next = new Node(value, previousNode.next);
    }

    // Удаляет значение из множества, если оно там есть, если нет ничего не делать
    public void delete(int value) {
        if(_head == null || _head.value > value) return;
        // Проверка на первое значение
        if (_head.value == value) {
            _head = _head.next;
            return;
        }

        Node previousNode = findValueLocation(_head, value);
        if(previousNode.next != null && previousNode.next.value == value) {
            previousNode.next = previousNode.next.next;
        }
    }

    public void makeNull() {
        _head = null;
    }

    // Находит узел который находит предыдущий элемент к этому значению
    // Если следущий элемент найденного равен числу значит число найдено
    // Может вернуть null, если нет головы(список пустой) или число
    private Node findValueLocation(Node start, int value) {
        // Пробегаемся по связному списку
        // Если текущее значение больше или равно числу возвращаем предыдущее к нему
        // Получается, если значение не найден, вернётся последний элемент списка(value.next == null)
        Node previous = null;
        Node current = start;
        while (current != null) {
            if(current.value >= value) {
                return previous;
            }
            previous = current;
            current = current.next;
        }
        return previous;
    }



    // Проверяет есть ли общие элементы
    public boolean haveCommonElements(Set set) {
        // Проверяем голову списков
        // Пробегаемся по первому связному списку, и используем findValueLocation с каждым элементом второго списка
        // Пока next найденного элемента не будет равен искому числу, это будет означать, что списки имеет один общий элемент
        // Каждую новую итерацию мы будем начинать с того элемента, который вернут findValueLocation
        // Если найденный элемент равен
        // Если кто-либо из списков закончился возвращаем false
        if(set == this) return true;
        if (set._head == null || _head == null) {
            return false;
        }
        Node set1Current = _head;
        Node set2Current = set._head;
        while(set1Current.next != null && set2Current != null) {
            if(set1Current.value < set2Current.value) {
                set1Current = findValueLocation(set1Current, set2Current.value).next;
            }
            else if(set1Current.value > set2Current.value)
            {
                set2Current = findValueLocation(set2Current, set1Current.value).next;
            }
            else {
                return true;
            }
        }
        return false;
    }


    // Вывод
    public void print() {
        if (_head == null) {
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
