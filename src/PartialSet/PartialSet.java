package PartialSet;

public class PartialSet {
    private static class Element extends Trail {
        // Значение элемента
        public int key;
        // Кол-во отношений элемента (сколько элементов в графе на него ссылаются)
        public int count;

        public Element(int key, Element next) {
            super(next);
            this.key = key;
            count = 0;
        }

    }

    private static class Trail {
        // Ссылка на элемент, который имеет связь с исходным
        public Element next;
        // Ссылка на следующий trail
        public Trail trail;

        public Trail(Element id, Trail next) {
            this.next = id;
            this.trail = next;
        }

        protected Trail(Element id) {
            this.next = id;
            this.trail = null;
        }
    }

    private Element _head;

    public PartialSet() {
        _head = null;
    }

    // Метод для добавления нового отношения в множество
    public void add(int less, int higher) {
        // Алгоритм:
        // Вначале смотрим, есть ли эти значения в списке
        // Если кого нет инициализируем и добавляем в основной список
        // Затем созданные или найденные значения мы добавляем элемент в trail в голову less
        // А для большего значения мы увеличиваем счетчик отношений на единицу

        Element lesEl = null;
        Element highEl = null;
        Element current = _head;
        while (current != null) {
            if (current.key == less) {
                lesEl = current;
            } else if (current.key == higher) {
                highEl = current;
            }
            current = current.next;
        }
        if (lesEl == null) {
            _head = new Element(less, _head);
            lesEl = _head;
        }
        if (highEl == null) {
            _head = new Element(higher, _head);
            highEl = _head;
        }
        lesEl.trail = new Trail(highEl, lesEl.trail);
        highEl.count++;
    }

    // Метод для упорядочивания множества
    public void sort() {
        // Алгоритм:
        // Удаляем из списка все элементы с помощью метода pop
        // Формируем новый список в порядке получения удаленных элементов
        // Перезаписываем голову
        Element newHead = null;
        Element current = null;
        // Вызываем метод pop, пока он не вернет пустой список
        while (_head != null) {
            Element removeNode;
            if(_head.count == 0) {
                 removeNode = _head;
                _head = _head.next;
                clearElement(removeNode);
            }
            else {
                Element previousToRemove = previousToRemove();
                removeNode = previousToRemove.next;
                previousToRemove.next = removeNode.next;
                clearElement(removeNode);
            }
            if(newHead == null) {
                newHead = removeNode;
                current = newHead;
            }
            else {
                current.next = removeNode;
            }

        }
        // Назначаем новое значение голове
        _head = newHead;
    }


    // Метод для получения, вызывать только если haad.count != 0
    private Element previousToRemove() {
        // Алгоритм:
        // Вначале находим предыдущий элемент к тому элемент которого count = 0
        // Затем удаляем обьект из списка и очищаем его с помощью clearElement
        // Возвращаем удаленный элемент
        Element previous = _head;
        Element current = _head;
        while (current != null) {
            if (current.count == 0) {
                return previous;
            }
            previous = current;
            current = previous.next;
        }
        return null;
    }

    // Приватный метод для очищения элемента
    private void clearElement(Element element) {
        // Алгоритм:
        // Проходимся по Trail, уменьшаем счетчики для каждого элементы
        // После обнуляем Trail и Next
        Trail currentTrail = element.trail;
        while (currentTrail != null) {
            currentTrail.next.count--;
            currentTrail = currentTrail.trail;
        }
        element.trail = null;
        element.next = null;
    }


    // Вывод списка
    public void print() {
        Element current = _head;
        while (current != null) {
            System.out.print("Key: " + current.key + " ");
            System.out.print("Count: " + current.count + " ");
            Trail currentTrail = current.trail;
            int counter = 1;
            while (currentTrail != null) {
                System.out.print("Trail" + counter + ": " + currentTrail.next.key + " ");
                counter++;
                currentTrail = currentTrail.trail;
            }
            System.out.print("\n");
            current = current.next;
        }
    }

}
