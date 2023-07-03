package BusinessLogic;

import DAO.LessonDAO;
import DomainModel.Arena;
import DomainModel.Lesson;
import DomainModel.Rider;
import DomainModel.Trainer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

public class LessonsController extends Subject {
    private final LessonDAO lessonDAO;
    private final TrainersController trainersController;
    private final ArenasController arenaController;

    public LessonsController(LessonDAO lessonDAO, TrainersController trainersController, ArenasController arenaController){
        this.lessonDAO = lessonDAO;
        this.trainersController = trainersController;
        this.arenaController = arenaController;
    }

    public void createLesson (int idArena, String trainerFiscalCode, LocalDate date, LocalTime time) throws Exception{
        Trainer trainer = trainersController.getTrainer(trainerFiscalCode);
        if(trainer == null) {
            throw new IllegalArgumentException("Trainer not found");
        }

        //check if the arena is available and not booked for this date and time
        Arena arena = arenaController.getArena(idArena);
        if(arena == null) { throw new IllegalArgumentException("Arena not found");}
        if(!arena.isAvailable()) { throw new IllegalArgumentException("Arena not available"); }
        if(lessonDAO.isArenaBookedAtTimeDate(idArena, date, time)) { throw new IllegalArgumentException("Arena already booked at this time and date");}

        Lesson lesson = new Lesson(lessonDAO.getNextId(), arena, trainer, date, time);
        lessonDAO.add(lesson);
    }

    public void deleteLesson(int lessonId) throws Exception{

        lessonDAO.remove(lessonId);
    }

    public ArrayList<Lesson> getAllLessons() throws Exception{
        return lessonDAO.getAll();
    }

    Lesson getLesson (int lessonId) throws Exception{
        return lessonDAO.get(lessonId);
    }


}
