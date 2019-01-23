package GUI;

import Controller.Controller;

public class RunExampleCommand {
    private String name;
    private Controller controller;
    private String description;

    RunExampleCommand(String name, String description, Controller controller) {
        this.name = name;
        this.description = description;
        this.controller = controller;
    }

    public void execute() {
        try {
            controller.evaluateProgram();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    String getName() { return name; }
    String getDescription() {
        return this.description;
    }
    Controller getController() { return this.controller; }
}
