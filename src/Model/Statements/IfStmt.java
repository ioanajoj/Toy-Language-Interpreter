package Model.Statements;

import Model.*;
import Model.Containers.IDictionary;
import Model.Containers.IStack;
import Model.Exceptions.DivisionByZeroException;
import Model.Exceptions.MissingVariableException;
import Model.Expressions.Expr;

public class IfStmt implements IStmt {
    private Expr expr;
    private IStmt thenS;
    private IStmt elseS;

    public IfStmt(Expr expr, IStmt thenS, IStmt elseS) {
        this.expr = expr;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    @Override
    public PrgState execute(PrgState state) throws MissingVariableException, DivisionByZeroException {
        IDictionary<String, Integer> symTable = state.getSymTable();
        IStack<IStmt> exeStack = state.getExeStack();
        if(this.expr.eval(symTable,state.getHeapMemory()) != 0)
            exeStack.push(thenS);
        else exeStack.push(elseS);
        return null;
    }

    @Override
    public String toString() {
        return "IF " + expr +
                " THEN " + thenS +
                " ELSE " + elseS;
    }
}
