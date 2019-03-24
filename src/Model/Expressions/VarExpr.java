package Model.Expressions;

import Model.Containers.IHeap;
import Model.Exceptions.MissingVariableException;
import Model.Containers.IDictionary;

public class VarExpr extends Expr{
    private String id;

    public VarExpr(String id) {
        this.id = id;
    }

    @Override
    public int eval(IDictionary<String, Integer> table, IHeap<Integer, Integer> heapMemory) throws MissingVariableException {
        return table.lookUp(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
