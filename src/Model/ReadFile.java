package Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadFile implements IStmt {
    private Expr varFile;
    private String varName;

    public ReadFile(Expr fileName, String varName) {
        this.varFile = fileName;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws MissingVariableException, DivisionByZeroException, IOException {
        int fileDesc = varFile.eval(state.getSymTable(),state.getHeapMemory());
        try {
            BufferedReader bufferR = state.getFileTable().lookUp(fileDesc).getValue();
            String line = bufferR.readLine();
            int value;
            if(line == null || line.equals("")) value = 0;
            else value = Integer.parseInt(line);
            state.getSymTable().put(varName, value);
        } catch (MissingVariableException ex) {
            throw new FileNotFoundException("The file that you are trying to read does not exist.");
        }
        return state;
    }

    @Override
    public IStmt duplicate() {
        return null;
    }

    @Override
    public String toString() {
        return "Read from file " + varFile + " variable " + varName;
    }
}
