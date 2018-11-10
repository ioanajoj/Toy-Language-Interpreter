package Model;

public class ReadHeap extends Expr {
    private String variableName;

    public ReadHeap(String variableName) {
        this.variableName = variableName;
    }

    @Override
    int eval(MyIDictionary<String, Integer> table, IDictionaryWithoutKey<Integer, Integer> heapMemory) throws MissingVariableException, DivisionByZeroException {
        Integer addressInHeap = table.get(variableName);
        return heapMemory.get(addressInHeap);
    }

    @Override
    Expr duplicate() {
        return null;
    }

    @Override
    public String toString() {
        return "readHeap: " + variableName;
    }
}
