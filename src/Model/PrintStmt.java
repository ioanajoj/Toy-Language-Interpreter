package Model;

public class PrintStmt implements IStmt{
    private Expr expr;

    public PrintStmt(Expr expr) {
        this.expr = expr;
    }

    @Override
    public PrgState execute(PrgState state) throws MissingVariableException, DivisionByZeroException {
        MyIList<Integer> output = state.getOut();
        output.add(expr.eval(state.getSymTable(), state.getHeapMemory()));
        return state;
    }

    @Override
    public String toString() {
        return "print " + expr;
    }

    @Override
    public IStmt duplicate() {
        return new PrintStmt(this.expr.duplicate());
    }
}
