package Model;

public class VarExpr extends Expr{
    private String id;

    public VarExpr(String id) {
        this.id = id;
    }

    @Override
    int eval(MyIDictionary<String, Integer> table, IDictionaryWithoutKey<Integer, Integer> heapMemory) throws MissingVariableException{
        return table.lookUp(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
