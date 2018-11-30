package Model;

public class WhileStmt implements IStmt {
    private Expr condition;
    private IStmt statement;

    public WhileStmt(Expr condition, IStmt statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws InfiniteLoopException, MissingVariableException, DivisionByZeroException {
        if(condition.getClass().getName().equals("Model.ConstExpr"))
            throw new InfiniteLoopException("The while loop is evaluated on a constant expression");
        if(condition.eval(state.getSymTable(), state.getHeapMemory()) != 0) {
            state.getExeStack().push(this);
            state.getExeStack().push(statement);
        }
        return null;
    }

    @Override
    public String toString() {
        return "while(" +condition + ") {" + statement + '}';
    }
}
