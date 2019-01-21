package Model.Statements;

import Model.Exceptions.DivisionByZeroException;
import Model.Exceptions.InfiniteLoopException;
import Model.Exceptions.MissingVariableException;
import Model.PrgState;

import java.io.IOException;

public interface IStmt {
    PrgState execute(PrgState state) throws InfiniteLoopException, MissingVariableException, DivisionByZeroException, IOException;
    String toString();
}
