package DAO;
import java.util.ArrayList;

public interface HorseDAO extends DAO {
    public void add(Object o);
    public void update(Object o);
    public void remove(int id);
    public Object get(int id);
    public ArrayList<Object> getAll();
}
