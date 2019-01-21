package Model.Containers;

import Model.Exceptions.InvalidIndexException;

public interface MyIList<T> {
    void add(T elem);
    void add(int pos, T elem);
    boolean contains(T elem);
    int size();
    boolean isEmpty();
    T get(int pos) throws InvalidIndexException;
    T remove(int pos) throws InvalidIndexException;
    String toString();
}
