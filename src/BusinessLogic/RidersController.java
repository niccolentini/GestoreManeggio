package BusinessLogic;
import DAO.RiderDAO;
import DomainModel.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class RidersController {
    private RiderDAO riderDAO;

    public RidersController(RiderDAO riderDAO) {
        this.riderDAO = riderDAO;
    }

    public void addRider(String fiscalcod, String fstName, String lstname, String horse) throws SQLException {
        Rider rider = new Rider(fiscalcod, fstName, lstname, horse);
        riderDAO.add(rider);
    }

    public void updateRider(String fiscalcod, String fstName, String lstname, String horse) throws SQLException {
        Rider rider = new Rider(fiscalcod, fstName, lstname, horse);
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
