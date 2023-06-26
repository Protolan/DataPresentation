package Dictionary.Close;

public class Dictionary {
    // Значение, которое указывает, что значение было использовано
    private static final char[] USED = {0};

    private final char[][] _array;

    public Dictionary(int capacity, int classCount) {
        //Инициализируем массив длиной maxCapacity
        _array = new char[capacity][];
    }

    // Метод для вставки значения в словарь
    public void insert(char[] name) {
        // Алгоритм:
        // Вычисляем индекс с помощью хеш-функции
        // Начинаем искать первую свободную позицию для вставки
        // Проверяем есть ли уже такой элемент, если есть выходим
        // Вставляем в свободную позицию
        int i = 0;
        int hash = hash(name);
        int startIndex = hashFunction(hash);
        int currentIndex = startIndex;
        int saveInput = -1;
        // Идем пока не найдем первый Null
        // Проверяем не сделали ли мы целый оборот (сравниваем текущий индекс с начальным), если да то место для вставки нет, выбросить исключение
        while (_array[currentIndex] != null) {
            // Если есть такой элемент уже есть то выйти
            if (compareNames(name, _array[currentIndex])) {
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
            currentIndex = hashFunction(hash + ++i);
            if (currentIndex == startIndex) {
                if (saveInput != -1) break;
                else return;
            }
        }

        _array[saveInput == -1 ? currentIndex : saveInput] = name;
    }

    // Метод для удаления значения из словаря
    public void delete(char[] name) {
        // Алгоритм:
        // Вычисляем индекс с помощью хеш функции
        int i = 0;
        int hash = hash(name);
        int startIndex = hashFunction(hash);
        int currentIndex = startIndex;
        // Идем пока не будет null обьект
        // Если текущий элемент использованный значим пропускаем
        // Если нет проверяем на совпадение имен
        // Если имя совпало присвоить текущему значению USED и выйти
        // Проверяем каждую итерацию не сделали ли мы целый оборот(сравниваем текущий индекс с начальным), тогда элемент не найден
        while (_array[currentIndex] != null) {
            if (_array[currentIndex] != USED && compareNames(_array[currentIndex], name)) {
                _array[currentIndex] = USED;
                return;
            }
            currentIndex = hashFunction(hash + ++i);
            if (currentIndex == startIndex) {
                System.out.println("Элемент не найден");
                return;
            }
        }
        System.out.println("Элемент не найден");
    }


    // Метод для проверки, есть ли этот элемент в словаре
    public boolean member(char[] name) {
        // Алгоритм:
        // Вычисляем индекс с помощью хеш функции
        int i = 0;
        int hash = hash(name);
        int startIndex = hashFunction(hash);
        int currentIndex = startIndex;
        // Идем пока не будет null обьект, если дойдем до конца возвращаем false
        // Если текущий элемент использованный значим пропускаем
        // Если нет проверяем на совпадение имен
        // Если имя совпало элемент найден
        // Проверяем каждую итерацию не сделали ли мы целый оборот(сравниваем текущий индекс с начальным), тогда элемент не найден
        while (_array[currentIndex] != null) {
            if (_array[currentIndex] != USED && compareNames(_array[currentIndex], name)) {
                return true;
            }
            currentIndex = hashFunction(hash + ++i);
            if (currentIndex == startIndex) {
                return false;
            }
        }
        return false;
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
        return  hash % _array.length;
    }

    private static int hash(char[] name) {
        int sum = 0;
        // Пробегаемся по массиву char
        for (int i = 0; i < name.length; i++) {
            sum += name[i];
        }
        return sum;
    }


    // Метод для сравнения имен, рассчитываем на то что передаются имена одной длины
    private boolean compareNames(char[] name1, char[] name2) {
        for (int i = 0; i < name1.length; i++) {
            if (name1[i] != name2[i]) return false;
        }
        return true;
    }


    //ВЫВОД
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int counter = 1;
        for (int i = 0; i < _array.length; i++) {
            if (_array[i] == null || _array[i] == USED) continue;
            builder.append(counter);
            builder.append(": ");
            builder.append(getClearName(_array[i]));
            builder.append("\n");
            counter++;
        }
        return builder.toString();
    }

    // Метод который красиво выводит символьный массив
    private char[] getClearName(char[] name) {
        int counter = 0;
        for (int i = 0; i < name.length; i++) {
            if (name[i] == 0) {
                break;
            }
            counter++;
        }
        char[] clear = new char[counter];
        for (int i = 0; i < counter; i++) {
            clear[i] = name[i];
        }
        return clear;
    }

    private static char[] nameFactory(char[] names) {
        char[] resultName = new char[10];
        for (int i = 0; i < resultName.length; i++) {
            resultName[i] = names[i];
        }
        return resultName;
    }


}
