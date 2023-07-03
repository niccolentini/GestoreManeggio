package DAO;
import DomainModel.Arena;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ArenaDAO implements DAO <Arena, Integer> {
    @Override
    public void add(Arena arena) throws Exception {
        Connection connection= DriverManager.getConnection("jdbc:sqlite: " + "maneggio.db");
        PreparedStatement ps = connection.prepareStatement("INSERT INTO arenas (name) VALUES (?)");
        //id is auto-incremented, so it's not needed
        ps.setString(1, arena.getName());
        ps.executeUpdate();
        ps.close();
        connection.close();
    }

    @Override
    public void update(Arena arena) throws Exception {
        Connection connection= DriverManager.getConnection("jdbc:sqlite: " + "maneggio.db");
        PreparedStatement ps = connection.prepareStatement("UPDATE arenas SET name = ? WHERE id = ?");
        ps.setString(1, arena.getName());
        ps.setInt(2, arena.getIdArena());
        ps.executeUpdate();
        ps.close();
        connection.close();
    }

    @Override
    public void remove(Integer id) {
    }

    @Override
    public Arena get(Integer id) throws Exception {
        Connection connection= DriverManager.getConnection("jdbc:sqlite: " + "maneggio.db");
        Arena arena = null;
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM arenas WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        {
            arena = new Arena(
                    rs.getString("name"),
                    id);
        }
        rs.close();
        ps.close();

        connection.close();
        return arena;
    }

    @Override
    public ArrayList<Arena> getAll() throws Exception {
        Connection connection= DriverManager.getConnection("jdbc:sqlite: " + "maneggio.db");
        ArrayList<Arena> arenas = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM arenas");
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            arenas.add(new Arena(
                    rs.getString("name"),
                    rs.getInt("id")));
        }
        rs.close();
        ps.close();
        connection.close();
        return arenas;
    }


    public int getNextId() throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:sqlite: " + "maneggio.db");
        String query = "SELECT MAX(id) FROM arenas";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        int id = rs.getInt(1) + 1;
        rs.close();
        statement.close();
        connection.close();
        return id;
    }
}
