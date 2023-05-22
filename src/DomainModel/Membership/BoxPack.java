package DomainModel.Membership;

public class BoxPack implements Membership{ //pacchetto base

    private int ID;

    public BoxPack(int ID) {
        this.ID = ID;
    }

    public String getType(){
        return "BoxPack";
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public int getNumLessons() {
        return 0; //il pachetto box non prevede lezioni incluse
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
