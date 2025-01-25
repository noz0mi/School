package org.example;

public final class Command {

    private Command() {}


    public static String parsing (String commandName) {
        switch (commandName) {
            case "-a" :
                return "A";
            case "-s" :
                return "S";
            case "-f" :
                return "F";
            default :
                System.out.println("Такой команды нет, либо неверно набрана");
                return null;
        }
    }

}
