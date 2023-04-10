package SetATD.LinkedList;

import SetATD.Exceptions.SetException;

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

    public Set(Set set) {
        copyFrom(set);
    }

    public Set union(Set set) {
        // Если список этот же возвращает копию списка
        if (set == this) return new Set(set);
        // Если оба списка пустых возвращаем пустой список
        if (set._head == null && _head == null) {
            return new Set();
        }
        // Если кто из списка пустой, возвращаем копию непустого списка
        if (set._head == null) {
            return new Set(set);
        }
        if (_head == null) {
            return new Set(this);
        }
        // Создаем новое множество их исходного множества
        // Пробегаемся по второму множеству пока не будут вставлены все элементы
        Set unionSet = new Set();
        Node firstCurrent = _head;
        Node secondCurrent = set._head;
        // Инициализируем голову списка, которая является младшой голов из двух голов
        unionSet._head = new Node(Math.min(firstCurrent.value, secondCurrent.value), null);
        Node unionCurrent = unionSet._head;
        firstCurrent = firstCurrent.next;
        secondCurrent = secondCurrent.next;
        // Идем по спискам пока кто либо из них не станет пустым
        // Если значения равны, тогда мы берем любое, и продвигаем по дальше
        // Мы ищем наименьший элемент из двух список и копируем его следущим элементом
        // Когда мы берем элемент из какого то списка мы меняем его на следущий
        while (firstCurrent != null && secondCurrent != null) {
            if (firstCurrent.value == secondCurrent.value) {
                unionCurrent.next = new Node(firstCurrent.value, null);
                firstCurrent = firstCurrent.next;
                secondCurrent = secondCurrent.next;
            } else if (firstCurrent.value < secondCurrent.value) {
                unionCurrent.next = new Node(firstCurrent.value, null);
                firstCurrent = firstCurrent.next;
            } else {
                unionCurrent.next = new Node(secondCurrent.value, null);
                secondCurrent = secondCurrent.next;
            }
            unionCurrent = unionCurrent.next;
        }
        // Если кто то из списков остался не пустым мы должны скопировать отуда все его элементо последовательно в конец
        while (firstCurrent != null) {
            unionCurrent.next = new Node(firstCurrent.value, null);
            unionCurrent = unionCurrent.next;
            firstCurrent = firstCurrent.next;
        }
        while (secondCurrent != null) {
            unionCurrent.next = new Node(secondCurrent.value, null);
            unionCurrent = unionCurrent.next;
            secondCurrent = secondCurrent.next;
        }
        return unionSet;
    }

    public Set intersection(Set set) {
        // Если список этот же возвращает копию списка
        if (set == this) return new Set(set);
        // Если хотя бы один из списков пустой возвращаем пустой список
        if (set._head == null || _head == null) {
            return new Set();
        }
        Set interSet = new Set();
        Node interCurrent = null;
        Node firstCurrent = _head;
        Node secondCurrent = set._head;
        // Ищем первый элемент пересечения, если элемент не найден, значит пересечений нет
        while (firstCurrent != null && secondCurrent != null) {
            if (firstCurrent.value == secondCurrent.value) {
                if(interCurrent == null) {
                    interSet._head = new Node(firstCurrent.value, null);
                    interCurrent = interSet._head;
                }
                else {
                    interCurrent.next = new Node(firstCurrent.value, null);
                    interCurrent = interCurrent.next;
                }
                firstCurrent = firstCurrent.next;
                secondCurrent = secondCurrent.next;
            } else if (firstCurrent.value < secondCurrent.value) {
                firstCurrent = firstCurrent.next;
            } else {
                secondCurrent = secondCurrent.next;
            }
        }
        return interSet;
    }

    public Set difference(Set set) {
        // Если список этот же возвращаем пустой список
        if (set == this || set._head == null) return new Set();
        if(_head == null) return new Set(set);
        // Создаем копие входящиего множества
        Set differSet = new Set(set);
        Node differCurrent = null;
        Node firstCurrent = _head;
        Node secondCurrent = set._head;
        while (firstCurrent != null && secondCurrent != null) {
            if (firstCurrent.value == secondCurrent.value) {
                firstCurrent = firstCurrent.next;
                secondCurrent = secondCurrent.next;
            } else if (firstCurrent.value < secondCurrent.value) {
                firstCurrent = firstCurrent.next;
            } else {
                if(differCurrent == null) {
                    differSet._head = new Node(secondCurrent.value, null);
                    differCurrent = differSet._head;
                }
                else {
                    differCurrent.next = new Node(secondCurrent.value, null);
                    differCurrent = differCurrent.next;
                }
                secondCurrent = secondCurrent.next;
            }
        }
        while (secondCurrent != null) {
            if(differCurrent == null) {
                differSet._head = new Node(secondCurrent.value, null);
                differCurrent = differSet._head;
            }
            else {
                differCurrent.next = new Node(secondCurrent.value, null);
                differCurrent = differCurrent.next;
            }
            differCurrent = differCurrent.next;
            secondCurrent = secondCurrent.next;
        }
        return differSet;
    }

    public Set merge(Set set) {
        // Если список этот же возвращает копию списка
        if (set == this) return new Set(set);
        // Если оба списка пустых возвращаем пустой список
        if (set._head == null && _head == null) {
            return new Set();
        }
        // Если кто из списка пустой, возвращаем копию непустого списка
        if (set._head == null) {
            return new Set(set);
        }
        if (_head == null) {
            return new Set(this);
        }
        // Создаем новое множество их исходного множества
        // Пробегаемся по второму множеству пока не будут вставлены все элементы
        Set unionSet = new Set();
        Node firstCurrent = _head;
        Node secondCurrent = set._head;
        // Инициализируем голову списка, которая является младшой голов из двух голов
        unionSet._head = new Node(Math.min(firstCurrent.value, secondCurrent.value), null);
        Node unionCurrent = unionSet._head;
        firstCurrent = firstCurrent.next;
        secondCurrent = secondCurrent.next;
        // Идем по спискам пока кто либо из них не станет пустым
        // Мы ищем наименьший элемент из двух список и копируем его следущим элементом
        // Когда мы берем элемент из какого то списка мы меняем его на следущий
        while (firstCurrent != null && secondCurrent != null) {
            if (firstCurrent.value < secondCurrent.value) {
                unionCurrent.next = new Node(firstCurrent.value, null);
                firstCurrent = firstCurrent.next;
            } else {
                unionCurrent.next = new Node(secondCurrent.value, null);
                secondCurrent = secondCurrent.next;
            }
            unionCurrent = unionCurrent.next;
        }
        // Если кто то из списков остался не пустым мы должны скопировать отуда все его элементо последовательно в конец
        while (firstCurrent != null) {
            unionCurrent.next = new Node(firstCurrent.value, null);
            unionCurrent = unionCurrent.next;
            firstCurrent = firstCurrent.next;
        }
        while (secondCurrent != null) {
            unionCurrent.next = new Node(secondCurrent.value, null);
            unionCurrent = unionCurrent.next;
            secondCurrent = secondCurrent.next;
        }
        return unionSet;
    }


    // Присваивает новое множество
    // Необходимо скопировать ячейки то есть создать новые ноды
    public void assign(Set set) {
        copyFrom(set);
    }

    private void copyFrom(Set set) {
        // Если множество пустое, то обнуляет список
        if (set._head == null) {
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
        if (_head == null) throw new SetException("Список пустой");
        return _head.value;
    }

    // Максимальный элемент это последний элемент списка
    public int max() {
        // Если список пустой выбросить исключение
        if (_head == null) throw new SetException("Список пустой");
        Node current = _head;
        while (current.next != null) {
            current = current.next;
        }
        return current.value;
    }


    // Добавляет значение в множество, если его там нет, если нет ничего не делать
    public void insert(int value) {
        Node previousNode = findClosest(value);
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
        if(_head == null) return;
        // Если элемент голова, тогда просто заменяем
        if (_head.value == value) {
            _head = _head.next;
            return;
        }
        Node previousNode = findClosest(value);
        if(previousNode.next != null && previousNode.next.value == value) {
            previousNode.next = previousNode.next.next;
        }
    }

    // Обнуление
    public void makeNull() {
        _head = null;
    }

    // Находит узел который находит предыдущий элемент к этому значению
    // Если следущий элемент найденного равен числу значит число найдено
    // Может вернуть null, если нет головы(список пустой) или число
    private Node findClosest(int value) {
        // Пробегаемся по связному списку
        // Если текущее значение больше или равно числу возвращаем предыдущее к нему
        // Если список закончился возвращаем предыдущий
        Node previous = null;
        Node current = _head;
        while (current != null) {
            if(current.value >= value) {
                return previous;
            }
            previous = current;
            current = current.next;
        }
        return previous;
    }

    private boolean isMember(int value) {
        Node previousNode = findClosest(value);
        if(previousNode.next != null && previousNode.next.value == value) {
            return false;
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
