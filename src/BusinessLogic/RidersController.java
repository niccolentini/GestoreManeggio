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

    public void newRider(String fiscalcod, String fstName, String lstname, String horse) throws SQLException {
        Rider rider = new Rider(fiscalcod, fstName, lstname, horse);
        riderDAO.createRider(rider);
    }

    public void updateRider(String fiscalcod, String fstName, String lstname, String horse) throws SQLException {
        Rider rider = new Rider(fiscalcod, fstName, lstname, horse);
        riderDAO.updateRider(rider);
    }

    public void deleteRider(String fiscalcod) throws SQLException {
        riderDAO.deleteRider(fiscalcod);
    }

    public Rider getRiderByFisCod(String fiscalcod) throws SQLException {
        return riderDAO.getRiderByFisCod(fiscalcod);
    }

    public ArrayList<Rider> getAllRiders() throws SQLException {
        return (ArrayList<Rider>) riderDAO.getAllRiders();
    }


}
