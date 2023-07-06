package main.java.DAO;

import main.java.DomainModel.Lesson;
import main.java.DomainModel.Rider;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


public class LessonDAO implements DAO <Lesson, Integer> {
    ArenaDAO arenaDAO;
    RiderDAO riderDAO;
    TrainerDAO trainerDAO;

    public LessonDAO(ArenaDAO arenaDao, RiderDAO riderDAO, TrainerDAO trainerDAO) {

        this.arenaDAO = arenaDao;
        this.riderDAO = riderDAO;
        this.trainerDAO = trainerDAO;
    }



    @Override
    public void add(Lesson lesson) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        PreparedStatement statement = connection.prepareStatement("INSERT INTO lessons ( arena, trainer, date, time) VALUES (?,?,?,?)");
        //id is auto-generated, so it's not needed
        statement.setInt(1, lesson.getArena().getIdArena());
        statement.setString(2, lesson.getTrainer().getFiscalCod());
        statement.setString(3, lesson.getDate().toString());
        statement.setString(4, lesson.getTime().toString());
        statement.executeUpdate();
        statement.close();
        connection.close();
    }


    @Override
    public void update(Lesson lesson) throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        PreparedStatement statement = connection.prepareStatement("UPDATE lessons SET arena = ?, trainer = ?, date = ?, time = ? WHERE id = ?");
        statement.setInt(1, lesson.getArena().getIdArena());
        statement.setString(2, lesson.getTrainer().getFiscalCod());
        statement.setString(3, lesson.getDate().toString());
        statement.setString(4, lesson.getTime().toString());
        statement.setInt(5, lesson.getLessonId());
        statement.executeUpdate();
        statement.close();
        connection.close();
    }


    @Override
    public void remove(Integer lessonId) throws Exception, SQLException {
        Connection connection = null;
        PreparedStatement selectStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
            selectStatement = connection.prepareStatement("SELECT id FROM lessons WHERE id = ?");
            selectStatement.setInt(1, lessonId);
            ResultSet resultSet = selectStatement.executeQuery();

            // Verifica se il lessonId esiste
            if (!resultSet.next()) {
                throw new Exception("Lesson not found: " + lessonId);
            }

            // Procedi con la rimozione della lezione
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM lessons WHERE id = ?");
            deleteStatement.setInt(1, lessonId);
            deleteStatement.executeUpdate();
            deleteStatement.close();
        } finally {
            if (selectStatement != null) {
                selectStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }



    @Override
    public Lesson get(Integer lessonId) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        Lesson lesson = null;
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM lessons WHERE id = ?");
        statement.setInt(1, lessonId);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next())
        {
            lesson = new Lesson(
                    lessonId,
                    arenaDAO.get(resultSet.getInt("arena")), //restituisce l'arena con id = resultSet.getInt("arena")
                    trainerDAO.get(resultSet.getString("trainer")),
                    LocalDate.parse(resultSet.getString("date")),
                    LocalTime.parse(resultSet.getString("time"))
            );
        }
        resultSet.close();
        statement.close();
        connection.close();
        return lesson;

    }


    @Override
    public ArrayList<Lesson> getAll() throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM lessons");
        ArrayList<Lesson> lessons = new ArrayList<>();
        while (resultSet.next()) {
            lessons.add(new Lesson(
                    resultSet.getInt("id"),
                    arenaDAO.get(resultSet.getInt("arena")),
                    trainerDAO.get(resultSet.getString("trainer")),
                    LocalDate.parse(resultSet.getString("date")),
                    LocalTime.parse(resultSet.getString("time"))
            ));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return lessons;
    }


    public ArrayList<Rider> getRidersForLesson(Integer lessonId) throws Exception {

        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        String query = "SELECT * FROM bookings WHERE lesson = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, lessonId);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Rider> riders = new ArrayList<>();
        while (resultSet.next()) riders.add(riderDAO.get(resultSet.getString("rider")));
        resultSet.close();
        statement.close();
        connection.close();
        return riders;
    }

    public ArrayList<Lesson> getLessonsForRider(String fiscalCode) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        String query = "SELECT * FROM bookings WHERE rider = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, fiscalCode);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Lesson> lessons = new ArrayList<>();
        while (resultSet.next()) lessons.add(this.get(resultSet.getInt("lesson")));
        resultSet.close();
        statement.close();
        connection.close();
        return lessons;
    }

    public void addRiderToLesson(String fiscalCode, Integer lessonId) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        String query = "INSERT OR IGNORE INTO bookings (lesson, rider) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, lessonId);
        statement.setString(2, fiscalCode);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    public boolean removeRiderFromLesson(String fiscalCode, Integer lessonId) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        String query = "DELETE FROM bookings WHERE lesson = ? AND rider = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, lessonId);
        statement.setString(2, fiscalCode);
        int result = statement.executeUpdate();
        statement.close();
        connection.close();
        return result != 0;
    }

    public boolean isArenaBookedForLesson(int idArena) throws Exception {
        //restituisce true se l'arena è prenotata per almeno una lezione
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        String query = "SELECT * FROM lessons WHERE arena = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idArena);
        ResultSet resultSet = statement.executeQuery();
        boolean result = resultSet.next();
        resultSet.close();
        statement.close();
        connection.close();
        return result;
    }

    public boolean isArenaBookedAtTimeDate(int idArena, LocalDate date, LocalTime time) throws Exception {
        //restituisce true se l'arena è prenotata per quella data e ora
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        String query = "SELECT * FROM lessons WHERE arena = ? AND date = ? AND time = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idArena);
        statement.setString(2, date.toString());
        statement.setString(3, time.toString());
        ResultSet resultSet = statement.executeQuery();
        boolean result = resultSet.next(); //restituisce false se non c'è nessuna riga
        resultSet.close();
        statement.close();
        connection.close();
        return result;
    }

    public int getNextId() throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        String query = "SELECT MAX(id) FROM lessons";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        int id = rs.getInt(1) + 1;
        rs.close();
        statement.close();
        connection.close();
        return id;
    }
}
