package Model;

public class ReadHeap extends Expr {
    // private VarExpr variableName;
    private String variableName;

    public ReadHeap(String variableName) {
        this.variableName = variableName;
    }

    @Override
    int eval(MyIDictionary<String, Integer> table, IDictionaryWithoutKey<Integer, Integer> heapMemory) throws MissingVariableException, DivisionByZeroException {
        Integer addressInHeap = table.lookUp(variableName);
        return heapMemory.lookUp(addressInHeap);
    }

    @Override
    public String toString() {
        return "readHeap: " + variableName;
    }
}
