package Model;

import java.io.IOException;

public class ForkStmt implements IStmt {
    private IStmt inForkStmt;

    public ForkStmt(IStmt inForkStmt) {
        this.inForkStmt = inForkStmt;
    }

    @Override
    public PrgState execute(PrgState state) throws InfiniteLoopException, MissingVariableException, DivisionByZeroException, IOException {
        return new PrgState(new MyStack<>(), state.getSymTableCopy(), state.getOut(), state.getFileTable(), state.getHeapMemory(), inForkStmt);
    }

    @Override
    public String toString() {
        return "Fork{" + inForkStmt.toString() + "}";
    }
}
