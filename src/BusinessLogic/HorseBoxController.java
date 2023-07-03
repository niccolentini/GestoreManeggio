package BusinessLogic;

import DAO.HorseBoxDAO;
import DomainModel.Horse;
import DomainModel.HorseBox;


public class HorseBoxController {

    HorseBoxDAO horseBoxDAO;

    public HorseBoxController(HorseBoxDAO horseBoxDAO){
        this.horseBoxDAO = horseBoxDAO;
    }

    public void addBox() throws Exception{
        HorseBox horseBox = new HorseBox(horseBoxDAO.getNextId());
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
