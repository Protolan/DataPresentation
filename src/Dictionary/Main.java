package Dictionary;

import Dictionary.Open.Dictionary;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

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
        Dictionary goods = new Dictionary(CAPACITY, CLASS_COUNT);
        Dictionary bads = new Dictionary(CAPACITY, CLASS_COUNT);
        // Инициализирем FileReader и Scanner и построчно заполняем "хороших парней"
        // Каждую строку мы преоборазуем в массива char c помощью метода nameFactory
        FileReader fr= new FileReader(FILE_NAME);
        Scanner scan = new Scanner(fr);
        int counter = 0;
        while (scan.hasNextLine() && counter < CAPACITY) {
            String line = scan.nextLine();
            if(line.length() > 10) continue;
            counter++;
            goods.insert(nameFactory(line));
        }
        // Не забываем закрыть файл после чтения
        fr.close();
        // Печатаем список хороших парней
        printList(goods, "Список из " + counter + " хороших парней создан");
        // Инициализируем Scanner для ввода данных
        scan = new Scanner(System.in);
        String answer = scan.nextLine();
        // Читаем построчно пока не встретиться E
        // Делим ответ на 2 элемента: 1-команда 2-имя
        // Имя также получаем через nameFactory
        // Если Команда F, удаляем имя из плохих и вставляем в список хороших
        // Если Команда U, удаляем имя из хороших и вставляем это имя в список плохих
        // Если команда ?, находим спиоск в котором он есть и пишем название элемента и название группу
        while (!answer.equals("E")) {
            String[] splitAnswer = answer.split(" ");
            if(splitAnswer.length != 2) continue;
            char[] name = nameFactory(splitAnswer[1]);
            if(splitAnswer[0].equals("F")) {
                bads.delete(name);
                goods.insert(name);
                System.out.println(splitAnswer[1] + " перемещен в список хороших");
            }
            else if(splitAnswer[0].equals("U")) {
                goods.delete(name);
                bads.insert(name);
                System.out.println(splitAnswer[1] + " перемещен в список плохих");
            }
            else if(splitAnswer[0].equals("?")) {
                if(goods.member(name)) {
                    System.out.println(splitAnswer[1] + " хороший");
                }
                if (bads.member(name)) {
                    System.out.println(splitAnswer[1] + " плохой");
                }
            }
            answer = scan.nextLine();
        }
        // Выводим списки хорошим и плохих
        printList(goods, "Хорошие парни:");
        printList(bads, "Плохие парни:");
    }

    // Метод для приведения имени из строки в массив char заданной длины
    private static char[] nameFactory(String str) {
        char[] resultName = new char[NAME_CAPACITY];
        char[] initCharArray = str.toCharArray();
        for (int i = 0; i < initCharArray.length; i++) {
            resultName[i] = initCharArray[i];
        }
        return resultName;
    }

    private static void printList(Dictionary dictionary, String label) {
        System.out.println(label);
        System.out.println(dictionary.toString());
    }
}
