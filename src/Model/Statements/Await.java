package Model.Statements;

import Model.PrgState;
import javafx.util.Pair;

import java.util.List;

public class Await implements IStmt {
    private String variable;
    private static java.util.concurrent.locks.Lock lock = new java.util.concurrent.locks.ReentrantLock();

    public Await(String variable) {
        this.variable = variable;
    }

    @Override
    public PrgState execute(PrgState state) {
        lock.lock();
        if(state.getSymTable().get(variable) != null) {
            int index = state.getSymTable().get(variable);
            if (state.getCyclicBarrier().get(index) != null) {
                Pair<Integer, List<Integer>> pair = state.getCyclicBarrier().get(index);
                int lengthL = pair.getValue().size();
                int n = pair.getKey();
                if(n > lengthL) {
                    if(pair.getValue().contains(state.getId()))
                        state.getExeStack().push(this);
                    else {
                        pair.getValue().add(state.getId());
                        state.getExeStack().push(this);
                    }
                }
            }
        }
        lock.unlock();
        return null;
    }

    @Override
    public String toString() {
        return "Await(" + variable + ')';
    }
}
