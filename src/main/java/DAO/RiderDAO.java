package main.java.DAO;

import main.java.DomainModel.Rider;

import java.sql.*;
import java.util.ArrayList;

public class RiderDAO implements DAO<Rider, String> {
    private HorseDAO horseDAO;
    public RiderDAO(HorseDAO horseDAO) {
        this.horseDAO = horseDAO;
    }

    public void add(Rider rider) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        PreparedStatement addRider = connection.prepareStatement("INSERT INTO rider (fiscalCode, firstName, lastName, horse) VALUES (?, ?, ?, ?)");
        addRider.setString(1, rider.getFiscalCod());
        addRider.setString(2, rider.getFirstName());
        addRider.setString(3, rider.getLastName());
        addRider.setInt(4, rider.getHorse().getHorseId());
        addRider.executeUpdate();
        addRider.close();
        connection.close();
    }

    @Override
    public void update(Rider rider) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        PreparedStatement updateRider = connection.prepareStatement("UPDATE riders SET firstName, lastName, horse WHERE fiscalCode = ?");
        updateRider.setString(1, rider.getFirstName());
        updateRider.setString(2, rider.getLastName());
        updateRider.setInt(3, rider.getHorse().getHorseId());
        updateRider.setString(4, rider.getFiscalCod());
        updateRider.executeUpdate();
        updateRider.close();
        connection.close();
    }

    @Override
    public void remove(String fisCod) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        PreparedStatement deleteRider = connection.prepareStatement("DELETE FROM riders WHERE fiscalCode = ?");
        deleteRider.setString(1, fisCod);
        deleteRider.executeUpdate();
        deleteRider.close();
        connection.close();
    }

    public Rider get(String fisCod) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        PreparedStatement getRider = connection.prepareStatement("SELECT * FROM riders WHERE fiscalCode = ?");
        getRider.setString(1, fisCod);
        ResultSet rs = getRider.executeQuery();
        Rider r = null;
        if(rs.next()){
            r = new Rider(rs.getString("fiscalCode"), rs.getString("firstName"), rs.getString("lastName"), horseDAO.get(rs.getInt("horse")));
        }
        getRider.executeUpdate();
        getRider.close();
        connection.close();
        return r;
    }

    public ArrayList<Rider> getAll() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        Statement getAllRiders = connection.createStatement();
        ResultSet rs = getAllRiders.executeQuery("SELECT * FROM riders");
        ArrayList<Rider> riders = new ArrayList<>();
        while(rs.next()){
            Rider r = new Rider(rs.getString("fiscalCode"), rs.getString("firstName"), rs.getString("lastName"), horseDAO.get(rs.getInt("horse")));
            riders.add(r);
        }
        getAllRiders.close();
        connection.close();
        return riders;
    }
}
