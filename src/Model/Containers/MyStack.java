package Model.Containers;

import java.util.ArrayList;
import java.util.EmptyStackException;

public class MyStack<T> implements MyIStack<T> {
    private ArrayList<T> stack;

    public MyStack() {
        this.stack = new ArrayList<>();
    }

    @Override
    public boolean isEmpty() {
        return stack.size() == 0;
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public T pop() throws EmptyStackException{
        if(!isEmpty()) {
            T first = stack.get(0);
            stack.remove(0);
            return first;
        }
        else throw new EmptyStackException();
    }

    @Override
    public T top() throws EmptyStackException{
        if(!isEmpty())
            return stack.get(0);
        else throw new EmptyStackException();
    }

    @Override
    public void push(T elem) {
        this.stack.add(0,elem);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(int i=0; i<stack.size()-1; i++)
        {
            result.append(stack.get(i));
            result.append("\n");
        }
        if(!stack.isEmpty()) {
            result.append(stack.get(stack.size()-1));
        }
        return result.toString();
    }
}
