package test.java.businessLogic;

import main.java.BusinessLogic.*;
import main.java.DAO.*;
import main.java.DomainModel.Horse;
import main.java.DomainModel.Lesson;
import main.java.DomainModel.Rider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
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
    private Rider testRider1;
    private Rider testRider2;
    private Rider testRider3;
    private Rider testRider4;

    @BeforeAll
    static void setUpDb() throws Exception {
        // Set up database
        StringBuilder resultStringBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/schema.sql"));
        String line;
        while ((line = br.readLine()) != null) {
         resultStringBuilder.append(line).append("\n");
        }
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        Statement stmt = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db").createStatement();
        int row = stmt.executeUpdate(resultStringBuilder.toString());
        stmt.close();
        connection.close();

    }

    public static void createDatabaseFromSchema(String schemaFilePath, String databaseName) {
        String url = "jdbc:sqlite:" + databaseName;

        try (Connection conn = DriverManager.getConnection(url)) {
            // Create an empty database file if it doesn't exist
            conn.createStatement().execute("CREATE TABLE IF NOT EXISTS dummy_table (id INTEGER)");

            // Read and execute the schema file
            try (BufferedReader reader = new BufferedReader(new FileReader(schemaFilePath));
                 Statement stmt = conn.createStatement()) {
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    // Remove leading/trailing white spaces and skip empty lines
                    line = line.trim();
                    if (line.isEmpty()) {
                        continue;
                    }

                    // Append the line to the SQL statement
                    sb.append(line);

                    // If the line ends with a semicolon, execute the statement
                    if (line.endsWith(";")) {
                        String sql = sb.toString();
                        stmt.execute(sql);
                        sb.setLength(0); // Clear the StringBuilder for the next statement
                    }
                }
                System.out.println("Database structure updated successfully.");
            } catch (Exception e) {
                System.err.println("Error updating database structure: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Error connecting to database: " + e.getMessage());
        }
    }


    @BeforeEach
    public void initDb() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        // Delete data from all tables
        List<String> tables = Arrays.asList("trainers", "lessons", "riders","memberships","bookings", "horses","horseboxes", "arenas", "sqlite_sequence");
        for (String table : tables) connection.prepareStatement("DELETE FROM " + table).executeUpdate();

        // Reset autoincrement counters
        connection.prepareStatement("DELETE FROM sqlite_sequence").executeUpdate();
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
        this.ridersController = new RidersController(riderDAO);
        this.bookingsController = new BookingsController(lessonDAO, lessonsController, ridersController , membershipDAO);

        // create test data
        Horse testHorse1 = new Horse(1, "testHorse1", "fieno");
        Horse testHorse2 = new Horse(2, "testHorse2", "carote");
        Horse testHorse3 = new Horse(3, "testHorse3", "mela");
        Horse testHorse4 = new Horse(4, "testHorse4", "zucchine");
        testRider1 = new Rider("PEPPEP12", "Peppe", "Peppe", testHorse1);
        testRider2 = new Rider("MARPOI11", "Marco", "Poi", testHorse2);
        testRider3 = new Rider("REIEII33", "Renzo", "Verza", testHorse3);
        testRider4 = new Rider("GIOGIO44", "Giorgio", "Giorgio", testHorse4);
        horseDAO.add(testHorse1);
        horseDAO.add(testHorse2);
        horseDAO.add(testHorse3);
        horseDAO.add(testHorse4);
        ridersController.addRider("PEPPEP12", "Peppe", "Peppe", testHorse1, 1);
        ridersController.addRider("MARPOI11", "Marco", "Poi", testHorse2, 2);
        ridersController.addRider("REIEII33", "Renzo", "Verza", testHorse3, 3);
        ridersController.addRider("GIOGIO44", "Giorgio", "Giorgio", testHorse4, 4);
        trainersController.addTrainer("LUCPAL22", "Luca", "Paoli");
        arenasController.addArena("Dante");
        arenasController.addArena("Alighieri");
        lessonsController.addLesson(1, "LUCPAL22", LocalDate.now(), LocalTime.now());
        lessonsController.addLesson(2, "LUCPAL22", LocalDate.now(), LocalTime.now().plusHours(2));
        testLesson1Id = 1;
        testLesson2Id = 2;
    }

    @Test
    public void Add_rider_already_booked_test() throws Exception { //testare l'aggiunta di un rider quando è già prenotato
        bookingsController.addRiderToLesson(testRider1.getFiscalCod(), testLesson1Id);
        Assertions.assertThrows(
                RuntimeException.class,
                () -> bookingsController.addRiderToLesson(testRider1.getFiscalCod(), testLesson1Id),
                "Expected RuntimeException to be thrown but it wasn't"
        );
    }

    @Test
    public void Remove_rider_test() throws Exception { //testare la rimozione di un rider da una lezione
        bookingsController.addRiderToLesson(testRider1.getFiscalCod(), testLesson1Id);
        if(bookingsController.getLessonsForRider(testRider1.getFiscalCod()).size() == 0) throw new Exception("Rider not added to lesson");
        else {
            bookingsController.removeRiderFromLesson(testRider1.getFiscalCod(), testLesson1Id);
            Assertions.assertEquals(0, bookingsController.getLessonsForRider(testRider1.getFiscalCod()).size());
        }
    }

    @Test
    public void Add_rider_test() throws Exception { //testare l'aggiunta di un rider ad una lezione
        bookingsController.addRiderToLesson(testRider1.getFiscalCod(), testLesson1Id);
        Lesson l = bookingsController.getLessonsForRider(testRider1.getFiscalCod()).get(0);
        Assertions.assertEquals(1, l.getLessonId());
    }

    @Test
    public void Add_rider_fullClass_test() throws Exception { //testare l'aggiunta di un rider ad una lezione piena
        bookingsController.addRiderToLesson(testRider1.getFiscalCod(), testLesson1Id);
        bookingsController.addRiderToLesson(testRider2.getFiscalCod(), testLesson1Id);
        bookingsController.addRiderToLesson(testRider3.getFiscalCod(), testLesson1Id);
        Assertions.assertThrows(
                RuntimeException.class,
                () -> bookingsController.addRiderToLesson(testRider4.getFiscalCod(), testLesson1Id),
                "Expected RuntimeException to be thrown but it wasn't"
        );
    }

    @Test
    public void Add_rider_notExisting_test() throws Exception { //testare l'aggiunta di un rider ad una lezione che non esiste
        Assertions.assertThrows(
                RuntimeException.class,
                () -> bookingsController.addRiderToLesson(testRider1.getFiscalCod(), 100),
                "Expected RuntimeException to be thrown but it wasn't"
        );
    }

    @Test
    public void Add_rider_to_multiple_lessons() throws Exception {
        bookingsController.addRiderToLesson(testRider1.getFiscalCod(), testLesson1Id);
        bookingsController.addRiderToLesson(testRider1.getFiscalCod(), testLesson2Id);
        List<Lesson> lessons = bookingsController.getLessonsForRider(testRider1.getFiscalCod());
        Assertions.assertEquals(2, lessons.size());
    }


}
