package Model.Expressions;

import Model.Containers.IHeap;
import Model.Containers.MyIDictionary;

public class ConstExpr extends Expr {
    private int number;

    public ConstExpr(int number) {
        this.number = number;
    }

    @Override
    public int eval(MyIDictionary<String, Integer> table, IHeap<Integer, Integer> heapMemory) {
        return number;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }
}
