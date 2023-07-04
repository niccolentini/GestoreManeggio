package main.java.DomainModel;

public class HorseBox {
    private int boxID;
    private Horse horse = null;

    public HorseBox(int boxID) {
        this.boxID = boxID;
    }

    public Horse getHorse() {
        return horse;
    }

    public int getHorseId(Horse horse){
        return horse.getHorseId();
    }

    public int getBoxID() {
        return boxID;
    }

    public void setHorse(Horse horse) {
        this.horse = horse;
    }
}
