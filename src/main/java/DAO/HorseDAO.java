package main.java.DAO;
import main.java.DomainModel.Horse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class HorseDAO implements DAO<Horse, Integer> {
    @Override
    public void add(Horse horse) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        PreparedStatement insertHorse = connection.prepareStatement("INSERT INTO horses (id, name, info) VALUES (?, ?, ?)");
        insertHorse.setInt(1, horse.getHorseId());
        insertHorse.setString(2, horse.getName());
        insertHorse.setString(3, horse.getInfo());
        insertHorse.executeUpdate();
        insertHorse.close();
        connection.close();
    }

    @Override
    public void update(Horse horse) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        PreparedStatement updateHorse = connection.prepareStatement("UPDATE memberships SET name, info WHERE horse = ?");
        updateHorse.setString(1, horse.getName());
        updateHorse.setString(2, horse.getInfo());
        updateHorse.setInt(3, horse.getHorseId());
        updateHorse.executeUpdate();
        updateHorse.close();
        connection.close();
    }

    @Override
    public void remove(Integer integer) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        PreparedStatement deleteHorse = connection.prepareStatement("DELETE FROM horses WHERE id = ?");
        deleteHorse.setInt(1, integer);
        deleteHorse.executeUpdate();
        deleteHorse.close();
        connection.close();
    }

    @Override
    public Horse get(Integer integer) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        PreparedStatement getHorse = connection.prepareStatement("SELECT * FROM horses WHERE id = ?");
        getHorse.setInt(1, integer);
        ResultSet rs = getHorse.executeQuery();
        Horse h = null;
        if (rs.next()) {
            h = new Horse(rs.getInt("id"), rs.getString("name"), rs.getString("info"));
        }
        getHorse.close();
        connection.close();
        return h;
    }


    @Override
    public ArrayList<Horse> getAll() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        PreparedStatement getHorses = connection.prepareStatement("SELECT * FROM horses");
        ResultSet rs = getHorses.executeQuery();
        ArrayList<Horse> horses = new ArrayList<>();
        while(rs.next()){
            Horse h = new Horse(rs.getInt("id"), rs.getString("name"), rs.getString("info"));
            horses.add(h);
        }
        getHorses.executeUpdate();
        getHorses.close();
        connection.close();
        return horses;
    }
}

