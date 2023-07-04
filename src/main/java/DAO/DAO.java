package main.java.DAO;

import java.util.ArrayList;

public interface DAO<T, ID>{
    public void add(T t) throws Exception;
    public void update(T t) throws Exception;
    public void remove(ID id) throws Exception;
    public T get(ID id) throws Exception;
    public ArrayList<T> getAll() throws Exception;
}
