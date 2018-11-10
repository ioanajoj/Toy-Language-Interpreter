package Model;

public class NewStmt implements IStmt {
    private String variable;
    private Expr expression;

    public NewStmt(String variable, Expr expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MissingVariableException, DivisionByZeroException {
        Integer value = expression.eval(state.getSymTable(),state.getHeapMemory());
        IDictionaryWithoutKey<Integer, Integer> heapMemory = state.getHeapMemory();
        Integer address = heapMemory.put(value);
        state.getSymTable().put(variable, address);
        return state;
    }

    @Override
    public String toString() {
        return variable + " <- " + expression;
    }

    @Override
    public IStmt duplicate() {
        return new NewStmt(variable, expression);
    }
}
