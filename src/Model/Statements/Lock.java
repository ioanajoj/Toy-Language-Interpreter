package Model.Statements;

import Model.PrgState;

public class Lock implements IStmt {
    private static java.util.concurrent.locks.Lock lock = new java.util.concurrent.locks.ReentrantLock();
    private String variable;

    public Lock(String variable) {
        this.variable = variable;
    }

    @Override
    public PrgState execute(PrgState state) {
        lock.lock();
        int index = state.getSymTable().get(variable);
        System.out.println(": variable " + variable + " index " + index + " in prg " + state.getId() + " locked.");
        if(state.getLockTable().get(index) == -1)
            state.getLockTable().replace(index,state.getId());
        else {
            state.getExeStack().push(this);
        }
        lock.unlock();
        return null;
    }

    @Override
    public String toString() {
        return "Lock(" + variable + ")";
    }
}
