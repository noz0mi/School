package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.parseLong;

public class FileHelper {
    public static String generalPath = "";
    public static String intTitle = "integers.txt";
    public static String strTitle = "strings.txt";
    public static String fltTitle = "floats.txt";
    public static boolean appendFlag = false;
    public static boolean shortStat = false;
    public static boolean fullStat = false;


    public static Data collectFromFile(String name) {
        try (FileReader fileReader = new FileReader(name);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            Data data = new Data();
            List<String> innerData = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                innerData.add(line);
            }
            bufferedReader.close();
            data.setRawArray(innerData);
            return data;
        } catch (IOException e) {
            System.out.println(e.getMessage() + "Имя файла набрано неверно или такого файла не существует.");
        }
        return null;
    }

    public static void changeName(String name) {
        String oldInt = intTitle;
        intTitle = name + oldInt;
        String oldFloat = fltTitle;
        fltTitle = name + oldFloat;
        String oldStr = strTitle;
        strTitle = name + oldStr;
    }

    public static void changePath(String path) {
        String[] folders = path.split("/");
        String newFolders = "";
        for (int i = 0; i < folders.length; i++) {
            if (folders[i].equals(""))
                continue;
            newFolders += "\\" + folders[i];
        }
        String oldPath = new File("").getAbsolutePath();
        String newPath = oldPath + newFolders + "\\";
        generalPath = newPath;
        Path pathForOut = Paths.get(newPath);
        if (Files.notExists(pathForOut)) {
            new File(newPath).mkdirs();
        }
    }

    public static void saveToFile(boolean append, ArrayList<Data> collectedData) {
        List<String> processedIntList = UtilityParser.integerFinder(collectedData);
        if (processedIntList.isEmpty()) {
            System.out.println("Нет целочисленного типа");
        }
        else {
            try (FileWriter fileWriter = new FileWriter(generalPath + intTitle, append);
                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                for (int i = 0; i < processedIntList.size(); i++) {
                    bufferedWriter.write(processedIntList.get(i));
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            }
            catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            if (append) {
                Data allData = FileHelper.collectFromFile(generalPath + intTitle);
                processedIntList = allData.getRawArray();
            }
            if (shortStat) {
                System.out.println("Количество элементов в integers файле - " + processedIntList.size());
            }
            if (fullStat) {
                fullStatForInt(processedIntList);
            }
        }

        List<String> processedFloatList = UtilityParser.floatFinder(collectedData);
        if (processedFloatList.isEmpty()) {
            System.out.println("Нет вещественного типа");
        }
        else {
            try (FileWriter fileWriter = new FileWriter(generalPath + fltTitle, append);
                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                for (int i = 0; i < processedFloatList.size(); i++) {
                    bufferedWriter.write(processedFloatList.get(i));
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            }
            catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            if (append) {
                Data allData = FileHelper.collectFromFile(generalPath + fltTitle);
                processedFloatList = allData.getRawArray();
            }
            if (shortStat) {
                System.out.println("Количество элементов в floats файле - " + processedFloatList.size());
            }
            if (fullStat) {
                fullStatForFloat(processedFloatList);
            }
        }

        List<String> processedStringList = UtilityParser.stringFinder(collectedData);
        if (processedStringList.isEmpty()) {
            System.out.println("Нет строкового типа");
        }
        else {
            try (FileWriter fileWriter = new FileWriter(generalPath + strTitle, append);
                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                for (int i = 0; i < processedStringList.size(); i++) {
                    bufferedWriter.write(processedStringList.get(i));
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            }
            catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            if (append) {
                Data allData = FileHelper.collectFromFile(generalPath + strTitle);
                processedStringList = allData.getRawArray();
            }
            if (shortStat) {
                System.out.println("Количество элементов в strings файле - " + processedStringList.size());
            }
            if (fullStat) {
                fullStatForString(processedStringList);
            }
        }
    }

    private static void fullStatForInt(List<String> processedList){
        int n = processedList.size();
        Long min, max, sum;
        min = parseLong(processedList.get(0));
        max = parseLong(processedList.get(0));
        sum = 0L;
        float avg;
        for (int i = 0; i < processedList.size(); i++) {
            sum += parseLong(processedList.get(i));
            if (min > parseLong(processedList.get(i)))
                min = parseLong(processedList.get(i));
            if (max < parseLong(processedList.get(i)))
                max = parseLong(processedList.get(i));
        }
        avg = sum/n;
        System.out.println("-------------------------------------------------");
        System.out.println("Количество элементов в integers файле = " + n);
        System.out.println("Минимальный элемент в integers файле = " + min);
        System.out.println("Максимальный элемент в integers файле = " + max);
        System.out.println("Сумма элементов в integers файле = " + sum);
        System.out.println("Среднее значение элементов в integers файле = " + avg);
        System.out.println("-------------------------------------------------");
    }

    private static void fullStatForFloat(List<String> processedList){
        int n = processedList.size();
        Float min, max, sum;
        min = Float.valueOf(processedList.get(0));
        max = Float.valueOf(processedList.get(0));
        sum = 0f;
        float avg;
        for (int i = 0; i < processedList.size(); i++) {
            sum += Float.valueOf(processedList.get(i));
            if (min > Float.valueOf(processedList.get(i)))
                min = Float.valueOf(processedList.get(i));
            if (max < Float.valueOf(processedList.get(i)))
                max = Float.valueOf(processedList.get(i));
        }
        avg = sum/n;
        System.out.println("-------------------------------------------------");
        System.out.println("Количество элементов в floats файле = " + n);
        System.out.println("Минимальный элемент в floats файле = " + min);
        System.out.println("Максимальный элемент в floats файле = " + max);
        System.out.println("Сумма элементов в floats файле = " + sum);
        System.out.println("Среднее значение элементов в floats файле = " + avg);
        System.out.println("-------------------------------------------------");
    }
    private static void fullStatForString(List<String> processedList){
        int n = processedList.size();
        int min = processedList.get(0).length(), max = processedList.get(0).length();
        for (int i = 0; i < processedList.size(); i++) {
            if (min > processedList.get(i).length())
                min = processedList.get(i).length();
            if (max < processedList.get(i).length())
                max = processedList.get(i).length();
        }
        System.out.println("-------------------------------------------------");
        System.out.println("Количество элементов в strings файле = " + n);
        System.out.println("Длина самой короткой строки = " + min);
        System.out.println("Длина самой длинной строки = " + max);
        System.out.println("-------------------------------------------------");
    }
}
