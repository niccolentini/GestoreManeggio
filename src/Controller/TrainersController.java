package Controller;

import DomainModel.Trainer;

import java.util.ArrayList;

public class TrainersController {
    private ArrayList<Trainer> trainers = new ArrayList<Trainer>();

    public void addTrainer (String fiscalcod, String fstName, String lstname){
        Trainer trainer = new Trainer(fiscalcod, fstName, lstname);
        trainers.add(trainer);
    }

    public void removeTrainer(String fiscalCod){
        String fc;
        for(Trainer tr : trainers){
            fc = tr.getFiscalCod();
            if (fc == fiscalCod){
                trainers.remove(tr);
                break;
            }
        }
        System.out.println("Il codice fiscale inserito non corrisponde a nessun trainer.");
    }
}
