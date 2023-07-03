package DAO;

import DomainModel.Arena;
import DomainModel.Trainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TrainerDAO implements DAO <Trainer, String> {

    @Override
    public void add(Trainer trainer) throws Exception {

    }

    @Override
    public void update(Trainer trainer) throws Exception {

    }

    @Override
    public void remove(String fiscalCode) throws Exception {

    }
    @Override
    public Trainer get(String fiscalCode) throws Exception {
        Connection connection= DriverManager.getConnection("jdbc:sqlite: " + "maneggio.db");
        Trainer trainer = null;
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM trainers WHERE fiscalCode = ?");
        ps.setString(1, fiscalCode);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        {
            trainer = new Trainer(
                    rs.getString("fiscalCode"),
                    rs.getString("firstName"),
                    rs.getString("lastName")
                    );
        }
        rs.close();
        ps.close();

        connection.close();
        return trainer;
    }

    @Override
    public ArrayList<Trainer> getAll() throws Exception {
        return null;
    }
}
