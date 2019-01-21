package Repository;

import Model.PrgState;
import javafx.util.Pair;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;

public interface IRepository {
    PrgState getCurrentPrg();
    int getCurrentIndex();
    void addPrgState(PrgState prg);
    void logAll();
    void logPrgState(PrgState prgState);
    void clearFile();
    List<PrgState> getPrograms();
    void setPrograms(List<PrgState> newPrograms);
    int getNewId();

    /* GUI */
    String getNoProgramStates();
    ArrayList<String> getExecStackString(Integer prgStateIndex);
    ArrayList<String> getOutString(Integer prgStateIndex);
    ArrayList<String> getProgramsString();
    ArrayList<Pair<String, Integer>> getSymbolTblPairs(Integer prgStateIndex);
    ArrayList<Pair<Integer, String>> getFileTablePairs(Integer prgStateIndex);
    ArrayList<Pair<Integer, Integer>> getHeapPairs(Integer prgStateIndex);
    ArrayList<Pair<Integer, Integer>> getLockTableString(Integer prgStateIndex);
    Integer getPrgStateIndex(int name);
}
