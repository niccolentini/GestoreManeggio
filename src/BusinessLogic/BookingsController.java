package BusinessLogic;

import DAO.LessonDAO;
import DAO.RiderDAO;
import DomainModel.*;

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

        int numLess = r.getMembership().getNumLessons();

        if (numLess > 0){
            if ( l.getNumRider() == l.getMaxRiders())
                throw new RuntimeException("Prenotazione non andata a buon fine. Sono state raggiunte le prenotazioni massime per questa lezione");

            ArrayList<Rider> riders = lessonDAO.getRidersForLesson(lessonId);
            if (riders.contains(r))
                throw new RuntimeException("Il rider è già iscritto a questa lezione");
            r.getMembership().setNumLessons(numLess - 1);
            lessonDAO.addRiderToLesson(riderFiscalCode, lessonId);
        }


        getLessonsForRider(riderFiscalCode).forEach(lesson -> {
            if (lesson.getDate().equals(l.getDate()) && lesson.getTime().equals(l.getTime()))
                throw new RuntimeException("Il rider è già iscritto ad un'altra lezione in questo orario");
        });

        //TODO: controllare se l'abbonamento è valido

        lessonDAO.addRiderToLesson(riderFiscalCode, lessonId);

    }

    public void removeRiderFromLesson (String fiscalCode, int lessonId) throws Exception{
        lessonDAO.removeRiderFromLesson(fiscalCode, lessonId);
    }


    public ArrayList<Lesson> getLessonsForRider(String riderFiscalCode) throws Exception {
        return lessonDAO.getLessonsForRider(riderFiscalCode);
    }



    @Override
    public void update(Lesson lesson) {
        //TODO: upddate observer
    }
}
