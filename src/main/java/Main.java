package main.java;


import main.java.BusinessLogic.*;
import main.java.DAO.*;
import main.java.DomainModel.Horse;
import main.java.DomainModel.HorseBox;
import main.java.DomainModel.Lesson;
import main.java.DomainModel.Rider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        // Delete data from all tables
        List<String> tables = Arrays.asList("trainers", "lessons", "riders","memberships","bookings", "horses","horseboxes", "arenas", "sqlite_sequence");
        for (String table : tables) connection.prepareStatement("DELETE FROM " + table).executeUpdate();

        // Reset autoincrement counters
        connection.prepareStatement("DELETE FROM sqlite_sequence").executeUpdate();
        connection.close();

        // DAOs
        TrainerDAO trainerDAO = new TrainerDAO();
        HorseDAO horseDAO = new HorseDAO();
        RiderDAO riderDAO = new RiderDAO(horseDAO);
        MembershipDAO membershipDAO = new MembershipDAO();
        ArenaDAO arenaDAO = new ArenaDAO();
        LessonDAO lessonDAO = new LessonDAO(arenaDAO, riderDAO, trainerDAO);
        HorseBoxDAO horseBoxDAO = new HorseBoxDAO();

        // Controllers
        TrainersController trainersController = new TrainersController(trainerDAO);
        ArenasController arenasController = new ArenasController(arenaDAO, lessonDAO);
        HorseBoxController horseBoxController = new HorseBoxController(horseBoxDAO);
        RidersController ridersController = new RidersController(riderDAO, membershipDAO);
        LessonsController lessonsController = new LessonsController(lessonDAO, trainersController, arenasController);
        BookingsController bookingsController = new BookingsController(lessonDAO, lessonsController, ridersController, membershipDAO);

        // Example Horses
        Horse h1 = new Horse(1, "Atlante", "Fieno e carruba.");
        Horse h2 = new Horse(2, "Bucefalo", "Mangime e carote.");
        horseDAO.add(h1);
        horseDAO.add(h2);

        // Example Riders
        ridersController.addRider("AAABBB00", "Antonio", "Parigi", h1, 1);
        ridersController.addRider("CCCDDD11", "Bianca", "Roma", h2, 1);

        // Example Trainers
        trainersController.addTrainer("EEFFGG22", "Carlo", "Milano");
        trainersController.addTrainer("HHIIJJ33", "Dario", "Torino");

        // Example Arenas
        arenasController.addArena("Arena Dante");
        arenasController.addArena("Arena Petrarca");

        // Example Lessons
        lessonsController.addLesson(1, "EEFFGG22", LocalDate.parse("2023-07-06"), LocalTime.parse("10:00"));
        lessonsController.addLesson(2, "HHIIJJ33", LocalDate.parse("2021-07-09"), LocalTime.parse("09:00"));

        bookingsController.addRiderToLesson("AAABBB00", 1);
        bookingsController.addRiderToLesson("CCCDDD11", 1);
        bookingsController.addRiderToLesson("CCCDDD11", 2);

        ArrayList<Lesson> lfr = bookingsController.getLessonsForRider("CCCDDD11");
        System.out.println("Lezioni prenotate da Bianca Roma:");
        for (Lesson l : lfr) {
            System.out.println(l.getArena().getName()+" "+l.getDate()+" "+l.getTime());
        }

        ArrayList<Lesson> all_less = lessonsController.getAllLessons();
        System.out.println("\n\nLista di tutte le lezioni programmate:");
        for (Lesson l : all_less) {
            System.out.println(l.getArena().getName()+" "+l.getDate()+" "+l.getTime());
        }

        ArrayList<Rider> all_rid = ridersController.getAllRiders();
        System.out.println("\n\nLista di tutti i cavalieri del maneggio:");
        for (Rider r : all_rid) {
            System.out.println(r.getFirstName()+" "+r.getLastName()+". In sella a "+r.getHorse().getName());
        }

    }
}
