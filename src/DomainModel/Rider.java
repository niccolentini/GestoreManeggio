package DomainModel;

import DomainModel.Membership.Membership;

public class Rider extends User{
    private Horse horse;
    private Membership membership;

    public Rider(String fiscalCod, String firstName, String lastName, Horse horse) {
        super(fiscalCod, firstName, lastName);
        this.horse = horse;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public Horse getHorse() {
        return horse;
    }

}
