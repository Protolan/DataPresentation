package ManyToManySet;

public class CourseList {
    // Значение, которое указывает, что значение было использовано
    private static final Course USED = new Course(0);
    private final Course[] _array;

    public CourseList(int capacity) {
        //Инициализируем массив длиной maxCapacity
        _array = new Course[capacity];
    }

    // Вставить в хеш таблицу
    public void insert(int id) {
        // Алгоритм:
        // Вычисляем индекс с помощью хеш-функции
        // Начинаем искать первую свободную позицию для вставки
        // Проверяем есть ли уже такой элемент, если есть выходим
        // Вставляем в свободную позицию
        int i = 0;
        int startIndex = hashFunction(id);
        int currentIndex = startIndex;
        int saveInput = -1;
        // Идем пока не найдем первый Null
        // Проверяем не сделали ли мы целый оборот (сравниваем текущий индекс с начальным), если да то место для вставки нет, выбросить исключение
        while (_array[currentIndex] != null) {
            // Если есть такой элемент уже есть то выйти
            if (_array[currentIndex].id == id) {
                System.out.println("Уже в списке");
                return;
            }
            // Если текущий индекс использованный, тогда сохраняем положения
            // Дальше нам нужно убедиться, что такого элемента уже нет в списке
            // Проверяем на этот элемент, пока не встретим ни разу не использовававшиеся или список закончиться
            // И только после этого вставляем его в сохраненную USED позицию
            if (saveInput == -1 && _array[currentIndex] == USED) {
                saveInput = currentIndex;
            }
            // Используем getNext, что сделать полный оборот
            currentIndex = hashFunction(id + ++i);
            if (currentIndex == startIndex) {
                if (saveInput != -1) {
                    _array[saveInput] = new Course(id);
                    return;
                }
                throw new RuntimeException("Невозможно добавить еще элементов!");
            }

        }

        _array[saveInput == -1 ? currentIndex : saveInput] = new Course(id);
    }

    // Метод для удаления значения из словаря
    public void delete(int id) {
        // Алгоритм:
        // Вычисляем индекс с помощью хеш функции
        int i = 0;
        int startIndex = hashFunction(id);
        int currentIndex = startIndex;
        // Идем пока не будет null обьект
        // Если текущий элемент использованный значим пропускаем
        // Если нет проверяем на совпадение имен
        // Если имя совпало присвоить текущему значению USED и выйти
        // Проверяем каждую итерацию не сделали ли мы целый оборот(сравниваем текущий индекс с начальным), тогда элемент не найден
        while (_array[currentIndex] != null) {
            if (_array[currentIndex] != USED && _array[currentIndex].id == id) {
                _array[currentIndex] = USED;
                return;
            }
            currentIndex = hashFunction(id + ++i);
            if (currentIndex == startIndex) {
                System.out.println("Элемент не найден");
                return;
            }
        }
        System.out.println("Элемент не найден");
    }

    // Метод для проверки, есть ли этот элемент в словаре
    public boolean member(int id) {
        // Алгоритм:
        // Вычисляем индекс с помощью хеш функции
        int i = 0;
        int startIndex = hashFunction(id);
        int currentIndex = startIndex;
        // Идем пока не будет null обьект, если дойдем до конца возвращаем false
        // Если текущий элемент использованный значим пропускаем
        // Если нет проверяем на совпадение имен
        // Если имя совпало элемент найден
        // Проверяем каждую итерацию не сделали ли мы целый оборот(сравниваем текущий индекс с начальным), тогда элемент не найден
        while (_array[currentIndex] != null) {
            System.out.println(currentIndex);
            if (_array[currentIndex] != USED && _array[currentIndex].id == id) {
                return true;
            }
            currentIndex = hashFunction(id + ++i);
            if (currentIndex == startIndex) {
                return false;
            }
        }
        return false;
    }

    // Метод для получения значения из словаря
    public Course get(int id) {
        // Алгоритм:
        // Вычисляем индекс с помощью хеш функции
        int i = 0;
        int startIndex = hashFunction(id);
        int currentIndex = startIndex;
        // Идем пока не будет null обьект, если дойдем до конца возвращаем null
        // Если текущий элемент использованный значим пропускаем
        // Если нет проверяем на совпадение имен
        // Если имя совпало элемент найден
        // Проверяем каждую итерацию не сделали ли мы целый оборот(сравниваем текущий индекс с начальным), тогда элемент не найден
        while (_array[currentIndex] != null) {
            if (_array[currentIndex] != USED && _array[currentIndex].id == id) {
                return _array[currentIndex];
            }
            currentIndex = hashFunction(id + ++i);
            if (currentIndex == startIndex) {
                return null;
            }
        }
        return null;
    }

    // Метод для обнуления словаря
    public void makeNull() {
        // Пробегаемся по массиву очищаем
        for (int i = 0; i < _array.length; i++) {
            _array[i] = null;
        }
    }

    // Хеш-функция результат которой вернет нужный индекс в массиве
    private int hashFunction(int hash) {
        return hash % _array.length;
    }

    // Метод для определения следущего элемента в массиве
    private int getNext(int i) {
        return (i + 1) % _array.length;
    }


}
