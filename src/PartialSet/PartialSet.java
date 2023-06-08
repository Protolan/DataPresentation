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

        // Инициализируем голову
        var head = pop();
        var current = head;
        // Вызываем метод pop, пока он не вернет пустой список
        while (current != null) {
            current.next = pop();
            current = current.next;
        }
        // Назначаем новое значение голове
        _head = head;
    }

    // Метод для получения удаленного элемента
    private Element pop() {
        // Алгоритм:
        // Вначале находим предыдущий элемент к тому элемент которого count = 0
        // Затем удаляем обьект из списка и очищаем его с помощью clearElement
        // Возвращаем удаленный элемент

        if (_head == null) return null;
        // Если элемент голова то берем его и очищаем
        if (_head.count == 0) {
            Element removeNode = _head;
            _head = _head.next;
            clearElement(removeNode);
            return removeNode;
        }
        Element previous = _head;
        while (previous.next != null) {
            if (previous.next.count == 0) {
                break;
            }
            previous = previous.next;
        }

        if (previous.next == null) {
            // Если в списке остались элементы, но он не нашел элемент с count == 0, то выбросить исключение
            throw new RuntimeException("Граф зациклен, не возможно найти элементы на которые никто не ссылается");
        }

        // Сохраняем удаленный элемент
        Element removeElement = previous.next;
        // Удаляем элемент из списка
        previous.next = previous.next.next;
        // Очищаем удаленный элемент
        clearElement(removeElement);
        return removeElement;
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
