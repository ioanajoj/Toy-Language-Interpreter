package View;

import java.util.HashMap;
import java.util.Scanner;

class TextMenu {
    private  HashMap<String, Command> commands;

    TextMenu() {
        commands = new HashMap<>();
    }

    void addCommand(Command com) {
        commands.put(com.getKey(), com);
    }

    private void printMenu() {
        for(Command com: commands.values()) {
            String line = com.getKey();
            line += ":\n";
            line += com.getDescription();
            System.out.println(line);
        }
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
