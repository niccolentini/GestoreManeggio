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

    }

    @Override
    public void update(Arena arena) throws Exception {

    }

    @Override
    public void remove(Integer integer) throws Exception {

    }

    @Override
    public Arena get(Integer id) throws Exception {
        Connection connection= DriverManager.getConnection("jdbc:sqlite: " + "maneggio.db");
        Arena arena = null;
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM arenas WHERE arenaId = ?");
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
        return null;
    }
}
