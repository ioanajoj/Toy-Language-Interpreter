package Model;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Integer> symTable;
    private MyIList<Integer> out;
    private IStmt originalProgram;
    private FileTable fileTable;
    private Heap heapMemory;

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

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyIDictionary<String, Integer> getSymTable() {
        return symTable;
    }

    public MyIList<Integer> getOut() {
        return out;
    }

    public FileTable getFileTable() { return fileTable; }

    public Heap getHeapMemory() { return heapMemory; }

    public void setFileTable(FileTable fileTable) {
        this.fileTable = fileTable;
    }

    public IStmt getOriginalProgram() {

        return originalProgram;
    }
}
