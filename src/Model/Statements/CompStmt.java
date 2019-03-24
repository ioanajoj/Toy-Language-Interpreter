package Model.Statements;

import Model.*;
import Model.Containers.IStack;
import Model.Exceptions.DivisionByZeroException;
import Model.Exceptions.InfiniteLoopException;
import Model.Exceptions.MissingVariableException;

import java.io.IOException;

public class CompStmt implements IStmt {
    private IStmt first;
    private IStmt second;

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public PrgState execute(PrgState state) throws IOException, MissingVariableException, DivisionByZeroException, InfiniteLoopException {
        IStack<IStmt> stack = state.getExeStack();
        stack.push(second);
        return first.execute(state);
    }

    @Override
    public String toString() {
        return first + "\n" + second;
    }
}
