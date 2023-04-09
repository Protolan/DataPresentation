package Dictionary.ClosePlan;

public class Dictionary {
    // Массив, который будет использовать у использованных элементов массива
    private static final char[] USED = {0};
    private final char[][] _array;

    public Dictionary(int capacity, int classCount) {
        //Инициализируем массив длиной maxCapacity с пустыми массивами
        _array = new char[capacity][];
    }

    // Вставить в хеш таблицу
    public void insert(char[] name) {
        // Вычисляем хеш индекс
        // Идем пока не найдем первый Null или USED обьект
        // Проверят не сделали ли мы целый оборот (сравниваем текущий индекс с начальным), если да то место для вставки нет, выбросить исключение
        // Нужно ли проверять на уже имеющиеся значение?
    }

    public void delete(char[] name) {
        // Вычисляем хеш индекс
        // Идем пока не будет null обьект
        // Если текущий элемент использованный значим пропускаем
        // Если нет проверяем на совпадение имен
        // Если имя совпало присвоить текущему значению USED и выйти
        // Проверяем каждую итерацию не сделали ли мы целый оборот(сравниваем текущий индекс с начальным), тогда элемент не найден
    }

    public boolean member(char[] name) {
        // Вычисляем хеш индекс
        // Идем пока не будет null обьект, если дойдем до конца возвращаем false
        // Если текущий элемент использованный значим пропускаем
        // Если нет проверяем на совпадение имен
        // Если имя совпало элемент найден
        // Проверяем каждую итерацию не сделали ли мы целый оборот(сравниваем текущий индекс с начальным), тогда элемент не найден
        return false;
    }

    public void makeNull() {
        // Пробегаемся по массиву очищаем
    }

    // Хеш-функция результат которой вернет нужный индекс в массиве
    private int hashFunction(char[] name) {
        // Пробегаемся по массиву char
        // Вопрос что лучше, бежать по массиву до конца и складывать включая возможные пустые символы
        // Или бежать по массиву пока не встретиться первый пустой символ или не дойдет до конца?
        return 0;
    }

    // Метод для определения следущего элемента в массиве
    private int getNext(int i) {
        return (i + 1) % _array.length;
    }

    // Метод для сравнения имен, рассчитываем на то что передаются имена одной длины
    private boolean compareNames(char[] name1, char[] name2) {
        return true;
    }



}