package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class UtilityParser {
    private UtilityParser(){}

    public static ArrayList<Data> parsingFiles(String line) {
        String[] parsedLine = line.split(" ");
        ArrayList<Data> collectedData = new ArrayList<>();
        for (String s:parsedLine) {
            if (s.contains(".txt")) {
                String path = new File("").getAbsolutePath() + '\\' + s;
                collectedData.add(FileHelper.collectFromFile(path));
            }
        }
        return collectedData;
    }

    public static void parsingCommands(String line) {
        String[] parsedLine = line.split(" ");
        ArrayList<String> flags = new ArrayList<>();
        for(int i = 0; i < parsedLine.length; i++) {
            if ((parsedLine[i].equals("-p"))&&(parsedLine[i+1].matches("([a-zA-Z0-9-_]+)"))) {
                FileHelper.changeName(parsedLine[i+1]);
                continue;
            }
            if ((parsedLine[i].equals("-o"))&&(parsedLine[i+1].matches("((/.*/?)+)"))) {
                FileHelper.changePath(parsedLine[i+1]);
                continue;
            }
            if (parsedLine[i].startsWith("-")) {
                flags.add(Command.parsing(parsedLine[i]));
            }
        }
        for (int i = 0; i < flags.size(); i++) {
            switch (flags.get(i)){
                case "A":
                    FileHelper.appendFlag = true;
                    break;
                case "S":
                    FileHelper.shortStat = true;
                    break;
                case "F":
                    FileHelper.fullStat = true;
                    break;

            }
        }
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
