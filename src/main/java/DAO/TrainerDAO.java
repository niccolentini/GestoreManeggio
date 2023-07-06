package main.java.DAO;

import main.java.DomainModel.Trainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TrainerDAO implements DAO <Trainer, String> {

    @Override
    public void add(Trainer trainer) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        PreparedStatement statement = connection.prepareStatement("INSERT INTO trainers ( fiscalCode, firstName, lastName) VALUES (?,?,?)");
        statement.setString(1, trainer.getFiscalCod());
        statement.setString(2, trainer.getFirstName());
        statement.setString(3, trainer.getLastName());
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    @Override
    public void update(Trainer trainer) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        PreparedStatement statement = connection.prepareStatement("UPDATE trainers SET firstName = ?, lastName = ? WHERE fiscalCode = ?");
        statement.setString(1, trainer.getFirstName());
        statement.setString(2, trainer.getLastName());
        statement.setString(3, trainer.getFiscalCod());
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    @Override
    public void remove(String fiscalCode) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        PreparedStatement statement = connection.prepareStatement("DELETE FROM trainers WHERE fiscalCode = ?");
        statement.setString(1, fiscalCode);
        statement.executeUpdate();
        statement.close();
        connection.close();

    }

    @Override
    public Trainer get(String fiscalCode) throws Exception {
        Connection connection= DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
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
        Connection connection= DriverManager.getConnection("jdbc:sqlite:" + "maneggio.db");
        ArrayList<Trainer> trainers = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM trainers");
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            trainers.add(new Trainer(
                    rs.getString("fiscalCode"),
                    rs.getString("firstName"),
                    rs.getString("lastName")
                    ));
        }
        rs.close();
        ps.close();
        connection.close();
        return trainers;
    }
}
