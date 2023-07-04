package main.java.BusinessLogic;

import main.java.DAO.TrainerDAO;
import main.java.DomainModel.Trainer;

public class TrainersController {

    TrainerDAO trainerDAO;

    public TrainersController(TrainerDAO trainerDAO){
        this.trainerDAO = trainerDAO;
    }

    public Trainer getTrainer(String fiscalCod) throws Exception{
        return trainerDAO.get(fiscalCod);
    }
    public void addTrainer (String fiscalcod, String fstName, String lstname){

    }

    public void removeTrainer(String fiscalCod){

    }
}
