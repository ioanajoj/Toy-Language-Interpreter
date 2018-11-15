package Model;

public class BooleanExpr extends Expr {
    private String operator;
    private Expr left;
    private Expr right;

    public BooleanExpr(String operator, Expr left, Expr right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    int eval(MyIDictionary<String, Integer> table, IDictionaryWithoutKey<Integer, Integer> heapMemory) throws MissingVariableException, DivisionByZeroException {
        boolean result = false;
        if(operator.equals("<"))
            result = left.eval(table, heapMemory)<right.eval(table, heapMemory);
        if(operator.equals("<="))
            result = left.eval(table, heapMemory)<=right.eval(table, heapMemory);
        if(operator.equals(">"))
            result = left.eval(table, heapMemory)>right.eval(table, heapMemory);
        if(operator.equals(">="))
            result = left.eval(table, heapMemory)>=right.eval(table, heapMemory);
        if(operator.equals("=="))
            result = left.eval(table, heapMemory)==right.eval(table, heapMemory);
        if(result) return 1;
        return 0;
    }

    @Override
    public String toString() {
        return left.toString() + operator + right.toString() ;
    }
}
