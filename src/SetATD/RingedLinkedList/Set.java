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

    public Set(int x, int y) {
        _tail = null;
    }

    // Копирующий конструктор
    private Set(Set set) {
        if (set._tail == null) {
            _tail = null;
            return;
        }
        _tail = copyFrom(set._tail.next, set._tail);
    }


    // Метод для получения мн-ва пересечения
    public Set union(Set set) {
        // Если этот же список возвращаем копию
        if (set == this) return new Set(set);
        // Если оба списка пустых возвращаем пустой список
        if (set._tail == null && _tail == null) return new Set();
        // Если кто из списка пустой, возвращаем копию непустого списка
        if (set._tail == null) return new Set(this);
        if (_tail == null) return new Set(set);
        // Создаем новое множество их исходного множества
        // Используем метод findValueLocation, чтобы получить значения для проверки и вставки
        // Если значения равны, тогда ничего не добавляем
        // Если не такого элемента то вставляем
        Set unionSet = new Set(this);
        Node unionCurrent = unionSet._tail;
        Node setCurrent = set._tail.next;
        while (setCurrent != set._tail) {
            Node previous = unionSet.findValueLocation(unionCurrent, setCurrent.value);
            if (previous.next.value == setCurrent.value) {
                unionCurrent = previous;
            } else if (previous.next == unionSet._tail) {
                unionCurrent = previous;
                break;
            } else {
                previous.next = new Node(setCurrent.value, previous.next);
                unionCurrent = previous.next;
            }
            setCurrent = setCurrent.next;
        }

        // Если дошли до конца просто копируем оставшиеся элементы
        if(unionCurrent.next == unionSet._tail) {
            while (setCurrent != set._tail) {
                Node temp = unionSet._tail;
                unionSet._tail = new Node(setCurrent.value, unionSet._tail.next);
                temp.next = unionSet._tail;
                setCurrent = setCurrent.next;
            }
        }

        // Отдельно обработаем по такому же алгоритму хвост
        Node previous = unionSet.findValueLocation(unionCurrent, set._tail.value);
        if (previous.next.value < setCurrent.value) {
            Node temp = unionSet._tail;
            unionSet._tail = new Node(set._tail.value, unionSet._tail.next);
            temp.next = unionSet._tail;
        } else if (previous.next.value > setCurrent.value) {
            previous.next = new Node(setCurrent.value,  previous.next);
        }

        return unionSet;
    }

    // Метод для получения мн-ва пересечения
    public Set intersection(Set set) {
        // Если список этот же возвращает копию списка
        if (set == this) return new Set(set);
        // Если хотя бы один из списков пустой возвращаем пустой список
        if (set._tail == null || _tail == null) {
            return new Set();
        }
        if(rangeNotInter(this, set)) return new Set();
        // Создаем пустое мн-во, которое будет заполнять
        Set interSet = new Set();
        Node set1Current = _tail;
        Node set2Current = set._tail.next;
        // Используем метод findValueLocation, чтобы проверить нашлись ли одинаковые значения
        // Если нашлись то вставляем
        while (set2Current != set._tail) {
            Node previous = findValueLocation(set1Current, set2Current.value);
            if (previous.next.value == set2Current.value) {
                // Важно проверить, иницилизровали ли мы хвост?
                if (interSet._tail == null) {
                    interSet._tail = new Node(set2Current.value, null);
                    interSet._tail.next = interSet._tail;
                } else {
                    Node temp = interSet._tail;
                    interSet._tail = new Node(set2Current.value, interSet._tail.next);
                    temp.next = interSet._tail;
                }
                set1Current = previous;
            } else if (previous.next == _tail) {
                return interSet;
            } else {
                set1Current = previous;
            }
            set2Current = set2Current.next;
        }

        // Обработка хвоста по такому же алгоритму
        Node previous = findValueLocation(set1Current, set._tail.value);
        if (previous.next.value == set2Current.value) {

            if (interSet._tail == null) {
                interSet._tail = new Node(set._tail.value, null);
                interSet._tail.next = interSet._tail;
            } else {
                Node temp = interSet._tail;
                interSet._tail = new Node(set2Current.value, interSet._tail.next);
                temp.next = interSet._tail;
            }
        }
        return interSet;
    }

    // Метод для получения мн-ва разницы
    public Set difference(Set set) {
        // Если список этот же возвращаем пустой список
        if (set == this || set._tail == null) return new Set();
        if (_tail == null) return new Set(set);
        if(rangeNotInter(this, set)) return new Set(set);
        // Создаем новое множество из исходного множества
        Set differSet = new Set(set);
        Node differCurrent = differSet._tail;
        Node setCurrent = _tail.next;
        // Если найдены одинаковые значения, значит их нужно удалить из мн-ва
        while (setCurrent != _tail) {
            Node previous = differSet.findValueLocation(differCurrent, setCurrent.value);
            if (previous.next.value == setCurrent.value) {
                // При удаления проверяем, удаляем ли мы голову, и не удаляем ли мы последний элемент
                if (previous.next == differSet._tail) {
                    if (previous.next == previous) {
                        differSet._tail = null;
                        return differSet;
                    }
                    previous.next = differSet._tail.next;
                    differSet._tail = previous;
                } else {
                    previous.next = previous.next.next;
                }
                differCurrent = previous;
            } else if (previous.next ==  differSet._tail) {
                return differSet;
            } else {
                differCurrent = previous;
            }
            setCurrent = setCurrent.next;
        }
        // Обработка хвоста по такому же алгоритму
        Node previous = differSet.findValueLocation(differCurrent, _tail.value);
        if (previous.next.value == setCurrent.value) {
            if (previous.next == differSet._tail) {
                if (previous.next == previous) {
                    differSet._tail = null;
                    return differSet;
                }
                previous.next = differSet._tail.next;
                differSet._tail = previous;
            } else {
                previous.next = previous.next.next;
            }
        }
        return differSet;
    }

    // Метод для получения мн-ва обьедения, можно вызывать только после проверки haveCommonElements
    public Set merge(Set set) {
        if (set == this) return new Set(set);
        // Если оба списка пустых возвращаем пустой список
        if (set._tail == null && _tail == null) return new Set();
        // Если кто из списка пустой, возвращаем копию непустого списка
        if (set._tail == null) return new Set(this);
        if (_tail == null) return new Set(set);
        // Создаем новое множество их исходного множества
        // Пробегаемся по второму множеству пока не будут вставлены все элементы
        Set mergeSet = new Set(this);
        Node mergeCurrent = mergeSet._tail;
        Node setCurrent = set._tail.next;
        while (setCurrent != set._tail) {
            Node previous = mergeSet.findValueLocation(mergeCurrent, setCurrent.value);
             if (previous.next == mergeSet._tail) {
                 mergeCurrent.next = previous;
                break;
            } else {
                previous.next = new Node(setCurrent.value, previous.next);
                mergeCurrent = previous.next;
            }
            setCurrent = setCurrent.next;
        }

        if(mergeCurrent.next == mergeSet._tail) {
            while (setCurrent != set._tail) {
                Node temp = mergeSet._tail;
                mergeSet._tail = new Node(setCurrent.value, mergeSet._tail.next);
                temp.next = mergeSet._tail;
                setCurrent = setCurrent.next;
            }
        }

        // Обработка хвоста
        Node previous = mergeSet.findValueLocation(mergeCurrent, set._tail.value);
        if (previous.next.value < setCurrent.value) {
            Node temp = mergeSet._tail;
            mergeSet._tail = new Node(set._tail.value, mergeSet._tail.next);
            temp.next = mergeSet._tail;
        } else if (previous.next.value > setCurrent.value) {
            previous.next = new Node(setCurrent.value,  previous.next);
        }

        return mergeSet;
    }


    // Присваивает новое множество в исходное множество
    public void assign(Set set) {
        if(set == this) return;
        if (set._tail == null) {
            _tail = null;
            return;
        }
        _tail = copyFrom(set._tail.next, set._tail);
    }

    // Проверяет равны ли множества
    public boolean equal(Set set) {
        // Если это же множество или оба множества пустые значит они одинаковые
        if (set._tail == _tail) {
            return true;
        }
        // Проходим параллельно по узлам обоим списка, пока кто-либо не закончиться
        // Если хотя бы один узел не равен, то возвращаем false
        // Если один из списков или оба списка закончилось, результатом будет равенство следующих элементов обоих множеств
        Node set1Current = _tail;
        Node set2Current = set._tail;
        while (set1Current.next != _tail && set2Current.next != set._tail) {
            if (set1Current.value != set2Current.value) {
                return false;
            }
            set1Current = set1Current.next;
            set2Current = set2Current.next;
        }
        return set1Current.next.value == set2Current.next.value;
    }

    // Возвращает то множество в котором есть это число
    public Set find(Set set, int value) {
        // Использует метод isMember, чтобы найти множество в котором есть это значение
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
        if (value > _tail.value) {
            Node temp = _tail;
            _tail = new Node(value, _tail.next);
            temp.next = _tail;
            return;
        }
        Node previous = findValueLocation(_tail, value);
        if (previous.next.value == value) return;
        previous.next = new Node(value, previous.next);

    }

    // Удаляет значение из множества, если оно там есть, если нет ничего не делать
    public void delete(int value) {
        if (_tail == null || value > _tail.value) return;
        if (_tail == _tail.next) {
            if (_tail.value == value) {
                _tail = null;
            }
            return;
        }
        Node previous = findValueLocation(_tail, value);
        if (previous.next.value == value) {
            if (previous.next == _tail) {
                previous.next = _tail.next;
                _tail = previous;
            } else {
                previous.next = previous.next.next;
            }
        }

    }


    // Обнуляет список
    public void makeNull() {
        // Обнуляем хвост
        _tail = null;
    }

    // Метод для проверки есть ли общие элементы, используется для проверки в find и merge
    public boolean haveCommonElements(Set set) {
        if (set == this) return true;
        if (set._tail == null || _tail == null) {
            return false;
        }
        if (rangeNotInter(this, set)) return false;

        Node set1Current = _tail;
        Node set2Current = set._tail.next;
        while (set2Current != set._tail) {
            Node previous = findValueLocation(set1Current, set2Current.value);
            if (previous.next.value == set2Current.value) {
               return true;
            } else if (previous.next == _tail) {
                return false;
            } else {
                set1Current = previous;
            }
            set2Current = set2Current.next;
        }

        // Обработка хвоста по такому же алгоритму
        Node previous = findValueLocation(set1Current, set._tail.value);
        if (previous.next.value == set2Current.value) {
            return true;
        }
        return false;
    }

    // Находит узел который, находит предыдущее значение к найденному или больше исходному значению
    private Node findValueLocation(Node startNode, int value) {
        Node previous = startNode;
        Node current = startNode.next;
        while (current != _tail) {
            if (current.value >= value) {
                return previous;
            }
            previous = current;
            current = current.next;
        }
        return previous;
    }

    // Определяет есть ли число в множестве
    private boolean isMember(int value) {
        if (_tail == null) return false;
        Node previousNode = findValueLocation(_tail, value);
        return previousNode.next.value == value;
    }

    // Копирует множество
    private Node copyFrom(Node from, Node fromTail) {
        if (from == null || fromTail == null) return null;
        Node tail = new Node(fromTail.value, null);
        tail.next = tail;
        Node to = tail;
        while (from != fromTail) {
            to.next = new Node(from.value, tail);
            from = from.next;
            to = to.next;
        }
        return tail;
    }


    // Метод для быстрой проверки не пересекающихся диапазонов
    private boolean rangeNotInter(Set first, Set second) {
        return first._tail.value < second._tail.next.value || second._tail.value < first._tail.next.value;
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
