package Model.Statements;

import Model.*;
import Model.Containers.IList;
import Model.Exceptions.DivisionByZeroException;
import Model.Exceptions.MissingVariableException;
import Model.Expressions.Expr;

public class PrintStmt implements IStmt {
    private Expr expr;

    public PrintStmt(Expr expr) {
        this.expr = expr;
    }

    @Override
    public PrgState execute(PrgState state) throws MissingVariableException, DivisionByZeroException {
        IList<Integer> output = state.getOut();
        output.add(expr.eval(state.getSymTable(), state.getHeapMemory()));
        return null;
    }

    @Override
    public String toString() {
        return "print " + expr;
    }
}
