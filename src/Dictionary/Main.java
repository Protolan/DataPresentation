package Dictionary;

import Dictionary.Open.Dictionary;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final int NAME_CAPACITY = 10;
    private static final int CAPACITY = 100;
    private static final int CLASS_COUNT = 10;
    private static final String FILE_NAME = "src/Dictionary/first-names.txt";
    public static void main(String[] args) throws IOException {
        // Инициализируем списки хороших и плохи
        Dictionary goods = new Dictionary(CAPACITY, CLASS_COUNT);
        Dictionary bads = new Dictionary(CAPACITY, CLASS_COUNT);
        // Инициализирем FileReader и Scanner и построчно заполняем "хороших парней"
        // Каждую строку мы преоборазуем в массива char c помощью метода nameFactory
        FileReader fr= new FileReader(FILE_NAME);
        Scanner scan = new Scanner(fr);
        int counter = 0;
        while (scan.hasNextLine() && counter < 100) {
            var line = scan.nextLine();
            if(line.length() > 10) continue;
            counter++;
            goods.insert(nameFactory(line));
        }
        fr.close();
        printList(goods, "Список из " + counter + " хороших парней создан");
        // Инициализируем Scanner для ввода данных
        scan = new Scanner(System.in);
        String answer = scan.nextLine();
        while (!answer.equals("E")) {
            var splitAnswer = answer.split(" ");
            if(splitAnswer.length != 2) continue;
            var name = nameFactory(splitAnswer[1]);
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
                else if (bads.member(name)) {
                    System.out.println(splitAnswer[1] + " плохой");
                }
            }
            answer = scan.nextLine();
        }
        printList(goods, "Хорошие парни:");
        printList(bads, "Плохие парни:");
    }

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
