package Repository;

import Model.IStmt;
import Model.MyIStack;
import Model.PrgState;

import java.io.*;
import java.util.ArrayList;

public class ListRepository implements IRepository {
    private ArrayList<PrgState> programs;
    private String fileName;
    private int index;

    public ListRepository(String fileName) {
        this.programs = new ArrayList<>();
        this.fileName = fileName;
        this.index = 0;
    }

    public void addProgram(PrgState prg) {
        this.programs.add(prg);
    }

    @Override
    public PrgState getCurrentPrg() {
        return this.programs.get(index);
    }

    @Override
    public int getCurrentIndex() {
        return index;
    }

    @Override
    public void addPrgState(PrgState prg) {
        this.programs.add(prg);
    }

    @Override
    public void logPrgState(int index) {
        PrintWriter printW;
        try {
            printW = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
            printW.println("Program State index:" + index);
            printW.println("Execution Stack:");
            PrgState prg = this.programs.get(index);
            MyIStack<IStmt> execStack = prg.getExeStack();
            if(!execStack.toString().equals(""))
                printW.println(execStack);

            printW.println("Symbol Table:");
            printW.println(prg.getSymTable());

            printW.println("Output:");
            printW.println(prg.getOut());

            printW.println("File Table:");
            printW.println(prg.getFileTable());

            printW.println("Heap Memory:");
            printW.println(prg.getHeapMemory());
            printW.println();
            printW.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Log error:" + ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearFile() {
        try {
            PrintWriter printW = new PrintWriter(new BufferedWriter(new FileWriter(fileName, false)));
            printW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}