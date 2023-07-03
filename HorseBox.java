package DomainModel;

public class HorseBox {
    private int boxID;
    private Horse horse = null;

    public HorseBox(int boxID) {
        this.boxID = boxID;
    }

    public Horse getHorse() {
        return horse;
    }

    public void assignHorse(Horse horse) {
        this.horse = horse;
    }

    public int getBoxID() {
        return boxID;
    }

    public void freeBox(){
        horse = null;
    }
}
