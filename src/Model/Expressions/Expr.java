package Model.Expressions;

import Model.Exceptions.DivisionByZeroException;
import Model.Containers.IHeap;
import Model.Exceptions.MissingVariableException;
import Model.Containers.MyIDictionary;

public abstract class Expr {
    public abstract int eval(MyIDictionary<String, Integer> table, IHeap<Integer, Integer> heapMemory) throws MissingVariableException, DivisionByZeroException;

    @Override
    public abstract String toString();
}
