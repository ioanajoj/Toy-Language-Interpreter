package Model.Statements;

import Model.PrgState;

public class Unlock implements IStmt {
    private String variable;

    public Unlock(String variable) {
        this.variable = variable;
    }

    @Override
    public PrgState execute(PrgState state) {
        int index = state.getSymTable().get(variable);
        System.out.println(": variable " + variable + " index " + index + " in prg " + state.getId() + " unlocked.");
        if(state.getLockTable().get(index) == state.getId())
            state.getLockTable().replace(index, -1);
        return null;
    }

    @Override
    public String toString() {
        return "Unlock(" + variable + ")";
    }
}
