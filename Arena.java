package DomainModel;

public class Arena {
    private String name;
    private int idArena;
    private boolean available = true;

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
    public void setAvailable(boolean b){
        this.available = b;
    }
    public boolean isAvailable() {
        return available;
    }
}
