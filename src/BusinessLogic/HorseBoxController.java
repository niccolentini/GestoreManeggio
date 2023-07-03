package BusinessLogic;

import DAO.HorseBoxDAO;
import DomainModel.Horse;
import DomainModel.HorseBox;

import javax.swing.*;
import java.util.ArrayList;

public class HorseBoxController {

    HorseBoxDAO horseBoxDAO;

    public HorseBoxController(HorseBoxDAO horseBoxDAO){
        this.horseBoxDAO = horseBoxDAO;
    }

    public void addBox(HorseBox horseBox) throws Exception{
        horseBoxDAO.add(horseBox);
    }

    public void assignBox(Horse horse, HorseBox box) throws Exception{
        box.setHorse(horse);
        horseBoxDAO.update(box);
    }

    public void freeBox(int horseId) throws Exception{  //medoto per liberare il box quando un rider abbandona un maneggio
        HorseBox box = horseBoxDAO.searchByHorse(horseId);
        horseBoxDAO.remove(box.getBoxID());
    }
}
