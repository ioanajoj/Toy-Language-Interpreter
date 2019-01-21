package Model.Statements;

import Model.Expressions.BooleanExpr;
import Model.Expressions.Expr;
import Model.Expressions.VarExpr;
import Model.PrgState;

public class For implements IStmt {
    private Expr exp1;
    private Expr exp2;
    private Expr exp3;
    private IStmt stmt;

    public For(Expr exp1, Expr exp2, Expr exp3, IStmt stmt) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) {
        AssignStmt assign = new AssignStmt("v", exp1);
        CompStmt compStmt = new CompStmt(stmt, new AssignStmt("v", exp3));
        WhileStmt whileS = new WhileStmt(new BooleanExpr("<", new VarExpr("v"), exp2), compStmt);
        state.getExeStack().push(whileS);
        state.getExeStack().push(assign);
        return null;
    }

    @Override
    public String toString() {
        return "For(v=" + exp1 +
                ";v<" + exp2 +
                ";v= " + exp3 +
                ") {\n" + stmt +
                "\n}";
    }
}
