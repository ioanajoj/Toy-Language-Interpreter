package Model;

public class ConstExpr extends Expr {
    private int number;

    public ConstExpr(int number) {
        this.number = number;
    }

    @Override
    int eval(MyIDictionary<String, Integer> table, IDictionaryWithoutKey<Integer, Integer> heapMemory) {
        return number;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }
}
