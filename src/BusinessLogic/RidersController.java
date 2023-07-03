package BusinessLogic;
import DAO.RiderDAO;
import DomainModel.*;
import DomainModel.Membership.BoxPack;
import DomainModel.Membership.GroomPack;
import DomainModel.Membership.LessonsPack;
import DomainModel.Membership.Membership;

import java.sql.SQLException;
import java.util.ArrayList;

public class RidersController {
    private RiderDAO riderDAO;

    public RidersController(RiderDAO riderDAO) {
        this.riderDAO = riderDAO;
    }

    public void addRider(String fiscalcod, String fstName, String lstname, String horse, int membership) throws SQLException {
        Rider rider = new Rider(fiscalcod, fstName, lstname, horse);
        Membership m = new BoxPack();
        if (membership == 1){
            rider.setMembership(new LessonsPack(m));
        }
        else if (membership == 2){
            rider.setMembership(new GroomPack(m));
        }
        else {
            rider.setMembership(m);
        }
        riderDAO.add(rider);
    }

    public void updateRider(String fiscalcod, String fstName, String lstname, String horse, Membership m) throws SQLException {
        Rider rider = new Rider(fiscalcod, fstName, lstname, horse);
        rider.setMembership(m);
        riderDAO.update(rider);
    }

    public void deleteRider(String fiscalcod) throws SQLException {
        this.riderDAO.remove(fiscalcod);
    }

    public Rider getRiderByFisCod(String fiscalcod) throws SQLException {
        return this.riderDAO.get(fiscalcod);
    }

    public ArrayList<Rider> getAllRiders() throws SQLException {
        return (ArrayList<Rider>) riderDAO.getAll();
    }


}
