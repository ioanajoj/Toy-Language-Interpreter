package Model.Containers;

import Model.Exceptions.InvalidKeyException;
import Model.Exceptions.MissingVariableException;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Heap implements IHeap<Integer, Integer> {
    private HashMap<Integer, Integer> heapMemory;
    private int address = 1;

    public Heap() {
        this.heapMemory = new HashMap<>();
    }

    @Override
    public Integer lookUp(Integer k) throws MissingVariableException {
        if(heapMemory.get(k) == null)
            throw new MissingVariableException("Variable " + k + "' doesn't exist in heap.");
        else return heapMemory.get(k);
    }

    @Override
    public Integer get(Integer k) {
        return heapMemory.get(k);
    }

    @Override
    public int put(Integer v) {
        int newKey = address;
        heapMemory.put(newKey,v);
        address++;
        return newKey;
    }

    @Override
    public Set<Integer> keys() {
        return heapMemory.keySet();
    }

    @Override
    public boolean isEmpty() {
        return heapMemory.size() == 0;
    }

    @Override
    public void remove(Integer k) throws InvalidKeyException {
        if(heapMemory.get(k) == null)
            throw new InvalidKeyException("The key '" + k + "' you are trying to remove from heap memory is not valid.");
        heapMemory.remove(k);
    }

    @Override
    public int size() {
        return heapMemory.size();
    }

    @Override
    public void replace(Integer k, Integer newVal) {
        heapMemory.put(k, newVal);
    }

    @Override
    public void setContent(Map<Integer, Integer> newDict) {
        heapMemory = new HashMap<>();
        for(Integer key : newDict.keySet())
            heapMemory.put(key, newDict.get(key));
    }

    @Override
    public Map<Integer, Integer> getContent() {
        return heapMemory;
    }

    @Override
    public String toString() {
        return heapMemory.toString();
    }

    @Override
    public void reset() {
        this.heapMemory = new HashMap<>();
        this.address = 1;
    }

    @Override
    public ArrayList<Pair<Integer, Integer>> getPairs() {
        ArrayList<Pair<Integer, Integer>> pairs = new ArrayList<>();
        heapMemory.forEach((key, value) -> pairs.add(new Pair<>(key, value)));
        return pairs;
    }
}
