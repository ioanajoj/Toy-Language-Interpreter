package Model.Statements;

import Model.Containers.IDictionary;
import Model.PrgState;

public class NewLock implements IStmt {
    private static java.util.concurrent.locks.Lock lock = new java.util.concurrent.locks.ReentrantLock();
    private String variable;

    public NewLock(String variable) {
        this.variable = variable;
    }

    @Override
    public PrgState execute(PrgState state) {
        lock.lock();
        int index = state.getLockTable().put(-1);
        IDictionary<String, Integer> symTable = state.getSymTable();
        symTable.put(variable, index);
        lock.unlock();
        return null;
    }

    @Override
    public String toString() {
        return "NewLock(" + variable + ")";
    }
}
