package org.example;


import java.util.ArrayList;


public class Main {



    public static void main(String[] args) {

        String line = "-s -o /some/newpath -a -p result_ in1.txt in2.txt in3.txt";
        ArrayList<Data> collected = UtilityParser.parsingFiles(line);
        UtilityParser.parsingCommands(line);
        FileHelper.saveToFile(FileHelper.appendFlag, collected);

    }

}
