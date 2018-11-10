package Model;

import java.io.IOException;

public class WriteHeap implements IStmt {
    private String variableName;
    private Expr expression;

    public WriteHeap(String variableName, Expr expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MissingVariableException, DivisionByZeroException, IOException {
        Integer address = state.getSymTable().get(variableName);
        Integer value = expression.eval(state.getSymTable(), state.getHeapMemory());
        state.getHeapMemory().replace(address, value);
        return state;
    }

    @Override
    public IStmt duplicate() {
        return null;
    }

    @Override
    public String toString() {
        return "writeHeap: " + variableName + "<-" + expression;
    }
}
