package main.java.DAO;
import main.java.DomainModel.Membership.*;

import java.sql.*;

public class MembershipDAO {
    public Membership get(String fiscalCode) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite: " + "maneggio.db");
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM memberships WHERE rider = ?");
        ps.setString(1, fiscalCode);
        ResultSet rs = ps.executeQuery();
        Membership membership = null;
        if (rs.next()) {
            Membership m = new BoxPack();
            membership = m;
            if (rs.getString("type").equals("LessonsPack")) {
                Membership memb = new LessonsPack(m);
                memb.setNumLessons(rs.getInt("numLessons"));
                membership = memb;
            } else if (rs.getString("type").equals("GroomPack")) {
                Membership memb = new GroomPack(m);
                memb.setNumLessons(rs.getInt("numLessons"));
                membership = memb;
            }
            membership.setNumLessons(rs.getInt("numLessons"));
        }
        rs.close();
        ps.close();
        connection.close();
        return membership;
    }

    public void add(String fiscalCode, Membership membership) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite: " + "maneggio.db");
        PreparedStatement insertMembership = connection.prepareStatement("INSERT INTO memberships (rider, numLessons, type) VALUES (?, ?, ?)");
        insertMembership.setString(1, fiscalCode);
        insertMembership.setInt(2, membership.getNumLessons());
        insertMembership.setString(3, membership.getType().toString());
        insertMembership.executeUpdate();
        insertMembership.close();
        connection.close();
    }

    public void update(String fiscalCode, Membership membership) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite: " + "maneggio.db");
        PreparedStatement updateMembership = connection.prepareStatement("UPDATE memberships SET numLessons WHERE rider = ?");
        updateMembership.setInt(1, membership.getNumLessons());
        updateMembership.setString(2, fiscalCode);
        updateMembership.executeUpdate();
        updateMembership.close();
        connection.close();
    }

    public boolean delete(String fiscalCode) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite: " + "maneggio.db");
        PreparedStatement deleteMembership = connection.prepareStatement("DELETE FROM memberships WHERE rider = ?");
        deleteMembership.setString(1, fiscalCode);
        int rows = deleteMembership.executeUpdate();
        deleteMembership.close();
        connection.close();
        return rows > 0;
    }
}
