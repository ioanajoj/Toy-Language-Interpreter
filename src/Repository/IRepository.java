package Repository;

import Model.PrgState;

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
}
