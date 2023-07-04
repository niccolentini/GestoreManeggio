package main.java.BusinessLogic;

import main.java.DAO.TrainerDAO;
import main.java.DomainModel.Horse;
import main.java.DomainModel.Membership.Membership;
import main.java.DomainModel.Rider;
import main.java.DomainModel.Trainer;

import java.sql.SQLException;
import java.util.ArrayList;

public class TrainersController {

    TrainerDAO trainerDAO;

    public TrainersController(TrainerDAO trainerDAO){
        this.trainerDAO = trainerDAO;
    }

    public Trainer getTrainer(String fiscalCod) throws Exception{
        return trainerDAO.get(fiscalCod);
    }
    public void addTrainer (String fiscalcod, String fstName, String lstname) throws Exception{
        Trainer trainer = new Trainer(fiscalcod, fstName, lstname);
        trainerDAO.add(trainer);
    }

    public void removeTrainer(String fiscalCod) throws Exception{
        trainerDAO.remove(fiscalCod);
    }

    public void updateTrainer(String fiscalcod, String fstName, String lstname) throws Exception {
        Trainer trainer = new Trainer(fiscalcod, fstName, lstname);
        trainerDAO.update(trainer);
    }

    public ArrayList<Trainer> getAllTrainers() throws Exception{
        return trainerDAO.getAll();
    }

}
