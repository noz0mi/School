package org.example;

public class Command {
    private String name;

    public Command() {};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String parsing (String commandName) {
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
