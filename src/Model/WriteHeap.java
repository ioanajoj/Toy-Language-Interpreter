package Model;

public class WriteHeap implements IStmt {
    private String variableName;
    private Expr expression;

    public WriteHeap(String variableName, Expr expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MissingVariableException, DivisionByZeroException {
        Integer address = state.getSymTable().lookUp(variableName);
        Integer value = expression.eval(state.getSymTable(), state.getHeapMemory());
        state.getHeapMemory().replace(address, value);
        return state;
    }

    @Override
    public String toString() {
        return "writeHeap: " + variableName + "<-" + expression;
    }
}
