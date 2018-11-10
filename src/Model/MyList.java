package Model;

import java.util.ArrayList;

public class MyList<T> implements MyIList<T> {
    private ArrayList<T> list;

    public MyList() { list = new ArrayList<>(); }

    @Override
    public void add(T elem) { list.add(elem); }

    @Override
    public void add(int pos, T elem) {
        list.add(pos, elem);
    }

    @Override
    public boolean contains(T elem) {
        return list.contains(elem);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.size() == 0;
    }

    @Override
    public T get(int pos) throws InvalidIndexException{
        if(pos >= list.size())
            throw new InvalidIndexException("The position you are trying to remove from the list does not exist");
        return list.get(list.size()-1);
    }

    @Override
    public T remove(int pos) throws InvalidIndexException {
        if(pos >= list.size())
            throw new InvalidIndexException("The position you are trying to remove from the list does not exist");
        return list.remove(list.size()-1);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
