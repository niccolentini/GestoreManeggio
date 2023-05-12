package Controller;
import DomainModel.Arena;
import java.util.ArrayList;
import java.util.Scanner;

public class ArenasController {
    private ArrayList<Arena> arenas = new ArrayList<Arena>();

    public void addArena(Arena arena){
        arenas.add(arena);
    }

    public void removeArena(Arena arena){
        String arenaName;
        int i = 1;
        int numArenas = this.arenas.size();
        System.out.println("Al momento ci sono " + numArenas + " arene...");
        for(Arena A : arenas ){
            arenaName = A.getName();
            System.out.println(i + ": arena " + arenaName);
        }
        System.out.println("Quale arena desidera rimuovere? (scegliere il numero di elencazione oppure -1 per annullare l'operazione)");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        if (id == -1){
            System.out.println("Operazione annullata.");
        } else if (id < -1 || id > numArenas) {
            System.out.println("Il numero selezionato non corrisponde a nessuna arena.");
            System.out.println("Operazione annullata.");
        } else {
            arenas.remove(id);
            System.out.println("Arena rimossa correttamente.");
        }
        scanner.close();
    }

    public void disableArena (){
        String arenaName;
        int i = 1;
        int numArenas = this.arenas.size();
        System.out.println("Tra queste " + numArenas + " arene...");
        for(Arena A : arenas ){
            arenaName = A.getName();
            System.out.println(i + ": arena " + arenaName);
        }
        System.out.println("Quale arena desidera disabilitare? (scegliere il numero di elencazione oppure -1 per annullare l'operazione)");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        if (id == -1){
            System.out.println("Operazione annullata.");
        } else if (id < -1 || id > numArenas) {
            System.out.println("Il numero selezionato non corrisponde a nessuna arena.");
            System.out.println("Operazione annullata.");
        }else {
            boolean available = arenas.get(id).isAvailable();
            if (available) {
                arenas.get(id).setAvailable(false);
                System.out.println("Arena disabilitata correttamente!");
            } else {
                System.out.println("L'arena selezionata risulta già disabilitata.");
            }
            scanner.close();
        }
    }

    public void enableArena() {
        String arenaName;
        int i = 1;
        int numArenas = this.arenas.size();
        System.out.println("Tra queste " + numArenas + " arene...");
        for(Arena A : arenas ){
            arenaName = A.getName();
            System.out.println(i + ": arena " + arenaName);
        }
        System.out.println("Quale arena desidera disabilitare? (scegliere il numero di elencazione)");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        if (id == -1){
            System.out.println("Operazione annullata.");
        } else if (id < -1 || id > numArenas) {
            System.out.println("Il numero selezionato non corrisponde a nessuna arena.");
            System.out.println("Operazione annullata.");
        }else {
            boolean available = arenas.get(id).isAvailable();
            if (!available) {
                arenas.get(id).setAvailable(true);
                System.out.println("Arena abilitata correttamente!");
            } else {
                System.out.println("L'arena selezionata risulta già abilitata.");
            }
        }
        scanner.close();
    }
}
