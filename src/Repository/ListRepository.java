package Repository;

import Model.IStmt;
import Model.MyIStack;
import Model.PrgState;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListRepository implements IRepository {
    private ArrayList<PrgState> programs;
    private String fileName;
    private int index;
    private int id;

    public ListRepository(String fileName) {
        this.programs = new ArrayList<>();
        this.fileName = fileName;
        this.index = 0;
        this.id = 0;
    }

    public void addProgram(PrgState prg) {
        prg.setId(id);
        this.programs.add(prg);
        this.id++;
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
    public void logAll() {
        this.programs.forEach(this::logPrgState);
    }

    @Override
    public void logPrgState(PrgState prg) {
        PrintWriter printW;
        try {
            printW = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
            printW.println("*=========================*");
            printW.println();

            printW.println("Program State index:" + prg.getId());
            printW.println("Execution Stack:");
            if(!prg.getExeStack().toString().equals(""))
                printW.println(prg.getExeStack());

            printW.println("Symbol Table:");
            printW.println(prg.getSymTable());

            printW.println("Output:");
            printW.println(prg.getOut());

            printW.println("File Table:");
            if(!prg.getFileTable().toString().equals(""))
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

    @Override
    public List<PrgState> getPrograms() {
        return this.programs;
    }

    @Override
    public void setPrograms(List<PrgState> newPrograms) {
        programs.clear();
        programs.addAll(newPrograms);
    }

    public int getNewId() { return id++; }
}