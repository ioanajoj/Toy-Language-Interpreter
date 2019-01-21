package CUI;

import java.util.LinkedHashMap;
import java.util.Scanner;

class TextMenu {
    private LinkedHashMap<String, Command> commands;

    TextMenu() {
        commands = new LinkedHashMap<>();
    }

    void addCommand(Command com) {
        commands.put(com.getKey(), com);
    }

    private void printMenu() {
        commands.forEach((line, command)-> {
            line += ":\n";
            line += command.getDescription();
            System.out.println(line);
        });
    }

    void show() {
        Scanner scanner = new Scanner(System.in);
        String key;
        do {
            printMenu();
            System.out.println("Input option: ");
            key = scanner.nextLine();
            Command com = commands.get(key);
            if(com == null) {
                System.out.println("Invalid option");
                continue;
            }
            com.execute();
        }while(!key.equals("exit"));
    }
}
