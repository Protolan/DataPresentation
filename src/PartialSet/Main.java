package PartialSet;

import PartialSet.Plan.PartialSet;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


public class Main {
    private static final String FILE_NAME = "C:\\Users\\alang\\Desktop\\Учеба\\Представление данных\\DataPresentation\\src\\PartialSet\\PartialSetData";

    public static void main(String[] args) throws FileNotFoundException {
        PartialSet set = new PartialSet();
        FileReader reader = new FileReader(FILE_NAME);
        Scanner scanner = new Scanner(reader);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String input = line.trim();
            String[] parts = input.split("<");
            set.add(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        }
        set.print();
        System.out.println("------------");
        set.sort();
        set.print();

    }


}