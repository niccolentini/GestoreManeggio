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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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

        Connection connection = DriverManager.getConnection("jdbc:sqlite: " + "maneggio.db");
        Statement stmt = DriverManager.getConnection("jdbc:sqlite: " + "maneggio.db").createStatement();
        stmt.executeUpdate(resultStringBuilder.toString());

        stmt.close();
        connection.close();

    }

    @BeforeEach
    public void initDb() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite: " + "maneggio.db");

        // Delete data from lessons table
        List<String> tables = Arrays.asList("trainers", "lessons", "riders", "horses", "bookings");
        for (String table : tables) connection.prepareStatement("DELETE FROM " + table).executeUpdate();

        // Reset autoincrement counters
        connection.prepareStatement("DELETE FROM sqlite_sequence").executeUpdate();

        //Insert some test data
        connection.prepareStatement("INSERT INTO arenas (id, name, avaiable) VALUES (1, 'name1', true)").executeUpdate();
        connection.prepareStatement("INSERT INTO arenas (id, name, avaiable) VALUES (2, 'name2', true)").executeUpdate();
        connection.prepareStatement("INSERT INTO trainers (fiscalCode, firstName, lastName) VALUES ('AAAAAA11', 'name1', 'surname1')").executeUpdate();
        connection.prepareStatement("INSERT INTO lessons (id, arena, trainer, date, time) VALUES (1, 2, 'AAAAAA11',  LocalDate.now(), LocalTime.now())").executeUpdate();
        connection.close();
    }


    @Test
    public void testDisableArenaSuccess() throws Exception{
        arenasController.disableArena(1);
        Assertions.assertFalse(arenaDAO.get(1).isAvailable());
    }

    @Test
    public void testDisableArenaFail(){
        Assertions.assertThrows(Exception.class, () -> arenasController.disableArena(3));
    }

    @Test
    public void testEnableArenaSuccess() throws Exception{
        arenasController.disableArena(1);
        arenasController.enableArena(1);
        Assertions.assertTrue(arenaDAO.get(1).isAvailable());
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
