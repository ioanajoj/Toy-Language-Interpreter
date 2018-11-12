package Model;

public abstract class Expr {
    abstract int eval(MyIDictionary<String, Integer> table, IDictionaryWithoutKey<Integer, Integer> heapMemory) throws MissingVariableException, DivisionByZeroException;

    @Override
    public abstract String toString();
}
