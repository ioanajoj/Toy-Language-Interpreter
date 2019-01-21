package Model.Statements;

import Model.*;
import Model.Containers.IHeap;
import Model.Exceptions.DivisionByZeroException;
import Model.Exceptions.MissingVariableException;
import Model.Expressions.Expr;

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
        IHeap<Integer, Integer> heapMemory = state.getHeapMemory();
        Integer address = heapMemory.put(value);
        state.getSymTable().put(variable, address);
        return null;
    }

    @Override
    public String toString() {
        return variable + " <- " + expression;
    }
}
