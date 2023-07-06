package main.java.BusinessLogic;
import main.java.DAO.RiderDAO;
import main.java.DAO.MembershipDAO;
import main.java.DomainModel.*;
import main.java.DomainModel.Membership.BoxPack;
import main.java.DomainModel.Membership.GroomPack;
import main.java.DomainModel.Membership.LessonsPack;
import main.java.DomainModel.Membership.Membership;

import java.sql.SQLException;
import java.util.ArrayList;

public class RidersController {
    private RiderDAO riderDAO;
    private MembershipDAO membershipDAO;

    public RidersController(RiderDAO riderDAO, MembershipDAO membershipDAO) {
        this.riderDAO = riderDAO;
        this.membershipDAO = membershipDAO;
    }

    public void addRider(String fiscalcod, String fstName, String lstname, Horse horse, int membership) throws SQLException {
        Rider rider = new Rider(fiscalcod, fstName, lstname, horse);
        Membership m = new BoxPack();
        rider.setMembership(m);
        if (membership == 1){
            rider.setMembership(new LessonsPack(m));
        }
        else if (membership == 2){
            rider.setMembership(new GroomPack(m));
        } else if (membership == 3) {
            rider.setMembership(new LessonsPack(new GroomPack(m)));
        }
        riderDAO.add(rider);
        membershipDAO.add(fiscalcod, rider.getMembership());
    }

    public void updateRider(String fiscalcod, String fstName, String lstname, Horse horse, Membership m) throws SQLException {
        Rider rider = new Rider(fiscalcod, fstName, lstname, horse);
        rider.setMembership(m);
        riderDAO.update(rider);
        membershipDAO.update(fiscalcod, rider.getMembership());
    }

    public void deleteRider(String fiscalcod) throws SQLException {
        this.riderDAO.remove(fiscalcod);
    }

    public Rider getRiderByFisCod(String fiscalcod) throws Exception {
        return this.riderDAO.get(fiscalcod);
    }

    public ArrayList<Rider> getAllRiders() throws Exception {
        return (ArrayList<Rider>) riderDAO.getAll();
    }


}
