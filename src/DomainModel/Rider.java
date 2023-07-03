package DomainModel;

import DomainModel.Membership.Membership;

public class Rider extends User{
    private String horse;
    private Membership membership;

    public Rider(String fiscalCod, String firstName, String lastName, String horse) {
        super(fiscalCod, firstName, lastName);
        this.horse = horse;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public String getHorse() {
        return horse;
    }

}
