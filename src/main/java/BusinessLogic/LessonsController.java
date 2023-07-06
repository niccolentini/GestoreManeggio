package main.java.BusinessLogic;

import main.java.DAO.LessonDAO;
import main.java.DomainModel.Arena;
import main.java.DomainModel.Lesson;
import main.java.DomainModel.Rider;
import main.java.DomainModel.Trainer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class LessonsController implements Subject {
    private final LessonDAO lessonDAO;
    private final TrainersController trainersController;
    private final ArenasController arenaController;

    public LessonsController(LessonDAO lessonDAO, TrainersController trainersController, ArenasController arenaController){
        this.lessonDAO = lessonDAO;
        this.trainersController = trainersController;
        this.arenaController = arenaController;
    }

    public void addLesson (int idArena, String trainerFiscalCode, LocalDate date, LocalTime time) throws Exception{
        Trainer trainer = trainersController.getTrainer(trainerFiscalCode);
        if(trainer == null) {
            throw new IllegalArgumentException("Trainer not found");
        }

        //check if the arena is available and not booked for this date and time
        Arena arena = arenaController.getArena(idArena);
        if(arena == null) { throw new IllegalArgumentException("Arena not found");}
        if(arena.isAvailable() == 0) { throw new IllegalArgumentException("Arena not available"); }
        if(lessonDAO.isArenaBookedAtTimeDate(idArena, date, time)) { throw new IllegalArgumentException("Arena already booked at this time and date");}

        Lesson lesson = new Lesson(lessonDAO.getNextId(), arena, trainer, date, time);
        lessonDAO.add(lesson);
    }

    public void removeLesson(int lessonId) throws Exception{
        lessonDAO.remove(lessonId);
        notifyObservers(lessonId); //notifica gli iscritti alla lezione dell'avvenuta cancellazione
    }

    public ArrayList<Lesson> getAllLessons() throws Exception{
        return lessonDAO.getAll();
    }

    Lesson getLesson (int lessonId) throws Exception{
        return lessonDAO.get(lessonId);
    }


    @Override
    public void notifyObservers(int lessonId) throws Exception {
        ArrayList<Rider> observers = lessonDAO.getRidersForLesson(lessonId);
        for(Rider observer : observers){
            observer.update();
        }
    }
}
