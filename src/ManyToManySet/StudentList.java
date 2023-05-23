package ManyToManySet;

public class StudentList {
    // Значение, которое указывает
    private static final Student USED = new Student(null);
    private final Student[] _array;

    public StudentList(int capacity) {
        //Инициализируем массив длиной maxCapacity и количество классов
        // Создаем
        _array = new Student[capacity];
    }



    // Вставить в хеш таблицу
    public void insert(char[] name) {
        // Вычисляем хеш индекс
        int startIndex = hashFunction(name);
        int currentIndex = startIndex;
        // Идем пока не найдем первый Null
        // Проверят не сделали ли мы целый оборот (сравниваем текущий индекс с начальным), если да то место для вставки нет, выбросить исключение
        while (_array[currentIndex] != null) {
            // Если есть такой элемент уже есть то выйти
            if(compareNames(name, _array[currentIndex].name)) return;
            // Если текущий индекс использованный, тогда сохраняем положения
            // Дальше нам нужно убедиться, что такого элемента уже нет в списке
            // Проверяем на этот элемент, пока не встретим ни разу не использовавашиеся или список закончиться
            // И только после этого вставляем его в сохраненную USED позцию
            if(_array[currentIndex] == USED) {
                int saveInput = currentIndex;
                while (_array[currentIndex] != null) {
                    if(compareNames(name, _array[currentIndex].name)) return;
                    currentIndex = getNext(currentIndex);
                    if (currentIndex == startIndex) {
                        break;
                    }
                }
                _array[saveInput] = new Student(name);
                return;
            }
            currentIndex = getNext(currentIndex);
            if (currentIndex == startIndex) throw new RuntimeException("Невозможно добавить еще элементов!");
        }

        _array[currentIndex] = new Student(name);
    }

    public void delete(char[] name) {
        // Вычисляем хеш индекс
        int startIndex = hashFunction(name);
        int currentIndex = startIndex;
        // Идем пока не будет null обьект
        // Если текущий элемент использованный значим пропускаем
        // Если нет проверяем на совпадение имен
        // Если имя совпало присвоить текущему значению USED и выйти
        // Проверяем каждую итерацию не сделали ли мы целый оборот(сравниваем текущий индекс с начальным), тогда элемент не найден
        while (_array[currentIndex] != null) {
            if (_array[currentIndex] != USED && _array[currentIndex].name == name) {
                _array[currentIndex] = USED;
                return;
            }
            currentIndex = getNext(currentIndex);
            if (currentIndex == startIndex) {
                System.out.println("Элемент не найден");
                return;
            }
        }
        System.out.println("Элемент не найден");
    }

    public boolean member(char[] name) {
        // Вычисляем хеш индекс
        int startIndex = hashFunction(name);
        int currentIndex = startIndex;
        // Идем пока не будет null обьект, если дойдем до конца возвращаем false
        // Если текущий элемент использованный значим пропускаем
        // Если нет проверяем на совпадение имен
        // Если имя совпало элемент найден
        // Проверяем каждую итерацию не сделали ли мы целый оборот(сравниваем текущий индекс с начальным), тогда элемент не найден
        while (_array[currentIndex] != null) {
            System.out.println(currentIndex);
            if (_array[currentIndex] != USED && _array[currentIndex].name == name) {
                return true;
            }
            currentIndex = getNext(currentIndex);
            if (currentIndex == startIndex) {
                return false;
            }
        }
        return false;
    }

    public Student get(char[] name) {
        // Вычисляем хеш индекс
        int startIndex = hashFunction(name);
        int currentIndex = startIndex;
        // Идем пока не будет null обьект, если дойдем до конца возвращаем false
        // Если текущий элемент использованный значим пропускаем
        // Если нет проверяем на совпадение имен
        // Если имя совпало элемент найден
        // Проверяем каждую итерацию не сделали ли мы целый оборот(сравниваем текущий индекс с начальным), тогда элемент не найден
        while (_array[currentIndex] != null) {
            System.out.println(currentIndex);
            if (_array[currentIndex] != USED && _array[currentIndex].name == name) {
                return _array[currentIndex];
            }
            currentIndex = getNext(currentIndex);
            if (currentIndex == startIndex) {
                return null;
            }
        }
        return null;
    }


    public void makeNull() {
        // Пробегаемся по массиву очищаем
        for (int i = 0; i < _array.length; i++) {
            _array[i] = null;
        }
    }



    // Хеш-функция результат которой вернет нужный индекс в массиве
    private int hashFunction(char[] name) {
        char sum = 0;
        // Пробегаемся по массиву char
        // Вопрос что лучше, бежать по массиву до конца и складывать включая возможные пустые символы
        // Или бежать по массиву пока не встретиться первый пустой символ или не дойдет до конца?
        for (int i = 0; i < name.length; i++) {
            sum += name[i];
        }
        return sum % _array.length;
    }

    // Метод для определения следущего элемента в массиве
    private int getNext(int i) {
        return (i + 1) % _array.length;
    }

    // Метод для сравнения имен, рассчитываем на то что передаются имена одной длины
    private boolean compareNames(char[] name1, char[] name2) {
        for (int i = 0; i < name1.length; i++) {
            if (name1[i] != name2[i]) return false;
        }
        return true;
    }

}
