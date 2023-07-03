package BusinessLogic;

import DAO.LessonDAO;
import DAO.RiderDAO;
import DomainModel.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class BookingsController implements Observer{
    private final LessonDAO lessonDAO;
    private final LessonsController lessonsController;
    private final RidersController ridersController;

    public BookingsController(LessonDAO lessonDAO, LessonsController lessonsController, RidersController ridersController) {
        this.lessonDAO = lessonDAO;
        this.lessonsController = lessonsController;
        this.ridersController = ridersController;
    }

    public void addRiderToLesson (String riderFiscalCode, int lessonId) throws Exception {
        Lesson l = lessonsController.getLesson(lessonId);
        Rider r = ridersController.getRiderByFisCod(riderFiscalCode);
        if (l == null) throw new RuntimeException("The given lesson id does not exist");
        if (r == null) throw new RuntimeException("The given rider does not exist");

        if ( l.getNumRider() == l.getMaxRiders())
            throw new RuntimeException("Prenotazione non andata a buon fine. Sono state raggiunte le prenotazioni massime per questa lezione");

        ArrayList<Rider> riders = lessonDAO.getRidersForLesson(lessonId);
        if (riders.contains(r))
            throw new RuntimeException("Il rider è già iscritto a questa lezione");


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


    @Override
    public void update(Lesson lesson) {
        //TODO: qui dentro richiamo il lessonsDAO per cancellare le associazioni rider-lezione
    }
}
