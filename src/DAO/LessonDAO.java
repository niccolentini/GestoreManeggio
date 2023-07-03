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

    public LessonDAO(Connection connection, ArenaDAO arenaDao, RiderDAO riderDAO) { //TODO: aggiungi nel controllore il trainerDAO e arenaDAO
        this.connection = connection;
        this.arenaDAO = arenaDao;
        this.riderDAO = riderDAO;
    }

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

    public ArrayList<Rider> getRidersForLesson(Integer lessonId) throws Exception {

        //Connection connection = Database.getConnection(); //TODO vedi andre e simo che aprono sempre la connessione e poi la chiudono. Necessaria una classe Database
        String query = "SELECT * FROM lesson WHERE leddonId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, lessonId);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Rider> riders = new ArrayList<>();
        while (resultSet.next()) riders.add(riderDAO.get(resultSet.getString("fiscalCod")));
        resultSet.close();
        statement.close();
        //Database.closeConnection(connection);
        return riders;
    }

    private Lesson extractLessonFromResultSet(ResultSet resultSet) throws Exception {
        int lessonId = resultSet.getInt("lessonId");
        int arenaId = resultSet.getInt("arenaId");
        int trainer = resultSet.getInt("trainer"); //TODO: risolvere, non vuole un int vuole un trainer la Lesson
        LocalDate date = resultSet.getDate("date").toLocalDate();
        LocalTime time = resultSet.getTime("time").toLocalTime();
        return new Lesson(lessonId, arenaDAO.get(arenaId), trainer, date, time);
        //TODO: passa a questo DAO tramite costruttore il DAO delle arene e dei trainer in modo da poter accedere tramite il loro id agli oggetti concreti
    }
}
