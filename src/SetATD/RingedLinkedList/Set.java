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

    public Set union(Set set) {
        // Если список этот же возвращает копию списка
        if (set == this) return new Set(set);
        // Если оба списка пустых возвращаем пустой список
        if (set._tail == null && _tail == null) {
            return new Set();
        }
        // Если кто из списка пустой, возвращаем копию непустого списка
        if (set._tail == null) {
            return new Set(set);
        }
        if (_tail == null) {
            return new Set(this);
        }
        // Создаем новое множество их исходного множества
        // Пробегаемся по второму множеству пока не будут вставлены все элементы
        Set unionSet = new Set();
        Node firstCurrent = _tail.next;
        Node secondCurrent = set._tail.next;
        // Инициализируем голову списка, которая является младшей голов из двух голов
        unionSet._tail = new Node(Math.min(firstCurrent.value, secondCurrent.value), unionSet._tail);
        Node unionCurrent = unionSet._tail;
        firstCurrent = firstCurrent.next;
        secondCurrent = secondCurrent.next;
        // Идем по спискам пока кто либо из них не станет пустым
        // Если значения равны, тогда мы берем любое, и продвигаем по дальше
        // Мы ищем наименьший элемент из двух список и копируем его следущим элементом
        // Когда мы берем элемент из какого то списка мы меняем его на следущий
        while (firstCurrent != _tail.next && secondCurrent != set._tail.next) {
            if (firstCurrent.value == secondCurrent.value) {
                unionCurrent.next = new Node(firstCurrent.value, unionSet._tail);
                firstCurrent = firstCurrent.next;
                secondCurrent = secondCurrent.next;
            } else if (firstCurrent.value < secondCurrent.value) {
                unionCurrent.next = new Node(firstCurrent.value, unionSet._tail);
                firstCurrent = firstCurrent.next;
            } else {
                unionCurrent.next = new Node(secondCurrent.value, unionSet._tail);
                secondCurrent = secondCurrent.next;
            }
            unionCurrent = unionCurrent.next;
        }
        unionSet.print();
        // Если кто то из списков остался не пустым мы должны скопировать отуда все его элементо последовательно в конец
        while (firstCurrent != _tail.next) {
            System.out.println("oneLeft " + firstCurrent.value);
            unionCurrent.next = new Node(firstCurrent.value, unionSet._tail);
            unionCurrent = unionCurrent.next;
            firstCurrent = firstCurrent.next;
        }
        while (secondCurrent != set._tail.next) {
            System.out.println("secondLeft");
            unionCurrent.next = new Node(secondCurrent.value, unionSet._tail);
            unionCurrent = unionCurrent.next;
            secondCurrent = secondCurrent.next;
        }
        return unionSet;
    }

    public Set intersection(Set set) {
        // Если список этот же возвращает копию списка
        if (set == this) return new Set(set);
        // Если хотя бы один из списков пустой возвращаем пустой список
        if (set._tail == null || _tail == null) {
            return new Set();
        }
        Set interSet = new Set();
        Node interCurrent = null;
        Node firstCurrent = _tail.next;
        Node secondCurrent = set._tail.next;
        // Ищем первый элемент пересечения, если элемент не найден, значит пересечений нет
        while (firstCurrent != _tail && secondCurrent != _tail) {
            if (firstCurrent.value == secondCurrent.value) {
                if(interCurrent == null) {
                    interSet._tail = new Node(firstCurrent.value, interSet._tail);
                    interCurrent = interSet._tail;
                }
                else {
                    interCurrent.next = new Node(firstCurrent.value, interSet._tail);
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
        if (set == this || set._tail == null) return new Set();
        if(_tail == null) return new Set(set);
        // Создаем копие входящие го множества
        Set differSet = new Set(set);
        Node differCurrent = null;
        Node firstCurrent = _tail.next;
        Node secondCurrent = set._tail.next;
        while (firstCurrent != _tail && secondCurrent != set._tail) {
            if (firstCurrent.value == secondCurrent.value) {
                firstCurrent = firstCurrent.next;
                secondCurrent = secondCurrent.next;
            } else if (firstCurrent.value < secondCurrent.value) {
                firstCurrent = firstCurrent.next;
            } else {
                if(differCurrent == null) {
                    differSet._tail = new Node(secondCurrent.value, differSet._tail);
                    differCurrent = differSet._tail;
                }
                else {
                    differCurrent.next = new Node(secondCurrent.value, differSet._tail);
                    differCurrent = differCurrent.next;
                }
                secondCurrent = secondCurrent.next;
            }
        }
        while (secondCurrent != set._tail) {
            if(differCurrent == null) {
                differSet._tail = new Node(secondCurrent.value, differSet._tail);
                differCurrent = differSet._tail;
            }
            else {
                differCurrent.next = new Node(secondCurrent.value, differSet._tail);
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
        if (set._tail == null && _tail == null) {
            return new Set();
        }
        // Если кто из списка пустой, возвращаем копию непустого списка
        if (set._tail == null) {
            return new Set(set);
        }
        if (_tail == null) {
            return new Set(this);
        }
        // Создаем новое множество их исходного множества
        // Пробегаемся по второму множеству пока не будут вставлены все элементы
        Set unionSet = new Set();
        Node firstCurrent = _tail.next;
        Node secondCurrent = set._tail.next;
        // Инициализируем голову списка, которая является младшей голов из двух голов
        unionSet._tail = new Node(Math.min(firstCurrent.value, secondCurrent.value), null);
        unionSet._tail.next = _tail;
        Node unionCurrent = unionSet._tail;
        firstCurrent = firstCurrent.next;
        secondCurrent = secondCurrent.next;
        // Идем по спискам пока кто либо из них не станет пустым
        // Если значения равны, тогда мы берем любое, и продвигаем по дальше
        // Мы ищем наименьший элемент из двух список и копируем его следущим элементом
        // Когда мы берем элемент из какого то списка мы меняем его на следущий
        while (firstCurrent != _tail && secondCurrent != set._tail) {
            if (firstCurrent.value < secondCurrent.value) {
                unionCurrent.next = new Node(firstCurrent.value, unionSet._tail);
                firstCurrent = firstCurrent.next;
            } else {
                unionCurrent.next = new Node(secondCurrent.value, unionSet._tail);
                secondCurrent = secondCurrent.next;
            }
            unionCurrent = unionCurrent.next;
        }
        // Если кто то из списков остался не пустым мы должны скопировать отуда все его элементо последовательно в конец
        while (firstCurrent != _tail) {
            unionCurrent.next = new Node(firstCurrent.value, unionSet._tail);
            unionCurrent = unionCurrent.next;
            firstCurrent = firstCurrent.next;
        }
        while (secondCurrent != set._tail) {
            unionCurrent.next = new Node(secondCurrent.value, unionSet._tail);
            unionCurrent = unionCurrent.next;
            secondCurrent = secondCurrent.next;
        }
        return unionSet;
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
        // Если больше хвоста, должен быть новый хвост
        if (value > _tail.value) {
            Node temp = _tail;
            _tail = new Node(value, _tail.next);
            temp.next = _tail;
            return;
        }
        // Ищем узел с нужным значением помощью findClosest
        Node closestNode = findClosest(value);
        // Если уже есть в множестве, ничего не делаем
        if (closestNode.value == value) {
            return;
        }
        // Иначе вставляем новый элемент после ближайшего меньшего значения
        closestNode.next = new Node(value, closestNode.next);
    }

    // Удаляет значение из множества, если оно там есть, если нет ничего не делать
    public void delete(int value) {
        // Если список пустой, ничего не делаем
        if (_tail == null) {
            return;
        }
        // Случай, если удаляемое значение - хвост
        // Если элемент единственный, то просто обнуляет хвост
        // Иначе, ищем предыдущий, и обновляем значение хвоста
        if (_tail.value == value) {
            if (_tail == _tail.next) {
                _tail = null;
                return;
            } else {
                Node previous = _tail;
                while (previous.next != _tail) {
                    previous = previous.next;
                }
                previous.next = _tail.next;
                _tail = previous;
            }
        }
        // Ищем узел, где находиться число, и одновременно храним его предыдущий элемент для замены
        // Если не найден ничего не делаем
        Node previous = _tail;
        Node current = _tail.next;
        while (current != _tail && current.value < value) {
            previous = current;
            current = current.next;
        }
        if (current.value == value) {
            previous.next = current.next;
        }
    }


    // Обнуляет список
    public void makeNull() {
        // Обнуляем хвост
        _tail = null;
    }

    // Находит узел который, находит это же значение или ближайшее меньшее его значение
    private Node findClosest(int value) {
        if (_tail.value == value) return _tail;
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
        if(_tail == null) return false;
        // Вызываем метод findClosest для поиска узла с этим значением
        Node closest = findClosest(value);
        // Если список пустой или не найдее элемент с таким значением
        if (closest.value != value) return false;
        // Если все хорошо, значит принадлежит
        return true;
    }

    // Копирует множество
    private void copyFrom(Set set) {
        // Если копируемое множество пустое, то обнуляем список
        if (set._tail == null) {
            _tail = null;
            return;
        }
        // Иначе инициализируем хвост и по узлам копируем взе значения
        _tail = new Node(set._tail.value, null);
        _tail.next = _tail;
        Node from = set._tail;
        Node to = _tail;
        while (from.next != set._tail) {
            to.next = new Node(from.next.value, _tail);
            from = from.next;
            to = to.next;
        }
    }


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
