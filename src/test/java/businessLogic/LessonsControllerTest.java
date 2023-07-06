package test.java.businessLogic;

import main.java.BusinessLogic.ArenasController;
import main.java.BusinessLogic.LessonsController;
import main.java.BusinessLogic.TrainersController;
import main.java.DAO.*;
import main.java.DomainModel.Arena;
import main.java.DomainModel.Lesson;
import main.java.DomainModel.Trainer;
import org.junit.jupiter.api.Assertions;
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

public class LessonsControllerTest {

    private LessonDAO lessonDAO;
    ArenaDAO arenaDAO = new ArenaDAO();
    HorseDAO horseDAO = new HorseDAO();
    RiderDAO riderDAO = new RiderDAO(horseDAO);
    TrainerDAO trainerDAO = new TrainerDAO();
    TrainersController trainersController = new TrainersController(trainerDAO);
    ArenasController arenasController = new ArenasController(arenaDAO, lessonDAO);

    LessonsController lessonsController = new LessonsController(lessonDAO, trainersController, arenasController);

    @BeforeAll
    static void setUpDb() throws SQLException, IOException {
        // Set up database
        StringBuilder resultStringBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/schema.sql"));
        String line;
        while ((line = br.readLine()) != null) {
            resultStringBuilder.append(line).append("\n");
        }

        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        Statement stmt = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db").createStatement();
        stmt.executeUpdate(resultStringBuilder.toString());

        stmt.close();
        connection.close();
    }

    @BeforeEach
    public void initDb() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        lessonDAO = new LessonDAO(arenaDAO, riderDAO, trainerDAO);

        // Delete data from lessons table
        List<String> tables = Arrays.asList("trainers", "lessons", "riders","memberships","bookings", "horses","horseboxes", "arenas", "sqlite_sequence");
        for (String table : tables) connection.prepareStatement("DELETE FROM " + table).executeUpdate();

        // Reset autoincrement counters
        connection.prepareStatement("DELETE FROM sqlite_sequence").executeUpdate();

        //Insert some test data
        connection.prepareStatement("INSERT INTO arenas (id, name, available) VALUES (1, 'name1', 1)").executeUpdate();
        connection.prepareStatement("INSERT INTO arenas (id, name, available) VALUES (2, 'name2', 0)").executeUpdate();
        connection.prepareStatement("INSERT INTO trainers (fiscalCode, firstName, lastName) VALUES ('AAAAAA11', 'name1', 'surname1')").executeUpdate();
        connection.prepareStatement("INSERT INTO lessons (id, arena, trainer, date, time) VALUES (1, 1, 'AAAAAA11', '" + LocalDate.now() + "', '" + LocalTime.now() + "')").executeUpdate();
        connection.prepareStatement("INSERT INTO lessons (id, arena, trainer, date, time) VALUES (2, 1, 'AAAAAA11', '" + LocalDate.now() + "', '" + LocalTime.now().plusHours(1) + "')").executeUpdate();

        connection.close();
    }

    @Test
    public void testAddLessonArenaNotAvailable(){
        //arena 2 is not available
        Assertions.assertThrows(Exception.class, () -> lessonsController.addLesson(2, "AAAAAA11", LocalDate.now(), LocalTime.now().plusHours(2)));
    }

    @Test
    public void testAddLessonArenaBooked() {
        //arena 1 is available but booked at this time and date
        Assertions.assertThrows(Exception.class, () -> lessonsController.addLesson(1, "AAAAAA11", LocalDate.now(), LocalTime.now().plusHours(1)));
    }

}
