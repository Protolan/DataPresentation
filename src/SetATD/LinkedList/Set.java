package SetATD.LinkedList;

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

    public Set(int x, int y) {
        _head = null;
    }

    // Копирующий конструктор
    public Set(Node node) {
        _head = getNodeCopy(node);
    }

    // Метод для получения мн-ва пересечения
    public Set union(Set set) {
        if (set == this) return new Set(set._head);
        // Если оба списка пустых возвращаем пустой список
        if (set._head == null && _head == null) {
            return new Set();
        }
        // Если кто из списка пустой, возвращаем копию непустого списка
        if (set._head == null) {
            return new Set(_head);
        }
        if (_head == null) {
            return new Set(set._head);
        }

        // Создаем новое множество их исходного множества
        // Пробегаемся по второму множеству пока не будут вставлены все элементы
        Set unionSet = new Set();
        Node firstCurrent = _head;
        Node secondCurrent = set._head;
        // Инициализируем голову списка, которая является младшой голов из двух голов
        if (firstCurrent.value == secondCurrent.value) {
            unionSet._head = new Node(firstCurrent.value, null);
            firstCurrent = firstCurrent.next;
            secondCurrent = secondCurrent.next;
        } else if (firstCurrent.value < secondCurrent.value) {
            unionSet._head  = new Node(firstCurrent.value, null);
            firstCurrent = firstCurrent.next;
        } else {
            unionSet._head  = new Node(secondCurrent.value, null);
            secondCurrent = secondCurrent.next;
        }
        Node unionCurrent = unionSet._head;
        // Идем по двум связным спискам и заполняем новое множество
        // Если значения одинаковые пропускаем
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
        if (firstCurrent != null) {
            unionCurrent.next = getNodeCopy(firstCurrent);
        } else if (secondCurrent != null) {
            unionCurrent.next = getNodeCopy(secondCurrent);
        }
        return unionSet;
    }

    // Метод для получения мн-ва пересечения
    public Set intersection(Set set) {
        // Если список этот же возвращает копию списка
        if (set == this) return new Set(_head);
        // Если хотя бы один из списков пустой возвращаем пустой список
        if (set._head == null || _head == null) {
            return new Set();
        }
        Set interSet = new Set();
        Node interCurrent = null;
        Node firstCurrent = _head;
        Node secondCurrent = set._head;

        // Используем метод findValueLocation, чтобы пройтись по двум спискам
        // Если значения однинаковые добавляем новый элемент
        while (secondCurrent != null) {
            Node foundValue = set.findValueLocation(firstCurrent, secondCurrent.value);
            if(foundValue == null) {
                secondCurrent = secondCurrent.next;
                continue;
            }
            if(foundValue.next == null) return interSet;
            if(foundValue.next.value == secondCurrent.value) {
                if (interCurrent == null) {
                    interSet._head = new Node(firstCurrent.value, null);
                    interCurrent = interSet._head;
                } else {
                    interCurrent.next = new Node(firstCurrent.value, null);
                    interCurrent = interCurrent.next;
                }
                firstCurrent = foundValue.next.next;
                secondCurrent = secondCurrent.next;
            }
        }
        return interSet;
    }

    // Метод для получения мн-ва пересечения
    public Set difference(Set set) {
        // Если список этот же возвращаем пустой список
        if (set == this || set._head == null) return new Set();
        if (_head == null) return new Set(set._head);
        Set differSet = new Set();
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
                // Проверяем инициализириовали ли мы голову
                if (differCurrent == null) {
                    differSet._head = new Node(secondCurrent.value, null);
                    differCurrent = differSet._head;
                } else {
                    differCurrent.next = new Node(secondCurrent.value, null);
                    differCurrent = differCurrent.next;
                }
                secondCurrent = secondCurrent.next;
            }
        }
        // Проверяем элементы во втором множестве остались просто их копируем в конец
        if (secondCurrent != null) {
            if (differCurrent == null) differSet._head = getNodeCopy(secondCurrent);
            else differCurrent.next = getNodeCopy(secondCurrent);
        }

        return differSet;
    }

    public Set merge(Set set) {
        // Такой же алгоритм как в union, только без проверки на равенство
        if (set == this) return new Set(set._head);
        // Если оба списка пустых возвращаем пустой список
        if (set._head == null && _head == null) return new Set();
        // Если кто из списка пустой, возвращаем копию непустого списка
        if (set._head == null) return new Set(_head);
        if (_head == null) return new Set(set._head);
        // Создаем новое множество их исходного множества
        // Пробегаемся по второму множеству пока не будут вставлены все элементы
        Set mergeSet = new Set();
        Node firstCurrent = _head;
        Node secondCurrent = set._head;
        // Инициализируем голову списка, которая является младшой голов из двух голов
        if (firstCurrent.value == secondCurrent.value) {
            mergeSet._head = new Node(firstCurrent.value, null);
            firstCurrent = firstCurrent.next;
            secondCurrent = secondCurrent.next;
        } else if (firstCurrent.value < secondCurrent.value) {
            mergeSet._head  = new Node(firstCurrent.value, null);
            firstCurrent = firstCurrent.next;
        } else {
            mergeSet._head  = new Node(secondCurrent.value, null);
            secondCurrent = secondCurrent.next;
        }
        Node mergeCurrent = mergeSet._head;
        // Такой же алгоритм как в Union, только без проверки на равенство
        while (firstCurrent != null && secondCurrent != null) {
            if (firstCurrent.value < secondCurrent.value) {
                mergeCurrent.next = new Node(firstCurrent.value, null);
                firstCurrent = firstCurrent.next;
            } else {
                mergeCurrent.next = new Node(secondCurrent.value, null);
                secondCurrent = secondCurrent.next;
            }
            mergeCurrent = mergeCurrent.next;
        }
        // Если кто то из списков остался не пустым мы должны скопировать отуда все его элементо последовательно в конец
        if (firstCurrent != null) {
            mergeCurrent.next = getNodeCopy(firstCurrent);
        } else if (secondCurrent != null) {
            mergeCurrent.next = getNodeCopy(secondCurrent);
        }
        return mergeSet;
    }


    // Присваивает новое множество
    // Необходимо скопировать ячейки то есть создать новые ноды
    public void assign(Set set) {
        if(set == this) return;
        set._head = getNodeCopy(set._head);
    }

    // Копирует входящее множества в исходное
    private Node getNodeCopy(Node from) {
        if (from == null) return null;
        // Иначе инициализируем голову и копируем поэлементно
        // Мы не можем просто присвоить узел так как обьект
        Node copyRoot = new Node(from.value, null);
        Node to = copyRoot;
        from = from.next;
        while (from != null) {
            to.next = new Node(from.value, null);
            from = from.next;
            to = to.next;
        }
        return copyRoot;
    }

    public boolean equal(Set set) {
        // Пробегается параллельно по двум спискам пока кто-либо и них не станет пустым
        // Каждую итерация проверяем на равенство, если хотя бы один не равен, то возвращаем false
        // Оба списка должны были закончиться, проверяем что они оба равны null
        Node set1Current = _head;
        Node set2Current = set._head;
        while (set1Current != null && set2Current != null) {
            if (set1Current.value != set2Current.value) {
                return false;
            }
            set1Current = set1Current.next;
            set2Current = set2Current.next;
        }
        return set1Current == set2Current;
    }

    // Метод для поиска мн-ва в котором находится элемент, можно использовать только после haveCommonElements
    public Set find(Set set, int value) {
        if (isMember(value)) return this;
        else if (set.isMember(value)) return set;
        else return null;
    }

    public boolean member(int value) {
        return isMember(value);
    }


    private boolean isMember(int value) {
        Node previousNode = findValueLocation(_head, value);
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
        if (_head == null || _head.value > value) return;
        // Проверка на первое значение
        if (_head.value == value) {
            _head = _head.next;
            return;
        }

        Node previousNode = findValueLocation(_head, value);
        if (previousNode.next != null && previousNode.next.value == value) {
            previousNode.next = previousNode.next.next;
        }
    }

    public void makeNull() {
        _head = null;
    }


    private Node findValueLocation(Node start, int value) {
        // Пробегаемся по связному списку
        // Если текущее значение больше или равно числу возвращаем предыдущее к нему
        // Получается, если значение не найден, вернётся последний элемент списка(value.next == null)
        Node previous = null;
        Node current = start;
        while (current != null) {
            if (current.value >= value) {
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
        if (set == this) return true;
        if (set._head == null || _head == null) {
            return false;
        }
        Node set1Current = _head;
        Node set2Current = set._head;
        while (set2Current != null) {
            Node foundValue = set.findValueLocation(set1Current, set2Current.value);
            if(foundValue == null) {
                set2Current = set2Current.next;
                continue;
            }
            if(foundValue.next == null) return false;
            if(foundValue.next.value == set2Current.value) {
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
