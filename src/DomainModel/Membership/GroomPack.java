package DomainModel.Membership;

public class GroomPack extends MembershipDecorator{
    public GroomPack(Membership membership) {
        super(membership);
    }

    @Override
    public int getNumLessons() {
        return super.getNumLessons();
    }

    @Override
    public float getPrice() {
        return super.getPrice() + 50 ;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " Pacchetto groom, il groom del maneggio si occuperà del tuo cavallo.";
    }
}
