package DAO;

import DomainModel.Lesson;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class LessonDAO implements DAO <Lesson, Integer> {
    private Connection connection;

    public LessonDAO(Connection connection) { //TODO: aggiungi nel controllore il trainerDAO e arenaDAO
        this.connection = connection;
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
    public ArrayList<Lesson> getAll() {
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

    private Lesson extractLessonFromResultSet(ResultSet resultSet) throws SQLException {
        int lessonId = resultSet.getInt("lessonId");
        String arena = resultSet.getString("arena");
        int trainer = resultSet.getInt("trainer");
        LocalDate date = resultSet.getDate("date").toLocalDate();
        LocalTime time = resultSet.getTime("time").toLocalTime();
        return new Lesson(lessonId, ArenaDAO.get(arena), trainer, date, time);
        //TODO: passa a questo DAO tramite costruttore il DAO delle arene e dei trainer in modo da poter accedere tramite il loro id agli oggetti concreti
    }
}
