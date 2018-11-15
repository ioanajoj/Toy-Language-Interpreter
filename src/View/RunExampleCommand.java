package View;

import Controller.Controller;
import Model.DivisionByZeroException;
import Model.InfiniteLoopException;
import Model.MissingVariableException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

public class RunExampleCommand extends Command {
    private Controller controller;

    public RunExampleCommand(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.allStepEval();
        } catch (MissingVariableException | DivisionByZeroException | FileNotFoundException | InfiniteLoopException | FileAlreadyExistsException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
