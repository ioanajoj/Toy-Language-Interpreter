package Repository;

import Model.PrgState;
import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

            printW.println("PROGRAM STATE INDEX: " + prg.getId());
            printW.println("*=== EXECUTION STACK ===*");
            if(!prg.getExeStack().toString().equals(""))
                printW.println(prg.getExeStack());

            printW.println("*=== SYMBOL TABLE ===*");
            printW.println(prg.getSymTable());

            printW.println("*=== OUTPUT ===*");
            printW.println(prg.getOut());

            printW.println("*=== FILE TABLE ===*:");
            if(!prg.getFileTable().toString().equals(""))
                printW.println(prg.getFileTable());

            printW.println("*=== HEAP MEMORY ===*") ;
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

    public int getNewId() { return new AtomicInteger(id++).intValue(); }

    @Override
    public String getNoProgramStates() {
        return String.valueOf(this.programs.size());
    }

    @Override
    public ArrayList<String> getExecStackString(Integer prgStateIndex) {
        String[] strings = programs.get(prgStateIndex).getExeStack().toString().split("\n");
        return new ArrayList<>(Arrays.asList(strings));
    }

    @Override
    public ArrayList<String> getOutString(Integer prgStateIndex) {
        String[] strings = programs.get(prgStateIndex).getOut().toString().split("\n");
        return new ArrayList<>(Arrays.asList(strings));
    }

    @Override
    public ArrayList<String> getProgramsString() {
        ArrayList<String> result = new ArrayList<>();
        programs.forEach(p -> result.add(Integer.toString(p.getId())));
        return result;
    }

    @Override
    public ArrayList<Pair<String, Integer>> getSymbolTblPairs(Integer prgStateIndex) {
        return programs.get(prgStateIndex).getSymTable().getPairs();
    }

    @Override
    public ArrayList<Pair<Integer, String>> getFileTablePairs(Integer prgStateIndex) {
        ArrayList<Pair<Integer, String>> pairs = new ArrayList<>();
        ArrayList<Pair<Integer, Pair<String, BufferedReader>>> raw = programs.get(prgStateIndex).getFileTable().getPairs();
        for (Pair<Integer, Pair<String, BufferedReader>> aRaw : raw) {
            pairs.add(new Pair<>(aRaw.getKey(), aRaw.getValue().getKey()));
        }
        return pairs;
    }

    @Override
    public ArrayList<Pair<Integer, Integer>> getHeapPairs(Integer prgStateIndex) {
        return programs.get(prgStateIndex).getHeapMemory().getPairs();
    }

    @Override
    public ArrayList<Pair<Integer, Integer>> getLockTableString(Integer prgStateIndex) {
        return programs.get(prgStateIndex).getLockTable().getPairs();
    }

    @Override
    public Integer getPrgStateIndex(int name) {
        for(int i=0; i<programs.size(); i++) {
            if (programs.get(i).getId() == name)
                return i;
        }
        return 0;
    }


}