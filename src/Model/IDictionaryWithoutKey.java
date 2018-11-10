package Model;

import java.util.Map;
import java.util.Set;

public interface IDictionaryWithoutKey<Key, Value> {
    Value lookUp(Key k) throws MissingVariableException;
    Value get(Key k);
    Integer put(Value v);
    Set<Key> keys();
    boolean isEmpty();
    void remove (Key k) throws InvalidKeyException;
    int size();
    void replace(Key k, Value newVal);
    void setContent(Map<Key, Value> newDict);
    Map<Key, Value> getContent();
    String toString();
}

