package Model.Statements;

import Model.*;
import Model.Containers.MyStack;

public class ForkStmt implements IStmt {
    private IStmt inForkStmt;

    public ForkStmt(IStmt inForkStmt) {
        this.inForkStmt = inForkStmt;
    }

    @Override
    public PrgState execute(PrgState state) {
        return new PrgState("A child was born", new MyStack<>(), state.getSymTableCopy(), state.getOut(), state.getFileTable(), state.getHeapMemory(), state.getLockTable(), state.getCyclicBarrier(), inForkStmt);
    }

    @Override
    public String toString() {
        return "fork() {\n" + inForkStmt.toString() + "\n}";
    }
}
