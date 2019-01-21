package Model;

import Model.Containers.*;
import Model.Exceptions.DivisionByZeroException;
import Model.Exceptions.InfiniteLoopException;
import Model.Exceptions.MissingVariableException;
import Model.Statements.IStmt;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;

public class PrgState {
    private String name;
    private int id;
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Integer> symTable;
    private MyIList<Integer> out;
    private IFileTable<Integer, Pair<String, BufferedReader>> fileTable;
    private IHeap<Integer, Integer> heapMemory;
    private ILockTable<Integer, Integer> lockTable;
    private IStmt originalProgram; // copy of the program (duplicate)

    public PrgState(String name, MyIStack<IStmt> exeStack, MyIDictionary<String, Integer> symTable, MyIList<Integer> out, IFileTable<Integer, Pair<String, BufferedReader>> fileTable, IHeap<Integer, Integer> heapMemory, ILockTable<Integer, Integer> lockTable, IStmt originalProgram) {
        System.out.println("New prg with id = " + id);
        this.name = name;
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = originalProgram;
        this.exeStack.push(originalProgram);
        this.fileTable = fileTable;
        this.heapMemory = heapMemory;
        this.lockTable = lockTable;
    }

    public PrgState oneStep() throws IOException, MissingVariableException, DivisionByZeroException, InfiniteLoopException {
        IStmt currentStmt = exeStack.pop();
        PrgState newPrg = currentStmt.execute(this);
        return newPrg;
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

    public boolean isCompleted() {
        return this.exeStack.isEmpty();
    }

    void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    void setSymTable(MyIDictionary<String, Integer> symTable) {
        this.symTable = symTable;
    }

    public void setOut(MyIList<Integer> out) {
        this.out = out;
    }

    public void setOriginalProgram(IStmt originalProgram) {
        this.originalProgram = originalProgram;
    }

    public void setFileTable(FileTable fileTable) {
        this.fileTable = fileTable;
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyIDictionary<String, Integer> getSymTable() {
        return symTable;
    }

    public MyIDictionary<String, Integer> getSymTableCopy() {
        MyIDictionary<String, Integer> newSymTable = new MyDictionary<>();
        this.symTable.keys().forEach(k -> newSymTable.put(k,symTable.get(k)));
        return newSymTable;
    }

    public MyIList<Integer> getOut() {
        return out;
    }

    public IFileTable<Integer, Pair<String, BufferedReader>> getFileTable() { return fileTable; }

    public IHeap<Integer, Integer> getHeapMemory() { return heapMemory; }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

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
}
