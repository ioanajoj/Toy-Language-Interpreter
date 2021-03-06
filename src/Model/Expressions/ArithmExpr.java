package Model.Expressions;

import Model.Exceptions.DivisionByZeroException;
import Model.Containers.IHeap;
import Model.Exceptions.MissingVariableException;
import Model.Containers.IDictionary;

public class ArithmExpr extends Expr {
    private Expr expr1;
    private Expr expr2;
    private char operator;

    public ArithmExpr(char op, Expr expr1, Expr expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.operator = op;
    }

    @Override
    public int eval(IDictionary<String, Integer> table, IHeap<Integer, Integer> heapMemory) throws MissingVariableException, DivisionByZeroException {
        // Switch Enum
        if(operator == '+') return (expr1.eval(table,heapMemory) + expr2.eval(table,heapMemory));
        else if(operator == '-') return (expr1.eval(table,heapMemory) - expr2.eval(table,heapMemory));
        else if(operator == '*') return (expr1.eval(table,heapMemory) * expr2.eval(table,heapMemory));
        // DIVISION BY ZERO EXCEPTION
        else if(operator == '/') {
            if(expr2.eval(table,heapMemory) == 0)
                throw new DivisionByZeroException("A division by zero has occurred");
            return (expr1.eval(table,heapMemory) / expr2.eval(table,heapMemory));
        }
        throw new RuntimeException();
    }

    @Override
    public String toString() {
        return expr1 + " " + operator + " " + expr2;
    }
}
