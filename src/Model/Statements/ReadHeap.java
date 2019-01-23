package Model.Statements;

import Model.Containers.IHeap;
import Model.Containers.MyIDictionary;
import Model.Exceptions.DivisionByZeroException;
import Model.Exceptions.MissingVariableException;
import Model.Expressions.Expr;

public class ReadHeap extends Expr {
    private String variableName;

    public ReadHeap(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public int eval(MyIDictionary<String, Integer> table, IHeap<Integer, Integer> heapMemory) throws MissingVariableException, DivisionByZeroException {
        Integer addressInHeap = table.lookUp(variableName);
        return heapMemory.lookUp(addressInHeap);
    }

    @Override
    public String toString() {
        return "readHeap: " + variableName;
    }
}
