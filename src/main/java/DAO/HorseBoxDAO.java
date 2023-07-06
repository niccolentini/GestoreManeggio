package main.java.DAO;

import main.java.DomainModel.HorseBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class HorseBoxDAO implements DAO <HorseBox, Integer>{
    @Override
    public void add(HorseBox box) throws Exception{
        Connection connection= DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        PreparedStatement ps = connection.prepareStatement("INSERT INTO horseBoxes (box, horse) VALUES (?,?)");
        ps.setInt(1, box.getBoxID());
        ps.setInt(2, box.getHorseId(box.getHorse()));
        ps.executeUpdate();
        ps.close();
        connection.close();
    }

    @Override
    public void update(HorseBox box) throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        PreparedStatement ps = connection.prepareStatement("UPDATE horseBoxes SET horse = ? WHERE box = ?");
        ps.setInt(1, box.getHorseId(box.getHorse()));
        ps.setInt(2, box.getBoxID());
        ps.executeUpdate();
        ps.close();
        connection.close();
    }

    @Override
    public void remove(Integer boxId) throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        PreparedStatement ps = connection.prepareStatement("DELETE FROM horseBoxes WHERE box = ?");
        ps.setInt(1, boxId);
        ps.executeUpdate();
        ps.close();
        connection.close();
    }

    @Override
    public HorseBox get(Integer boxId) throws Exception {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        HorseBox box = null;
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM horseBoxes WHERE box = ?");
        ps.setInt(1, boxId);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        {
            box = new HorseBox(boxId);
        }
        rs.close();
        ps.close();
        connection.close();
        return box;
    }

    @Override
    public ArrayList<HorseBox> getAll() throws Exception {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        ArrayList<HorseBox> boxes = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM horseBoxes");
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            boxes.add(new HorseBox(
                    rs.getInt("box")));
        }
        rs.close();
        ps.close();
        connection.close();
        return boxes;
    }

    public HorseBox searchByHorse(int horseId) throws Exception{
        Connection connection= DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        HorseBox box = null;
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM horseBoxes WHERE horse = ?");
        ps.setInt(1, horseId);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        {
            box = new HorseBox(rs.getInt("box"));
        }
        rs.close();
        ps.close();
        connection.close();
        return box;
    }

    public int getNextId() throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        String query = "SELECT MAX(box) FROM horseBoxes";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        int id = rs.getInt(1) + 1;
        rs.close();
        statement.close();
        connection.close();
        return id;
    }

}
