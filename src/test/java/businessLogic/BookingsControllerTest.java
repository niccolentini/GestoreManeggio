package test.java.businessLogic;

import main.java.BusinessLogic.*;
import main.java.DAO.*;
import main.java.DomainModel.Horse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

class BookingsControllerTest {
    private BookingsController bookingsController;
    private LessonsController lessonsController;
    private RidersController ridersController;
    private TrainersController trainersController;
    private ArenasController arenasController;
    private int testLesson1Id;
    private int testLesson2Id;
    private String testRiderFiscalCode;

    @BeforeAll
    public void initDb() throws Exception {
        // Set up database
        StringBuilder resultStringBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/schema.sql"));
        String line;
        while ((line = br.readLine()) != null) {
            resultStringBuilder.append(line).append("\n");
        }

        Connection connection = DriverManager.getConnection("jdbc:sqlite: " + "maneggio.db");
        Statement stmt = DriverManager.getConnection("jdbc:sqlite: " + "maneggio.db").createStatement();
        int row = stmt.executeUpdate(resultStringBuilder.toString());

        stmt.close();
        connection.close();

        // Create DAOs
        HorseDAO horseDAO = new HorseDAO();
        RiderDAO riderDAO = new RiderDAO(horseDAO);
        TrainerDAO trainerDAO = new TrainerDAO();
        ArenaDAO arenaDAO = new ArenaDAO();
        LessonDAO lessonDAO = new LessonDAO(arenaDAO, riderDAO, trainerDAO);
        MembershipDAO membershipDAO = new MembershipDAO();

        // create Controllers
       this.trainersController = new TrainersController(trainerDAO);
       this.arenasController = new ArenasController(arenaDAO, lessonDAO);
       this.lessonsController = new LessonsController(lessonDAO, trainersController, arenasController);

       // create test data
        Horse testHorse1 = new Horse(1, "testHorse1", "fieno");
        Horse testHorse2 = new Horse(2, "testHorse2", "carote");
        Horse testHorse3 = new Horse(3, "testHorse3", "mela");
        horseDAO.add(testHorse1);
        horseDAO.add(testHorse2);
        horseDAO.add(testHorse3);
        ridersController.addRider("PEPPEP12", "Peppe", "Peppe", testHorse1, 2);
        ridersController.addRider("MARPOI11", "Marco", "Poi", testHorse2, 3);
        ridersController.addRider("REIEII", "Renzo", "Verza", testHorse3, 1);
        trainersController.addTrainer("LUCPAL22", "Luca", "Paoli");
        arenasController.addArena("Dante");
        lessonsController.addLesson(1, "LUCPAL22", LocalDate.now(), LocalTime.now());
        lessonsController.addLesson(1, "LUCPAL22", LocalDate.now(), LocalTime.now());


    }

    @BeforeEach
    public void resetDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite: " + "maneggio.db");
        // Delete data from all tables
        List<String> tables = Arrays.asList("trainers", "lessons", "riders", "memberships", "bookings");
        for (String table : tables) connection.prepareStatement("DELETE FROM " + table).executeUpdate();

        // Reset autoincrement counters
        connection.prepareStatement("DELETE FROM sqlite_sequence").executeUpdate();
        connection.close();
    }

    @Test
    public void Add_rider_already_booked_test() throws Exception { //testare l'aggiunta di un rider quando è già prenotato
        // TODO
    }

    @Test
    public void Remove_rider_test() throws Exception { //testare la rimozione di un rider da una lezione
        // TODO
    }

    @Test
    public void Add_rider_test() throws Exception { //testare l'aggiunta di un rider ad una lezione
        // TODO
    }

    @Test
    public void Add_rider_fullClass_test() throws Exception { //testare l'aggiunta di un rider ad una lezione piena
        // TODO
    }


}
