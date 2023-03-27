package Dictionary.ClosePlan;

public class Dictionary {
    // Количество классов для решения колизии
    private final int B;
    // Значение, которое указывает
    private static final char[] USED = {0};
    private final char[][] _array;

    public Dictionary(int capacity, int classCount) {
        //Инициализируем массив длиной maxCapacity и количество классов
        B = classCount;
        // Создаем
        _array = new char[capacity][10];
    }

    // Вставить в хеш таблицу
    public void insert(char name) {
        // Вычисляем хеш индекс

    }

    public void delete(char name) {

    }

    public void member(char name) {

    }

    // Метод для нахождения позиции обьекта name
    private int findPosition(char name) {
        return 0;
    }


    public void makenull() {
        // Пробегаемся по массиву очищаем
    }

}
