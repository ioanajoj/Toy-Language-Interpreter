package Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CloseFile implements IStmt {
    private Expr fileDesc;

    public CloseFile(Expr fileDesc) {
        this.fileDesc = fileDesc;
    }

    @Override
    public PrgState execute(PrgState state) throws MissingVariableException, DivisionByZeroException, IOException {
        int value = fileDesc.eval(state.getSymTable(),state.getHeapMemory());
        try {
            BufferedReader bufferR = state.getFileTable().lookUp(value).getValue();
            bufferR.close();
            state.getFileTable().remove(value);
        }
        catch (MissingVariableException ex) {
            throw new FileNotFoundException("The file you are trying to close does not exist.");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return state;
    }

    @Override
    public IStmt duplicate() {
        return null;
    }

    @Override
    public String toString() {
        return "CloseFile " + fileDesc;
    }
}
