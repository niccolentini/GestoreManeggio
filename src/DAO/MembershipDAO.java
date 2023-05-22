package DAO;
import DomainModel.Membership.*;
import java.util.ArrayList;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembershipDAO implements DAO {
    private Connection connection;

    public MembershipDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Object o) {
        if (o instanceof Membership) {
            Membership membership = (Membership) o;
            try {
                createMembership(membership);
            } catch (SQLException e) {
                System.err.println("Errore durante l'aggiunta della membership.");
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("L'oggetto fornito non è di tipo Membership.");
        }
    }

    @Override
    public void update(Object o) {
        if (o instanceof Membership) {
            Membership membership = (Membership) o;
            try {
                updateMembership(membership);
            } catch (SQLException e) {
                System.err.println("Errore durante l'aggiornamento della membership.");
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("L'oggetto fornito non è di tipo Membership.");
        }
    }

    @Override
    public void remove(int id) {
        try {
            deleteMembership(id);
        } catch (SQLException e) {
            System.err.println("Errore durante la rimozione della membership.");
            e.printStackTrace();
        }
    }

    @Override
    public Object get(int id) {
        try {
            return getMembershipById(id);
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero della membership.");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Object> getAll() {
        try {
            return getAllMemberships();
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero delle memberships.");
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private void createMembership(Membership membership) throws SQLException {
        String query = "INSERT INTO membership (id, packType) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, membership.getID());
            statement.setString(2, membership.getType());
            statement.executeUpdate();
        }
    }

    private void updateMembership(Membership membership) throws SQLException {
        String query = "UPDATE membership SET packType = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, membership.getType());
            statement.setInt(2, membership.getID());
            statement.executeUpdate();
        }
    }

    private void deleteMembership(int id) throws SQLException {
        String query = "DELETE FROM membership WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private Membership getMembershipById(int id) throws SQLException {
        String query = "SELECT * FROM membership WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractMembershipFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    private ArrayList<Object> getAllMemberships() throws SQLException {
        String query = "SELECT * FROM membership";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            ArrayList<Object> memberships = new ArrayList<>();
            while (resultSet.next()) {
                memberships.add(extractMembershipFromResultSet(resultSet));
            }
            return memberships;
        }
    }

    public int getMaxMembershipId() throws SQLException {
        String query = "SELECT MAX(id) AS maxId FROM membership";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                return resultSet.getInt("maxId");
            }
        }
        return 0; // Restituisce 0 se non sono presenti membership
    }


    private Membership extractMembershipFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        String phoneNumber = resultSet.getString("phoneNumber");
        String packType = resultSet.getString("packType");

        int newId = getMaxMembershipId();

        // Crea il pacchetto di iscrizione corrispondente in base al tipo
        Membership membership;
        switch (packType) {
            case "BoxPack":
                membership = new BoxPack(newId+1);
                break;
            case "LessonPack":
                BoxPack membershipbase = new BoxPack(newId+1);
                membership = new LessonsPack(membershipbase);
                break;
            case "GroomPack":
                BoxPack membershipbase2 = new BoxPack(newId+1);
                membership = new GroomPack(membershipbase2);
                break;
            default:
                throw new IllegalArgumentException("Tipo di pacchetto di iscrizione non valido: " + packType);
        }

        return membership;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la chiusura della connessione al database.");
            e.printStackTrace();
        }
    }
}
