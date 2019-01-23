package Model.Statements;

import Model.Exceptions.DivisionByZeroException;
import Model.Exceptions.MissingVariableException;
import Model.Expressions.Expr;
import Model.PrgState;

public class Switch implements IStmt {
    private Expr expr;
    private Expr expr1;
    private IStmt stmt1;
    private Expr expr2;
    private IStmt stmt2;
    private IStmt stmt3;

    public Switch(Expr expr, Expr expr1, IStmt stmt1, Expr expr2, IStmt stmt2, IStmt stmt3) {
        this.expr = expr;
        this.expr1 = expr1;
        this.stmt1 = stmt1;
        this.expr2 = expr2;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    @Override
    public PrgState execute(PrgState state) throws DivisionByZeroException, MissingVariableException {
        if(expr.eval(state.getSymTable(), state.getHeapMemory()) == expr1.eval(state.getSymTable(), state.getHeapMemory()))
            state.getExeStack().push(stmt1);
        else if(expr.eval(state.getSymTable(), state.getHeapMemory()) == expr2.eval(state.getSymTable(), state.getHeapMemory()))
            state.getExeStack().push(stmt2);
        else state.getExeStack().push(stmt3);
        return null;
    }

    @Override
    public String toString() {
        return "Switch(" + expr + ")\n" +
                "case " + expr1 + ":" + stmt1 + "\n" +
                "case " + expr2 + ":" + stmt2 + "\n" +
                "default: " + stmt3;
    }
}
