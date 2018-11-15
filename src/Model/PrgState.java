package Model;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Integer> symTable;
    private MyIList<Integer> out;
    private IDictionaryWithoutKey<Integer, Pair<String, BufferedReader>> fileTable;
    private IDictionaryWithoutKey<Integer, Integer> heapMemory;
    private IStmt originalProgram; // copy of the program (duplicate)

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String, Integer> symTable, MyIList<Integer> out, IStmt originalProgram) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = originalProgram;
        this.exeStack.push(originalProgram);
        this.fileTable = new FileTable();
        this.heapMemory = new Heap();
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

    public MyIList<Integer> getOut() {
        return out;
    }

    public IDictionaryWithoutKey<Integer, Pair<String, BufferedReader>> getFileTable() { return fileTable; }

    public IDictionaryWithoutKey<Integer, Integer> getHeapMemory() { return heapMemory; }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public void closeAllFiles() {
        // Should we also remove values from symTable?
        Map<Integer, Pair<String, BufferedReader>> fT = new HashMap<>();
        fileTable.getContent().forEach(fT::put);
        fT.forEach((descriptor, pair) -> {
            try {
                new CloseFile(new ConstExpr(descriptor)).execute(this);
            } catch (MissingVariableException | DivisionByZeroException | IOException e) {
                e.printStackTrace();
            }
        });
    }
}
