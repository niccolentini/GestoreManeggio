package main.java.DomainModel.Membership;

public class BoxPack implements Membership { //pacchetto base
    /** Base membership. Just the horse box. */


    private int lessons = 0;
    public String getType(){
        return "BoxPack";
    }
    public boolean isExipered () {
        if (lessons == 0) return true;
        else return false;
    }
    @Override
    public int getNumLessons() {
        return lessons; //il pachetto box non prevede lezioni incluse
    }
    @Override
    public void setNumLessons(int numLessons) {
        lessons = numLessons;
    }
    @Override
    public float getPrice() {
        return 100;
    }

    @Override
    public String getDescription() {
        return "Pacchetto base, prevede l'assegnazione del box.";
    }
}
