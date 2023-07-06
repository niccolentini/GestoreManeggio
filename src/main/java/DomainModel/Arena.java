package main.java.DomainModel;

public class Arena {
    private String name;
    private int idArena;
    private int available = 1;  //l'arena è sempre disponibile a meno che la segreteria non decida di disabilitarla


    public Arena(String name, int idArena) {
        this.name = name;
        this.idArena = idArena;
    }

    public String getName() {
        return name;
    }

    public int getIdArena() {
        return idArena;
    }

    public void setAvailable(int a){
        this.available = a;
    }

    public int isAvailable() {
        return available;
    }

}
