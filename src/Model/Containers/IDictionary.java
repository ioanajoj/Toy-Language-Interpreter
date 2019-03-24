package Model.Containers;

import Model.Exceptions.InvalidKeyException;
import Model.Exceptions.MissingVariableException;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public interface IDictionary<Key, Value> {
    Value lookUp(Key k) throws MissingVariableException;
    Value get(Key k);
    void put(Key k, Value v);
    Set<Key> keys();
    Collection<Value> values();
    boolean isEmpty();
    void remove (Key k) throws InvalidKeyException;
    int size();
    void replace(Key k, Value newVal);
    String toString();
    ArrayList<Pair<Key,Value>> getPairs();
}
