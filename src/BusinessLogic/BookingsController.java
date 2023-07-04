package BusinessLogic;

import DAO.LessonDAO;
import DAO.MembershipDAO;
import DAO.RiderDAO;
import DomainModel.*;

import java.util.ArrayList;
import java.util.Objects;

public class BookingsController{
    private final LessonDAO lessonDAO;
    private final LessonsController lessonsController;
    private final RidersController ridersController;
    private final MembershipDAO membershipDAO;

    public BookingsController(LessonDAO lessonDAO, LessonsController lessonsController, RidersController ridersController, MembershipDAO membershipDAO) {
        this.lessonDAO = lessonDAO;
        this.lessonsController = lessonsController;
        this.ridersController = ridersController;
        this.membershipDAO = membershipDAO;
    }

    public void addRiderToLesson (String riderFiscalCode, int lessonId) throws Exception {
        Lesson l = lessonsController.getLesson(lessonId);
        Rider r = ridersController.getRiderByFisCod(riderFiscalCode);
        if (l == null) throw new RuntimeException("The given lesson id does not exist");
        if (r == null) throw new RuntimeException("The given rider does not exist");

        getLessonsForRider(riderFiscalCode).forEach(lesson -> {
            if (lesson.getDate().equals(l.getDate()) && lesson.getTime().equals(l.getTime()))
                throw new RuntimeException("Il rider è già iscritto ad un'altra lezione in questo orario");
        });

        int numLess = r.getMembership().getNumLessons();  //numero restanti di lezioni nel pacchetto
        if (numLess > 0){
            if ( l.getNumRider() == l.getMaxRiders())
                throw new RuntimeException("Prenotazione non andata a buon fine. Sono state raggiunte le prenotazioni massime per questa lezione");

            ArrayList<Rider> riders = lessonDAO.getRidersForLesson(lessonId);
            if (riders.contains(r))
                throw new RuntimeException("Il rider è già iscritto a questa lezione");
            r.getMembership().setNumLessons(numLess - 1);
            membershipDAO.update(riderFiscalCode, r.getMembership());
            lessonDAO.addRiderToLesson(riderFiscalCode, lessonId);
        }
        else throw new RuntimeException("Prenotazione non andata a buon fine. Il rider non ha più lezioni disponibili nel proprio pacchetto lezioni");
        //da questo momento il rider verrà notificato in caso di cambiamenti alla lezione
        //non creo una lista di observer per ogni lezione in quanto basta accedere al database per sapere gli observer di una lezione
    }

    public void removeRiderFromLesson (String fiscalCode, int lessonId) throws Exception{
        lessonDAO.removeRiderFromLesson(fiscalCode, lessonId);
    }


    public ArrayList<Lesson> getLessonsForRider(String riderFiscalCode) throws Exception {
        return lessonDAO.getLessonsForRider(riderFiscalCode);
    }

}
