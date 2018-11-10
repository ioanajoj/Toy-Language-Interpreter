package Model;

import java.io.IOException;

public interface IStmt {
    String toString();
    PrgState execute(PrgState state) throws MissingVariableException, DivisionByZeroException, IOException;
    IStmt duplicate();
}
