package DAO;

import DomainModel.Horse;
import DomainModel.Rider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RiderDAO {
    private Connection connection;

    public RiderDAO(Connection connection) {
        this.connection = connection;
    }
    //TODO: devi dare a questo DAO il horseDAO in modo che il rider possa avere un cavallo di tipo Horse

    public void createRider(Rider rider) throws SQLException {
        String query = "INSERT INTO rider (fiscalCod, firstName, lastName, horse) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, rider.getFiscalCod());
            statement.setString(2, rider.getFirstName());
            statement.setString(3, rider.getLastName());
            statement.setString(4, rider.getHorse()); //TODO: qui dovrai avere getHorse.getName() e in fondo avere il collegamento al horse.dao
            statement.executeUpdate();
        }
    }

    public void updateRider(Rider rider) throws SQLException {
        String query = "UPDATE rider SET fiscalCod = ?, firstName = ?, lastName = ?, horse = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, rider.getFiscalCod());
            statement.setString(2, rider.getFirstName());
            statement.setString(3, rider.getLastName());
            statement.setString(4, rider.getHorse());
            statement.executeUpdate();
        }
    }

    public void deleteRider(String fisCod) throws SQLException {
        String query = "DELETE FROM rider WHERE fiscalCod = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, fisCod);
            statement.executeUpdate();
        }
    }

    public Rider getRiderByFisCod(String fisCod) throws SQLException {
        String query = "SELECT * FROM rider WHERE fiscalCod = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, fisCod);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractRiderFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    public List<Rider> getAllRiders() throws SQLException {
        String query = "SELECT * FROM rider";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            List<Rider> riders = new ArrayList<>();
            while (resultSet.next()) {
                riders.add(extractRiderFromResultSet(resultSet));
            }
            return riders;
        }
    }

    private Rider extractRiderFromResultSet(ResultSet resultSet) throws SQLException {
        String fisCod = resultSet.getString("fiscalCod");
        String fstName = resultSet.getString("firstName");
        String lstName = resultSet.getString("lastName");
        String horse = resultSet.getString("horse");                          //fixme: devi cambiare questo horse
        return new Rider(fisCod, fstName, lstName, horse);
    }
}
