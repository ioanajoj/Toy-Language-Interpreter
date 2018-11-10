package Repository;

import Model.PrgState;

public interface IRepository {
    PrgState getCurrentPrg();
    int getCurrentIndex();
    void addPrgState(PrgState prg);
    void logPrgState(int index);
    void clearFile();
}
