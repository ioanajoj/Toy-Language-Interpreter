package Model.Containers;

import Model.Exceptions.InvalidKeyException;
import Model.Exceptions.MissingVariableException;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LockTable implements ILockTable<Integer, Integer> {
    private HashMap<Integer, Integer> lockTable;
    private int location = 1;

    public LockTable() {
        this.lockTable = new HashMap<>();
    }

    @Override
    public Integer lookUp(Integer k) throws MissingVariableException {
        if(lockTable.get(k) == null)
            throw new MissingVariableException("Variable " + k + "' doesn't exist in lock table.");
        else return lockTable.get(k);
    }

    @Override
    public Integer get(Integer k) {
        return lockTable.get(k);
    }

    @Override
    public int put(Integer v) {
        int newKey = location;
        lockTable.put(location,v);
        location++;
        return newKey;
    }

    @Override
    public void resetKey(Integer k) {
        this.lockTable.put(k, -1);
    }

    @Override
    public Set<Integer> keys() {
        return lockTable.keySet();
    }

    @Override
    public boolean isEmpty() {
        return lockTable.size() == 0;
    }

    @Override
    public void remove(Integer k) throws InvalidKeyException {
        if(lockTable.get(k) == null)
            throw new InvalidKeyException("The key '" + k + "' you are trying to remove from heap memory is not valid.");
        lockTable.remove(k);
    }

    @Override
    public int size() {
        return lockTable.size();
    }

    @Override
    public void replace(Integer k, Integer newVal) {
        lockTable.put(k, newVal);
    }

    @Override
    public void setContent(Map<Integer, Integer> newDict) {
        lockTable = new HashMap<>();
        for(Integer key : newDict.keySet())
            lockTable.put(key, newDict.get(key));
    }

    @Override
    public Map<Integer, Integer> getContent() {
        return lockTable;
    }

    @Override
    public void reset() {
        this.lockTable= new HashMap<>();
        this.location = 1;
    }

    @Override
    public ArrayList<Pair<Integer, Integer>> getPairs() {
        ArrayList<Pair<Integer, Integer>> pairs = new ArrayList<>();
        lockTable.forEach((key, value) -> pairs.add(new Pair<>(key, value)));
        return pairs;
    }
}
