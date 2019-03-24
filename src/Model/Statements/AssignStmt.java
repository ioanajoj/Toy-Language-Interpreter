package Model.Statements;

import Model.*;
import Model.Containers.IDictionary;
import Model.Exceptions.DivisionByZeroException;
import Model.Exceptions.MissingVariableException;
import Model.Expressions.Expr;

public class AssignStmt implements IStmt {
    private String id;
    private Expr expr;

    public AssignStmt(String id, Expr expr) {
        this.id = id;
        this.expr = expr;
    }

    @Override
    public String toString() {
        return id + " = " + expr;
    }

    @Override
    public PrgState execute(PrgState state) throws MissingVariableException, DivisionByZeroException {
        IDictionary<String,Integer> symTable= state.getSymTable();
        int val = expr.eval(symTable,state.getHeapMemory());
        if(symTable.get(id) != null)
            symTable.replace(id, val);
        else symTable.put(id,val);
        return null;
    }
}
