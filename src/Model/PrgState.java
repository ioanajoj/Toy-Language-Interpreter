package Model;

import Model.Containers.*;
import Model.Containers.MDictionary;
import Model.Containers.IDictionary;
import Model.Containers.IList;
import Model.Containers.IStack;
import Model.Exceptions.DivisionByZeroException;
import Model.Exceptions.InfiniteLoopException;
import Model.Exceptions.MissingVariableException;
import Model.Statements.IStmt;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class PrgState {
    private String name;
    private int id;
    private IStack<IStmt> exeStack;
    private IDictionary<String, Integer> symTable;
    private IList<Integer> out;
    private IFileTable<Integer, Pair<String, BufferedReader>> fileTable;
    private IHeap<Integer, Integer> heapMemory;
    private ILockTable<Integer, Integer> lockTable;
    private ICyclicBarrier<Integer, Pair<Integer, List<Integer>>> cyclicBarrier;

    public PrgState(String name, IStack<IStmt> exeStack, IDictionary<String, Integer> symTable, IList<Integer> out, IFileTable<Integer, Pair<String, BufferedReader>> fileTable, IHeap<Integer, Integer> heapMemory, ILockTable<Integer, Integer> lockTable, ICyclicBarrier<Integer, Pair<Integer, List<Integer>>> cyclicBarrier, IStmt originalProgram) {
        System.out.println("New prg with id = " + id);
        this.name = name;
        this.exeStack = exeStack;
        this.exeStack.push(originalProgram);
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heapMemory = heapMemory;
        this.lockTable = lockTable;
        this.cyclicBarrier = cyclicBarrier;
    }

    public PrgState oneStep() throws IOException, MissingVariableException, DivisionByZeroException, InfiniteLoopException {
        IStmt currentStmt = exeStack.pop();
        return currentStmt.execute(this);
    }

    public void closeAllFiles() {
        fileTable.getContent().forEach((descriptor,pair) -> {
            try {
                pair.getValue().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fileTable.reset();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isCompleted() {
        return this.exeStack.isEmpty();
    }

    public IStack<IStmt> getExeStack() {
        return exeStack;
    }

    public IDictionary<String, Integer> getSymTable() {
        return symTable;
    }

    public IDictionary<String, Integer> getSymTableCopy() {
        IDictionary<String, Integer> newSymTable = new MDictionary<>();
        this.symTable.keys().forEach(k -> newSymTable.put(k,symTable.get(k)));
        return newSymTable;
    }

    public IList<Integer> getOut() {
        return out;
    }

    public IFileTable<Integer, Pair<String, BufferedReader>> getFileTable() { return fileTable; }

    public IHeap<Integer, Integer> getHeapMemory() { return heapMemory; }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getName() { return this.name; }

    public ILockTable<Integer, Integer> getLockTable() {
        return lockTable;
    }

    public ICyclicBarrier<Integer, Pair<Integer, List<Integer>>> getCyclicBarrier() { return cyclicBarrier; }
}
