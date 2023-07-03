package DomainModel.Membership;

public class BoxPack implements Membership{ //pacchetto base
    /** Base membership. Just the horse box. */
    public String getType(){
        return "BoxPack";
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
