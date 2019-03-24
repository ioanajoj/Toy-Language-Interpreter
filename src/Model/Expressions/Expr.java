package Model.Expressions;

import Model.Containers.IDictionary;
import Model.Exceptions.DivisionByZeroException;
import Model.Containers.IHeap;
import Model.Exceptions.MissingVariableException;

public abstract class Expr {
    public abstract int eval(IDictionary<String, Integer> table, IHeap<Integer, Integer> heapMemory) throws MissingVariableException, DivisionByZeroException;

    @Override
    public abstract String toString();
}
