package Model;

import javax.management.openmbean.InvalidKeyException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class MyDictionary<Key, Value> implements MyIDictionary<Key, Value> {
    private LinkedHashMap<Key, Value> dictionary;

    public MyDictionary() {
        dictionary = new LinkedHashMap<>();
    }

    @Override
    public Value lookUp(Key k) throws MissingVariableException{
        if(dictionary.get(k) == null)
            throw new MissingVariableException("Variable '" + k + "' doesn't exist");
        else return dictionary.get(k);
    }

    @Override
    public Value get(Key k) { return dictionary.get(k); }

    @Override
    public void put(Key k, Value v) {
        dictionary.put(k, v);
    }

    @Override
    public Set<Key> keys() {
        return this.dictionary.keySet();
    }

    @Override
    public Collection<Value> values() {
        return dictionary.values();
    }

    @Override
    public boolean isEmpty() {
        return dictionary.isEmpty();
    }

    @Override
    public void remove(Key k) throws InvalidKeyException{
        if(dictionary.get(k) == null)
            throw new InvalidKeyException("The key '" + k + "' you are trying to remove from dictionary is invalid");
        dictionary.remove(k);
    }

    @Override
    public int size() {
        return dictionary.size();
    }

    @Override
    public void replace(Key k, Value newVal) {
        dictionary.replace(k, newVal);
    }

    @Override
    public String toString() {
        return dictionary.toString();
    }
}
