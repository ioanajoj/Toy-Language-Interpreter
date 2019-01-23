package Model.Containers;

import Model.Exceptions.InvalidKeyException;
import Model.Exceptions.MissingVariableException;
import javafx.util.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CyclicBarrier implements ICyclicBarrier<Integer, Pair<Integer, List<Integer>>> {
    private HashMap<Integer, Pair<Integer, List<Integer>>> cyclicBarrier;
    private int index = 1;

    public CyclicBarrier() {
        this.cyclicBarrier = new HashMap<>();
    }

    @Override
    public Pair<Integer, List<Integer>> lookUp(Integer k) throws MissingVariableException {
        if(cyclicBarrier.get(k) == null)
            throw new MissingVariableException("Variable " + k + "' doesn't exist in heap.");
        else return cyclicBarrier.get(k);
    }

    @Override
    public Pair<Integer, List<Integer>> get(Integer k) {
        return cyclicBarrier.get(k);
    }

    @Override
    public int put(Integer number) {
        int newKey = index;
        Pair<Integer, List<Integer>> newEntry = new Pair<>(number, new ArrayList<>());
        cyclicBarrier.put(newKey, newEntry);
        index = new AtomicInteger(index++).intValue();
        return newKey;
    }

    @Override
    public Set<Integer> keys() {
        return cyclicBarrier.keySet();
    }

    @Override
    public boolean isEmpty() {
        return cyclicBarrier.size() == 0;
    }

    @Override
    public void remove(Integer k) throws InvalidKeyException {
        if(cyclicBarrier.get(k) == null)
            throw new InvalidKeyException("The key '" + k + "' you are trying to remove from heap memory is not valid.");
        cyclicBarrier.remove(k);
    }

    @Override
    public int size() {
        return cyclicBarrier.size();
    }

    @Override
    public void replace(Integer k, Pair<Integer, List<Integer>> newVal) {
        cyclicBarrier.put(k, newVal);
    }

    @Override
    public void setContent(Map<Integer, Pair<Integer, List<Integer>>> newDict) {
        cyclicBarrier = new HashMap<>();
        for(Integer key : newDict.keySet())
            cyclicBarrier.put(key, newDict.get(key));
    }

    @Override
    public Map<Integer, Pair<Integer, List<Integer>>> getContent() {
        return cyclicBarrier;
    }

    @Override
    public String toString() {
        return cyclicBarrier.toString();
    }

    @Override
    public void reset() {
        this.cyclicBarrier = new HashMap<>();
        this.index = 1;
    }

    @Override
    public ArrayList<Pair<Integer, Pair<Integer, List<Integer>>>> getPairs() {
        ArrayList<Pair<Integer, Pair<Integer, List<Integer>>>> pairs = new ArrayList<>();
        cyclicBarrier.forEach((key, value) -> pairs.add(new Pair<>(key, value)));
        return pairs;
    }

    @Override
    public ArrayList<Triplet<Integer, Integer, String>> getGUI() {
        ArrayList<Triplet<Integer, Integer, String>> result = new ArrayList<>();
        cyclicBarrier.forEach((key, value) -> result.add(new Triplet<>(key, value.getKey(), String.join(",", value.getValue().toString()))));
        return result;
    }
}
