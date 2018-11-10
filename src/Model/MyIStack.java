package Model;

import java.util.EmptyStackException;

public interface MyIStack<T> {
    T pop() throws EmptyStackException;
    T top() throws EmptyStackException;
    void push(T elem);
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean isEmpty();
    int size();
    String toString();
}
