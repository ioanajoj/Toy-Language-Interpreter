package CUI;

import Controller.Controller;

public class RunExampleCommand extends Command {
    private Controller controller;

    public RunExampleCommand(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
//            controller.allStepEval();
            controller.evaluateProgram();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
