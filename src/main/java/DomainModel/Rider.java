package main.java.DomainModel;

import main.java.BusinessLogic.Observer;
import main.java.DomainModel.Membership.Membership;

public class Rider extends User implements Observer {
    private final Horse horse;
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

    @Override
    public void update(){
        System.out.println("Rider " + this.getFirstName() + " " + this.getLastName() + " has been notified of the lesson cancellation");
    }

}
