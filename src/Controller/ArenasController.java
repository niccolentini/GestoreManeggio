package Controller;
import DomainModel.Arena;
import java.util.ArrayList;
import java.util.Scanner;

public class ArenasController {
    private ArrayList<Arena> arenas = new ArrayList<Arena>();

    public void addArena(Arena arena){
        arenas.add(arena);
    }

    public void removeArena(int nArena){
        Arena target = null;
        for(Arena ar : arenas) {
            if (ar.getIdArena() == nArena) {
                target = ar;
                break;
            }
        }
            if(target != null){
                arenas.remove(target);
                System.out.println("Arena rimossa correttamente!");
            }else {
                System.out.println("L'identificativo inserito non corrisponde con nessuna arena del centro.");
            }
        }


    public void disableArena (int nArena) {
        Arena target = null;
        for (Arena ar : arenas) {
            if (ar.getIdArena() == nArena) {
                target = ar;
                break;
            }
        }
        if (target != null) {
            boolean available = target.isAvailable();
            if (available) {
                target.setAvailable(false);
                System.out.println("Arena disabilitata correttamente!");
            } else {
                System.out.println("L'arena selezionata risulta già disabilitata.");
            }
        } else {
            System.out.println("L'identificativo inserito non corrisponde con nessuna arena del centro.");
        }
    }


    public void enableArena(int nArena) {
        Arena target = null;
        for (Arena ar : arenas) {
            if (ar.getIdArena() == nArena) {
                target = ar;
                break;
            }
        }
        if (target != null) {
            boolean available = target.isAvailable();
            if (!available) {
                target.setAvailable(true);
                System.out.println("Arena abilitata correttamente!");
            } else {
                System.out.println("L'arena selezionata risulta già abilitata.");
            }
        } else {
            System.out.println("L'identificativo inserito non corrisponde con nessuna arena del centro.");
        }
    }
}
