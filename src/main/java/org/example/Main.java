package org.example;


import java.util.ArrayList;
import java.util.Scanner;


public class Main {

public static boolean correctFile = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите команды и файлы для фильтрации");
        String line = scanner.nextLine();

        // String line = "-s -o /some/newpath -a -p result_ in1.txt in2.txt in3.txt";

        ArrayList<Data> collected = UtilityParser.parsingFiles(line);
            while (collected.isEmpty()) {
                System.out.println("Введите команды и файлы для фильтрации по примеру: " +
                        "-s -o /some/newpath -a -p result_ in1.txt in2.txt in3.txt");
                line = scanner.nextLine();
                collected = UtilityParser.parsingFiles(line);
            }

        UtilityParser.parsingCommands(line);
        FileHelper.saveToFile(FileHelper.appendFlag, collected);

    }

}
