package test.java.businessLogic;

import main.java.BusinessLogic.ArenasController;
import main.java.DAO.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class ArenasControllerTest {

    ArenaDAO arenaDAO = new ArenaDAO();
    TrainerDAO trainerDAO = new TrainerDAO();
    HorseDAO horseDAO = new HorseDAO();
    RiderDAO riderDAO = new RiderDAO(horseDAO);
    LessonDAO lessonDAO = new LessonDAO(arenaDAO, riderDAO, trainerDAO);
    ArenasController arenasController = new ArenasController(arenaDAO, lessonDAO);

    @BeforeAll
    static void setUpDb() throws SQLException, IOException {
        // Set up database
        StringBuilder resultStringBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/schema.sql"));
        String line;
        while ((line = br.readLine()) != null) {
            resultStringBuilder.append(line).append("\n");
        }

        Connection connection = DriverManager.getConnection("jdbc:sqlite:maneggio.db");
        Statement stmt = DriverManager.getConnection("jdbc:sqlite:maneggio.db").createStatement();
        stmt.executeUpdate(resultStringBuilder.toString());

        stmt.close();
        connection.close();

    }

    @BeforeEach
    public void initDb() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:maneggio.db");

        // Delete data from lessons table
        List<String> tables = Arrays.asList("trainers", "lessons", "riders", "horses", "bookings");
        for (String table : tables) {
            connection.prepareStatement("DELETE FROM " + table).executeUpdate();
        }

        // Reset autoincrement counters
        connection.prepareStatement("DELETE FROM sqlite_sequence").executeUpdate();

        // Insert some test data
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        String insertArenaQuery = "INSERT INTO arenas (name, available) VALUES (?, ?)";
        PreparedStatement insertArenaStatement = connection.prepareStatement(insertArenaQuery);
        insertArenaStatement.setString(1, "name1");
        insertArenaStatement.setInt(2, 1);
        insertArenaStatement.executeUpdate();

        insertArenaStatement.setString(1, "name2");
        insertArenaStatement.setInt(2, 1);
        insertArenaStatement.executeUpdate();

        String insertTrainerQuery = "INSERT INTO trainers (fiscalCode, firstName, lastName) VALUES (?, ?, ?)";
        PreparedStatement insertTrainerStatement = connection.prepareStatement(insertTrainerQuery);
        insertTrainerStatement.setString(1, "AAAAAA11");
        insertTrainerStatement.setString(2, "name1");
        insertTrainerStatement.setString(3, "surname1");
        insertTrainerStatement.executeUpdate();

        String insertLessonQuery = "INSERT INTO lessons (id, arena, trainer, date, time) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement insertLessonStatement = connection.prepareStatement(insertLessonQuery);
        insertLessonStatement.setInt(1, 1);
        insertLessonStatement.setInt(2, 2);
        insertLessonStatement.setString(3, "AAAAAA11");
        insertLessonStatement.setString(4, currentDate.toString());
        insertLessonStatement.setString(5, currentTime.toString());
        insertLessonStatement.executeUpdate();

        connection.close();
    }


    @Test
    public void testDisableArenaSuccess() throws Exception{
        arenasController.disableArena(1);
        Assertions.assertNotEquals(1, arenaDAO.get(1).isAvailable());
    }

    @Test
    public void testDisableArenaFail(){
        Assertions.assertThrows(Exception.class, () -> arenasController.disableArena(3));
    }

    @Test
    public void testEnableArenaSuccess() throws Exception{
        arenasController.disableArena(1);
        arenasController.enableArena(1);
        Assertions.assertEquals(1, arenaDAO.get(1).isAvailable());
    }

    @Test
    public void testEnableArenaFail() {
        Assertions.assertThrows(Exception.class, () -> arenasController.enableArena(1)); //already available
    }

    @Test
    public void testDisableArenaBookedForLessons() {
        Assertions.assertThrows(Exception.class, () -> arenasController.disableArena(2));
    }
}
