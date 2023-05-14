package Controller;

import DomainModel.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class BookingsController {

    public void addRiderToLesson (Rider rider, @NotNull Lesson lesson){
        lesson.addRider(rider);
    }

    public void removeRiderFromLesson (Rider rider, Lesson lesson){
        for(Rider r : lesson.getRiders()){
            if(Objects.equals(r.getFiscalCod(), rider.getFiscalCod())){
                lesson.removeRider(r);
                System.out.println("Rider rimosso dalla lezione.");
            }
        }
        System.out.println("Il rider inserito non è iscritto alla lezione del"); //todo aggiungi data nell'out dopo del...
    }



}
