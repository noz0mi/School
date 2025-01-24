package org.example;

public final class Command {

    private Command() {}


    public static String parsing (String commandName) {
        switch (commandName) {
            case "-a" :
                System.out.println("command a");
                return "A";
            case "-s" :
                System.out.println("command s");
                return "S";
            case "-f" :
                System.out.println("command f");
                return "F";
            default :
                System.out.println("Такой команды нет, либо неверно набрана");
                return null;
        }
    }

}
