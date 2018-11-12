package Model;

public class AssignStmt implements IStmt{
    private String id;
    private Expr expr;

    public AssignStmt(String id, Expr expr) {
        this.id = id;
        this.expr = expr;
    }

    @Override
    public String toString() {
        return id + " = " + expr;
    }

    @Override
    public PrgState execute(PrgState state) throws MissingVariableException, DivisionByZeroException{
        MyIDictionary<String,Integer> symTable= state.getSymTable();
        int val = expr.eval(symTable,state.getHeapMemory());
        if(symTable.get(id) != null)
            symTable.replace(id, val);
        else symTable.put(id,val);
        return state;
    }
}
