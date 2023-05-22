package DAO;
import java.util.ArrayList;

public interface HorseDAO extends DAO { //TODO: QUI LA CHIAVE PRIMARIA É IL NOME DEL CAVALLO
    public void add(Object o);
    public void update(Object o);
    public void remove(int id);
    public Object get(int id);
    public ArrayList<Object> getAll();
}
