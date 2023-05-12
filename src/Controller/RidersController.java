package Controller;
import DomainModel.*;

import java.util.ArrayList;

public class RidersController {
    private ArrayList<Rider> riders = new ArrayList<Rider>();

    public void newRider(String fiscalcod, String fstName, String lstname, Horse horse){
        Rider rider = new Rider(fiscalcod, fstName, lstname, horse);
        riders.add(rider);
    }


}
