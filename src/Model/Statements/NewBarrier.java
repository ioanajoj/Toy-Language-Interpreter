package Model.Statements;

import Model.Exceptions.DivisionByZeroException;
import Model.Exceptions.MissingVariableException;
import Model.Expressions.Expr;
import Model.PrgState;


public class NewBarrier implements IStmt {
    private String variable;
    private Expr expr;
    private static java.util.concurrent.locks.Lock lock = new java.util.concurrent.locks.ReentrantLock();

    public NewBarrier(String variable, Expr expr) {
        this.variable = variable;
        this.expr = expr;
    }

    @Override
    public PrgState execute(PrgState state) throws DivisionByZeroException, MissingVariableException {
        lock.lock();
        int eval = expr.eval(state.getSymTable(), state.getHeapMemory());
        int index = state.getCyclicBarrier().put(eval);
        state.getSymTable().put(variable, index);
        lock.unlock();
        return null;
    }

    @Override
    public String toString() {
        return "NewBarrier(" + variable + "," + expr.toString() + ")";
    }
}
