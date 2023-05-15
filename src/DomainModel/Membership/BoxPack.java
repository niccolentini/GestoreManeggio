package DomainModel.Membership;

public class BoxPack implements Membership{ //pacchetto base

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
