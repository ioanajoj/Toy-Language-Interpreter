package Model.Containers;

import Model.Exceptions.InvalidKeyException;
import Model.Exceptions.MissingVariableException;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public interface ILockTable<Key, Value> {
    Value lookUp(Key k) throws MissingVariableException;
    Value get(Key k);
    int put(Value v);
    void resetKey(Key k);
    Set<Key> keys();
    boolean isEmpty();
    void remove (Key k) throws InvalidKeyException;
    int size();
    void replace(Key k, Value newVal);
    void setContent(Map<Key, Value> newDict);
    Map<Key, Value> getContent();
    String toString();
    void reset();
    ArrayList<Pair<Key,Value>> getPairs();
}
