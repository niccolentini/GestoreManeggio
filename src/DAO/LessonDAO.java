package DAO;

import DomainModel.Lesson;
import DomainModel.Rider;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


public class LessonDAO implements DAO <Lesson, Integer> {
    private Connection connection;
    ArenaDAO arenaDAO;
    RiderDAO riderDAO;
    TrainerDAO trainerDAO;

    public LessonDAO(Connection connection, ArenaDAO arenaDao, RiderDAO riderDAO, TrainerDAO trainerDAO) { //TODO: aggiungi nel controllore il trainerDAO e arenaDAO
        this.connection = connection;
        this.arenaDAO = arenaDao;
        this.riderDAO = riderDAO;
        this.trainerDAO = trainerDAO;
    }


    //TODO: da rivedere
    @Override
    public void add(Lesson less) throws SQLException {
        if (less instanceof Lesson) {
            Lesson lesson = (Lesson) less;
            try {
                String query = "INSERT INTO lesson (lessonId, arena, trainer, date, time) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setInt(1, lesson.getLessonId());
                    statement.setInt(2, lesson.getArena().getIdArena());
                    statement.setString(3, lesson.getTrainer().getFiscalCod());
                    statement.setDate(4, Date.valueOf(lesson.getDate()));
                    statement.setTime(5, Time.valueOf(lesson.getTime()));
                    statement.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //TODO: da rivedere
    @Override
    public void update(Lesson o) throws SQLException{
        if (o instanceof Lesson) {
            Lesson lesson = (Lesson) o;
            try {
                String query = "UPDATE lesson SET arena = ?, trainer = ?, date = ?, time = ? WHERE lessonId = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setInt(1, lesson.getArena().getIdArena());
                    statement.setString(2, lesson.getTrainer().getFiscalCod());
                    statement.setDate(3, Date.valueOf(lesson.getDate()));
                    statement.setTime(4, Time.valueOf(lesson.getTime()));
                    statement.setInt(5, lesson.getLessonId());
                    statement.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //TODO: da rivedere
    @Override
    public void remove(Integer integer) throws Exception {
        try {
            String query = "DELETE FROM lesson WHERE lessonId = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, integer);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TODO: da rivedere
    @Override
    public Lesson get(Integer id) throws Exception {
        try {
            String query = "SELECT * FROM lesson WHERE lessonId = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return extractLessonFromResultSet(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    //TODO: da rivedere
    @Override
    public ArrayList<Lesson> getAll() throws Exception{
        try {
            String query = "SELECT * FROM lesson";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                ArrayList<Lesson> lessons = new ArrayList<>();
                while (resultSet.next()) {
                    lessons.add(extractLessonFromResultSet(resultSet));
                }
                return lessons;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO: da rivedere
    private Lesson extractLessonFromResultSet(ResultSet resultSet) throws Exception {
        int lessonId = resultSet.getInt("lessonId");
        int arenaId = resultSet.getInt("arenaId");
        String trainerFiscalCode = resultSet.getString("trainer");
        LocalDate date = resultSet.getDate("date").toLocalDate();
        LocalTime time = resultSet.getTime("time").toLocalTime();

        return new Lesson(lessonId, arenaDAO.get(arenaId), trainerDAO.get(trainerFiscalCode), date, time);
        //fixme: passa a questo DAO tramite costruttore il DAO delle arene e dei trainer in modo da poter accedere tramite il loro id agli oggetti concreti
    }



    public ArrayList<Rider> getRidersForLesson(Integer lessonId) throws Exception {

        Connection connection = DriverManager.getConnection("jdbc:sqlite: " + "maneggio.db");
        String query = "SELECT * FROM lesson WHERE leddonId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, lessonId);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Rider> riders = new ArrayList<>();
        while (resultSet.next()) riders.add(riderDAO.get(resultSet.getString("fiscalCod")));
        resultSet.close();
        statement.close();
        connection.close();
        return riders;
    }

    public ArrayList<Lesson> getLessonsForRider(String fiscalCode) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite: " + "maneggio.db");
        String query = "SELECT * FROM lessons WHERE rider = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, fiscalCode);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Lesson> lessons = new ArrayList<>();
        while (resultSet.next()) lessons.add(this.get(resultSet.getInt("lessonId")));
        resultSet.close();
        statement.close();
        connection.close();
        return lessons;
    }

    public void addRiderToLesson(String fiscalCode, Integer lessonId) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite: " + "maneggio.db");
        String query = "INSERT OR IGNORE INTO bookings (lesson, rider) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, lessonId);
        statement.setString(2, fiscalCode);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    public boolean removeRiderFromLesson(String fiscalCode, Integer lessonId) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite: " + "maneggio.db");
        String query = "DELETE FROM bookings WHERE lesson = ? AND rider = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, lessonId);
        statement.setString(2, fiscalCode);
        int result = statement.executeUpdate();
        statement.close();
        connection.close();
        return result != 0;
    }


}
