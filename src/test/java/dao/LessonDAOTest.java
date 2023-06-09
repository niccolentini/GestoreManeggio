package test.java.dao;

import main.java.DAO.*;
import main.java.DomainModel.*;
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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LessonDAOTest {

    private LessonDAO lessonDAO;
    private ArenaDAO arenaDAO = new ArenaDAO();
    private HorseDAO horseDAO = new HorseDAO();
    private RiderDAO riderDAO = new RiderDAO(horseDAO);
    private TrainerDAO trainerDAO = new TrainerDAO();
    private String data;
    private String ora;


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
        List<String> tables = Arrays.asList("trainers", "lessons", "riders", "memberships", "bookings", "horses", "horseboxes", "arenas", "sqlite_sequence");
        for (String table : tables) {
            connection.prepareStatement("DELETE FROM " + table).executeUpdate();
        }

        // Reset autoincrement counters
        connection.prepareStatement("DELETE FROM sqlite_sequence").executeUpdate();

        //Insert some test data
        connection.prepareStatement("INSERT INTO arenas (id, name, available) VALUES (1, 'name1', true)").executeUpdate();
        connection.prepareStatement("INSERT INTO arenas (id, name, available) VALUES (2, 'name2', true)").executeUpdate();
        connection.prepareStatement("INSERT INTO trainers (fiscalCode, firstName, lastName) VALUES ('AAAAAA11', 'name1', 'surname1')").executeUpdate();
        this.data = LocalDate.now().toString();
        this.ora = LocalTime.now().truncatedTo(ChronoUnit.HOURS).toString();
        connection.prepareStatement("INSERT INTO lessons (id, arena, trainer, date, time) VALUES (1, 1, 'AAAAAA11', '" + LocalDate.now() + "', '" + LocalTime.now() + "')").executeUpdate();
        connection.prepareStatement("INSERT INTO lessons (id, arena, trainer, date, time) VALUES (2, 1, 'AAAAAA11', '" + LocalDate.now() + "', '" + LocalTime.now().plusHours(1) + "')").executeUpdate();
        connection.prepareStatement("INSERT INTO lessons (id, arena, trainer, date, time) VALUES (3, 1, 'AAAAAA11', '" + this.data + "', '" + this.ora + "')").executeUpdate();
        connection.prepareStatement("INSERT INTO horses (id, name, info) VALUES (1, 'name1', 'info1')").executeUpdate();
        connection.prepareStatement("INSERT INTO riders (fiscalCode, firstName, lastName, horse) VALUES ('BBBBBB11', 'name1', 'surname1', 1)").executeUpdate();
        connection.prepareStatement("INSERT INTO bookings (rider, lesson) VALUES ('BBBBBB11', '1')").executeUpdate(); //aggiunto rider BBBBBB11 alla lezione 1
        connection.close();
    }


    @Test
    public void testAddLessonSuccess() throws Exception {
        Arena arena = arenaDAO.get(1);
        Trainer trainer = trainerDAO.get("AAAAAA11");
        Lesson lesson = new Lesson(5, arena, trainer, LocalDate.now(), LocalTime.now().plusHours(2)); //id non presente nel db
        Assertions.assertDoesNotThrow(() -> lessonDAO.add(lesson));
        Assertions.assertEquals(4, lessonDAO.getAll().size());
    }

    @Test
    public void testUpdateLessonSuccess() throws Exception {
        Arena arena = arenaDAO.get(1);
        Trainer trainer = trainerDAO.get("AAAAAA11");
        Lesson lesson = new Lesson(1, arena, trainer, LocalDate.now(), LocalTime.now().plusHours(5));   //modifico l'ora della lezione con id 1
        Assertions.assertDoesNotThrow(() -> lessonDAO.update(lesson));
        Assertions.assertEquals(3, lessonDAO.getAll().size());
    }

    @Test
    public void testRemoveSuccess() throws Exception {
        Assertions.assertDoesNotThrow(() -> lessonDAO.remove(1));
        Assertions.assertEquals(2, lessonDAO.getAll().size());
    }

    @Test
    public void testRemoveFail() {
        Assertions.assertThrows(Exception.class, () -> lessonDAO.remove(5));
    }

    @Test
    public void testGet() throws Exception {
        Lesson lesson = lessonDAO.get(1);
        Assertions.assertEquals(1, lesson.getLessonId());
        Assertions.assertEquals(1, lesson.getArena().getIdArena());
        Assertions.assertEquals("AAAAAA11", lesson.getTrainer().getFiscalCod());
        Assertions.assertEquals(LocalDate.now().toString(), lesson.getDate().toString());
    }

    @Test
    public void testGetAll() throws Exception {
        List<Lesson> lessons = lessonDAO.getAll();
        Assertions.assertEquals(3, lessons.size());
        Assertions.assertEquals(1, lessons.get(0).getLessonId());
        Assertions.assertEquals(2, lessons.get(1).getLessonId());
    }

    @Test
    public void testGetRidersForLesson() throws Exception {
        ArrayList<Rider> riders = lessonDAO.getRidersForLesson(1);
        Assertions.assertEquals(1, riders.size());
    }

    @Test
    public void testGetLessonsForRider() throws Exception {
        ArrayList<Lesson> lessons = lessonDAO.getLessonsForRider("BBBBBB11");
        Assertions.assertEquals(1, lessons.size());
    }

    @Test
    public void testAddRiderToLessonSuccess() throws Exception {
        lessonDAO.addRiderToLesson("BBBBBB11", 2);
        Assertions.assertEquals(1, lessonDAO.getRidersForLesson(2).size());
    }

    @Test
    public void testAddRiderToLessonFail() {
        Assertions.assertThrows(Exception.class, () -> lessonDAO.addRiderToLesson("BBBBBB11", 1)); //rider già presente nella lezione
        Assertions.assertThrows(Exception.class, () -> lessonDAO.addRiderToLesson("BBBBBB11", 5)); //lezione non presente nel db
    }

    @Test
    public void testRemoveRiderFromLessonSuccess() throws Exception{
        lessonDAO.removeRiderFromLesson("BBBBBB11", 1);
        Assertions.assertEquals(0, lessonDAO.getRidersForLesson(1).size());
    }

    @Test
    public void testRemoveRiderFromLessonFail() {
        Assertions.assertThrows(Exception.class, () -> lessonDAO.removeRiderFromLesson("BBBBBB11", 2)); //rider non presente nella lezione
    }

    @Test
    public void testIsArenaBookedForLesson() throws Exception{
        Assertions.assertTrue(lessonDAO.isArenaBookedForLesson(1)); //arena1 ha lezioni prenotate
        Assertions.assertFalse(lessonDAO.isArenaBookedForLesson(2)); //arena2 non ha lezioni prenotate
    }

    @Test
    public void testIsArenaBookedTimeDate() throws Exception{
        Assertions.assertTrue(lessonDAO.isArenaBookedAtTimeDate(1, LocalDate.parse(this.data), LocalTime.parse(this.ora)));
        Assertions.assertFalse(lessonDAO.isArenaBookedAtTimeDate(2, LocalDate.now(), LocalTime.now().truncatedTo(ChronoUnit.HOURS)));
    }
}
