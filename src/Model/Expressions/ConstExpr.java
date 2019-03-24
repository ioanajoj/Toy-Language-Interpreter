package Model.Expressions;

import Model.Containers.IDictionary;
import Model.Containers.IHeap;

public class ConstExpr extends Expr {
    private int number;

    public ConstExpr(int number) {
        this.number = number;
    }

    @Override
    public int eval(IDictionary<String, Integer> table, IHeap<Integer, Integer> heapMemory) {
        return number;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }
}
