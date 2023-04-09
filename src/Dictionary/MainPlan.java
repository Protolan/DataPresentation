package Dictionary;
import Dictionary.Close.Dictionary;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class MainPlan {

    // Максимальная длина имени
    private static final int NAME_CAPACITY = 10;
    // Максимальное кол-во имен
    private static final int CAPACITY = 100;
    // Кол-во классов
    private static final int CLASS_COUNT = 10;
    // Имя файла откуда будет считываться имена
    private static final String FILE_NAME = "src/Dictionary/first-names.txt";

    public static void main(String[] args) throws IOException {
        // Инициализируем списки хороших и плохих
        // Инициализирем FileReader и Scanner и построчно заполняем "хороших парней"
        // Каждую строку мы преоборазуем в массива char c помощью метода nameFactory
        // Не забываем закрыть файл после чтения
        // Печатаем список хороших парней
        // Инициализируем Scanner для ввода данных
        // Читаем построчно пока не встретиться E
        // Делим ответ на 2 элемента: 1-команда 2-имя
        // Имя также получаем через nameFactory
        // Если Команда F, удаляем имя из плохих и вставляем в список хороших
        // Если Команда U, удаляем имя из хороших и вставляем это имя в список плохих
        // Если команда ?, находим спиоск в котором он есть и пишем название элемента и название группу
        // Выводим списки хорошим и плохих
    }

    // Метод для приведения имени из строки в массив char заданной длины
    private static char[] nameFactory(String str) {
        // Инициализируем массив char длиной NAME_CAPACITY
        // Получаем из входной строки массив с помощью toCharArray
        // Копируем значения
        return null;
    }


}
