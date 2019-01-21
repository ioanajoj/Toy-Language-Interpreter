package Model.Containers;

import Model.Exceptions.InvalidKeyException;
import Model.Exceptions.MissingVariableException;
import javafx.util.Pair;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FileTable implements IFileTable<Integer, Pair<String, BufferedReader>> {
    private HashMap<Integer, Pair<String, BufferedReader>> fileTable;
    private int descriptor = 1;

    public FileTable() {
        this.fileTable = new HashMap<>();
    }

    @Override
    public Pair<String, BufferedReader> lookUp(Integer k) throws MissingVariableException {
        if(fileTable.get(k) == null)
            throw new MissingVariableException("File Descriptor '" + k + "' doesn't exist");
        else return fileTable.get(k);
    }

    @Override
    public Pair<String, BufferedReader> get(Integer k) {
        return fileTable.get(k);
    }

    @Override
    public int put(Pair<String, BufferedReader> v) {
        int k = descriptor++;
        fileTable.put(k, v);
        return k;
    }

    @Override
    public Set<Integer> keys() {
        return this.fileTable.keySet();
    }

    @Override
    public boolean isEmpty() {
        return fileTable.size() == 0;
    }

    @Override
    public void  remove(Integer k) throws InvalidKeyException {
        if(fileTable.get(k) == null)
            throw new InvalidKeyException("The key '" + k + "' you are trying to remove from fileTable is invalid");
        fileTable.remove(k);
    }

    @Override
    public int size() {
        return fileTable.size();
    }

    @Override
    public void replace(Integer k, Pair<String, BufferedReader> newVal) {
        fileTable.put(k, newVal);
    }

    @Override
    public void setContent(Map<Integer, Pair<String, BufferedReader>> newDict) {
        fileTable = new HashMap<>();
        for(Integer key : newDict.keySet())
            fileTable.put(key, newDict.get(key));
    }

    @Override
    public Map<Integer, Pair<String, BufferedReader>> getContent() {
        return fileTable;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(int desc : this.fileTable.keySet()) {
            result.append(desc).append(": ");
            result.append(this.fileTable.get(desc).getKey());
            result.append('\n');
        }
        if(result.length() == 0)
            return "";
        return result.substring(0, result.length()-1);
    }

    @Override
    public void reset() {
        this.fileTable = new HashMap<>();
        this.descriptor = 1;
    }

    @Override
    public ArrayList<Pair<Integer, Pair<String, BufferedReader>>> getPairs() {
        ArrayList<Pair<Integer, Pair<String, BufferedReader>>> pairs = new ArrayList<>();
        fileTable.forEach((key, value) -> pairs.add(new Pair<>(key, value)));
        return pairs;
    }


}
