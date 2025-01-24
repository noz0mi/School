package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

public class Main {

    public static String generalPath = "";
    public static String intTitle = "integers.txt";
    public static String strTitle = "strings.txt";
    public static String fltTitle = "floats.txt";
    public static boolean appendFlag = false;
    public static boolean shortStat = false;
    public static boolean fullStat = false;
    public static int amountOfInt;
    public static int amountOfFloat;
    public static int amountOfString;
    public static void main(String[] args) {

        String line = "-f in1.txt in2.txt";
        ArrayList<Data> collected = parsingFiles(line);
        parsingCommands(line);
        saveToFile(appendFlag, collected);

    }

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
            System.out.println("File has been read");
            return data;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static ArrayList<Data> parsingFiles(String line) {
        String[] parsedLine = line.split(" ");
        ArrayList<Data> collectedData = new ArrayList<>();
        for (String s:parsedLine) {
            if (s.contains(".txt")) {
                String path = new File("").getAbsolutePath() + '\\' + s;
                collectedData.add(collectFromFile(path));
            }
        }
        return collectedData;
    }

    public static ArrayList<String> parsingCommands(String line) {
        String[] parsedLine = line.split(" ");
        ArrayList<String> flags = new ArrayList<>();
        for(int i = 0; i < parsedLine.length; i++) {
            if ((parsedLine[i].equals("-p"))&&(parsedLine[i+1].matches("([a-zA-Z0-9-_]+)"))) {
                changeName(parsedLine[i+1]);
                continue;
            }
            if ((parsedLine[i].equals("-o"))&&(parsedLine[i+1].matches("((/.*/?)+)"))) {
                changePath(parsedLine[i+1]);
                continue;
            }
            if (parsedLine[i].startsWith("-")) {
                Command command = new Command();
                flags.add(command.parsing(parsedLine[i]));
            }
        }
        for (int i = 0; i < flags.size(); i++) {
            switch (flags.get(i)){
                case "A":
                    appendFlag = true;
                    break;
                case "S":
                    shortStat = true;
                    break;
                case "F":
                    fullStat = true;
                    break;

            }
        }
        return flags;
    }

    public static void changeName(String name) {
        String newName = name;
        String oldInt = intTitle;
        intTitle = newName + oldInt;
        String oldFloat = fltTitle;
        fltTitle = newName + oldFloat;
        String oldStr = strTitle;
        strTitle = newName + oldStr;
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
        String newPath = oldPath + newFolders + '\\';
        generalPath = newPath;
        Path pathForInt = Paths.get(newPath);
        if (Files.notExists(pathForInt)) {
            new File(newPath).mkdirs();
        }
    }

    public static void saveToFile(boolean append, ArrayList<Data> collectedData) {
        List<String> processedIntList = integerFinder(collectedData);
        if (processedIntList.isEmpty()) {
            System.out.println("Нет целочисленного типа");
        }
        else {
            amountOfInt = processedIntList.size();
            if (shortStat) {
                System.out.println("Количество элементов в integers файле - " + processedIntList.size());
            }
            if (fullStat) {
                fullStatForInt(processedIntList);
            }
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
        }

        List<String> processedFloatList = floatFinder(collectedData);
        if (processedFloatList.isEmpty()) {
            System.out.println("Нет вещественного типа");
        }
        else {
            amountOfFloat = processedFloatList.size();
            if (shortStat) {
                System.out.println("Количество элементов в floats файле - " + processedFloatList.size());
            }
            if (fullStat) {
                fullStatForFloat(processedFloatList);
            }
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
        }

        List<String> processedStringList = stringFinder(collectedData);
        if (processedStringList.isEmpty()) {
            System.out.println("Нет вещественного типа");
        }
        else {
            amountOfString = processedStringList.size();
            if (shortStat) {
                System.out.println("Количество элементов в strings файле - " + processedStringList.size());
            }
            if (fullStat) {
                fullStatForString(processedStringList);
            }
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
        }


        return;
    }

    public static void fullStatForInt(List<String> processedList){
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

    public static void fullStatForFloat(List<String> processedList){
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
    public static void fullStatForString(List<String> processedList){
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

    public static List<String> integerFinder (ArrayList<Data> collectedData) {
        ArrayList<Data> copiedCollectedData = new ArrayList<>(collectedData.size());
        for (Data data: collectedData){
            try {
                copiedCollectedData.add(data.clone());
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
        List<String> ints = new ArrayList<>();
        boolean flag = true;
        while(flag) {
            for (int i = 0; i < copiedCollectedData.size(); i++) {
                List<String> currentList = copiedCollectedData.get(i).getRawArray();
                if (currentList.isEmpty()){
                    copiedCollectedData.remove(i);
                    if(copiedCollectedData.isEmpty())
                        flag = false;
                    continue;
                }
                String variant = currentList.get(0);
                if (variant.matches("-?\\d+"))
                    ints.add(variant);
                copiedCollectedData.get(i).getRawArray().remove(0);
            }
        }
        return ints;
    }
    public static List<String> floatFinder (ArrayList<Data> collectedData)  {
        ArrayList<Data> copiedCollectedData = new ArrayList<>(collectedData.size());
        for (Data data: collectedData){
            try {
                copiedCollectedData.add(data.clone());
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
        List<String> floats = new ArrayList<>();
        boolean flag = true;
        while(flag) {
            for (int i = 0; i < copiedCollectedData.size(); i++) {
                List<String> currentList = copiedCollectedData.get(i).getRawArray();
                if (currentList.isEmpty()){
                    copiedCollectedData.remove(i);
                    if(copiedCollectedData.isEmpty())
                        flag = false;
                    continue;
                }
                String variant = currentList.get(0);
                if (variant.matches("(-?(\\d+\\.\\d+(([eE](\\+|-)\\d+)?)))"))
                    floats.add(variant);
                copiedCollectedData.get(i).getRawArray().remove(0);
            }
        }
        return floats;
    }
    public static List<String> stringFinder (ArrayList<Data> collectedData)  {
        ArrayList<Data> copiedCollectedData = new ArrayList<>(collectedData.size());
        for (Data data: collectedData){
            try {
                copiedCollectedData.add(data.clone());
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
        List<String> strings = new ArrayList<>();
        boolean flag = true;
        while(flag) {
            for (int i = 0; i < copiedCollectedData.size(); i++) {
                List<String> currentList = copiedCollectedData.get(i).getRawArray();
                if (currentList.isEmpty()){
                    copiedCollectedData.remove(i);
                    if(copiedCollectedData.isEmpty())
                        flag = false;
                    continue;
                }
                String variant = currentList.get(0);
                if (!((variant.matches("(-?(\\d+\\.\\d+(([eE](\\+|-)\\d+)?)))")) ||
                        (variant.matches("-?\\d+"))))
                    strings.add(variant);
                copiedCollectedData.get(i).getRawArray().remove(0);
            }
        }
        return strings;
    }
}
