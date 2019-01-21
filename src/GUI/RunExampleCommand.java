package GUI;

import Controller.Controller;

public class RunExampleCommand {
    private String name;
    private Controller controller;
    private String description;

    public RunExampleCommand(String name, String description, Controller controller) {
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

    public String getName() { return name; }
    public String getDescription() {
        return this.description;
    }
    public Controller getController() { return this.controller; }
}
