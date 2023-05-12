package Controller;

import DomainModel.Horse;
import DomainModel.HorseBox;

import java.util.ArrayList;

public class HorseBoxController {
    private ArrayList<HorseBox> horseBoxes = new ArrayList<HorseBox>();

    public void newBox(){
        int max = 0;
        int id = 0;
        for (HorseBox hb : horseBoxes){
            id = hb.getBoxID();
            if(id > max)
                max = id;
        }
        max = max+1;
        HorseBox newHorseBox = new HorseBox(max);
        horseBoxes.add(newHorseBox);
    }

    public void assignBox(Horse h, HorseBox hb){
        for(HorseBox horsebox : horseBoxes){
            if(horsebox.getHorse() == null){
                horsebox.assignHorse(h);
                System.out.println("Cavallo assegnato al box n. " + horsebox.getBoxID());
                break;
            }
        }
        System.out.println("Non è stato trovato alcun box disponibile.");
    }

    public void freeBox(Horse h){ // questo metodo dovrebbe essere esguito se un rider abbandona il maneggio passando il cavallo associato
        Horse horse = null;
        for(HorseBox horsebox : horseBoxes){
            horse = horsebox.getHorse();
            if(horse == h) {
                horsebox.freeBox();
                break;
            }
        }
        System.out.println("Il cavallo cercato non è presente in maneggio.");
    }
}
