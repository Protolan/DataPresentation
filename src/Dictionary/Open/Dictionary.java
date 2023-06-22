package Dictionary.Open;

public class Dictionary {
    private class Node {
        char[] name;
        Node next;

        public Node(char[] name, Node next) {
            this.name = name;
            this.next = next;
        }

        public Node(char[] name) {
            this.name = name;
            next = null;
        }

        // Метод для сравнения имени
        public boolean compareNames(char[] name) {
            for (int i = 0; i < name.length; i++) {
                if(this.name[i] != name[i]) return false;
            }
            return true;
        }

        // Метод для красивого вывода имени
        public char[] getClearName() {
            int counter = 0;
            for (int i = 0; i < name.length; i++) {
                if(this.name[i] == 0) {
                    break;
                }
                counter++;
            }
            char[] clear = new char[counter];
            for (int i = 0; i < counter; i++) {
                clear[i] = this.name[i];
            }
            return clear;
        }
    }

    // Количество классов для решения коллизии
    private final int B;

    private final Node[] _array;

    public Dictionary(int maxCapacity, int classCount) {
        // Инициализируем количество классов и создаем массива равный кол-во классов
        B = classCount;
        _array = new Node[B];
    }

    // Вставить в хеш таблицу
    public void insert(char[] name) {
        // Вычисляем хеш индекс через метод hashFunction
        int index = hashFunction(hash(name));
        // Если элемент массива пустой, тогда создаем новый обьект с этим именем
        if(_array[index] == null) {
            _array[index] = new Node(name);
            return;
        }
        // Если элемент не пустой, тогда идем по связному списку пока next элемента не будет равен null
        // Так мы найдем предыдущий элемент, чтобы вставить в него следущий элемент
        // Также проверяем во время пробежки проверяем нет ли там уже такого имени, и если найдеться то return
        Node previous = _array[index];
        Node current = _array[index];
        while (current != null) {
            if(current.compareNames(name)) {
                return;
            }
            previous = current;
            current = current.next;
        }
        previous.next = new Node(name);
    }

    public void delete(char[] name) {
        // Вычисляем хеш индекс через метод hashFunction
        int index = hashFunction(hash(name));
        // Если элемент пуcтой, тогда нечего удалять, return
        if(_array[index] == null) {
            return;
        }
        // Проверям первый элемент
        if(_array[index].compareNames(name)) {
            if(_array[index].next == null) {
                _array[index] = null;
            }
            else {
                _array[index] = _array[index].next;
            }
            return;
        }
        // Если элемент не пустой, тогда идем по связному списку пока next элемент не будет равен null
        // Проверяем во время пробежки next на это имя и если имя найдется тогда удаляем элемент
        // Если у удаляемого элемента нет previous, тогда обнуляем элемент массива
        // Если есть, то просто предыдущий ссылается на next текущего
        Node previous = null;
        Node current = _array[index];
        while (current != null) {
            if(current.compareNames(name)) {
                previous.next = current.next;
                return;
            }
            previous = current;
            current = current.next;
        }

    }

    public boolean member(char[] name) {
        // Вычисляем хеш индекс через метод hashFunction
        int index = hashFunction(hash(name));
        // Если элемент пустой, тогда возвращаем false
        if(_array[index] == null) {
            return false;
        }
        // Если элемент не пустой, тогда идем по связному списку пока next элемент не будет равен null
        // Проверяем во время пробежки next на это имя и если имя найдется тогда return true
        // Если не найден, то в конце вернуть false
        Node current = _array[index];
        while (current != null) {
            if(current.compareNames(name)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void makeNull() {
        // Пробегаемся по массиву и обнуляем все значения
        for (int i = 0; i < _array.length; i++) {
            _array[i] = null;
        }
    }

    // Хеш-функция результат которой вернет нужный индекс в массиве
    private int hashFunction(int hash) {
        return  hash % B;
    }

    private static int hash(char[] name) {
        int sum = 0;
        // Пробегаемся по массиву char
        for (int i = 0; i < name.length; i++) {
            sum += name[i];
        }
        return sum;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int counter = 1;
        for (int i = 0; i < _array.length; i++) {
            Node current = _array[i];
            while (current != null) {
                builder.append(counter);
                builder.append(": ");
                builder.append(current.getClearName());
                builder.append("\n");
                current = current.next;
                counter++;
            }
        }
        return builder.toString();
    }
}
