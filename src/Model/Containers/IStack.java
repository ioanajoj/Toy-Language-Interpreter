package Model.Containers;

import java.util.EmptyStackException;

public interface IStack<T> {
    T pop() throws EmptyStackException;
    T top() throws EmptyStackException;
    void push(T elem);
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean isEmpty();
    int size();
    String toString();
}
