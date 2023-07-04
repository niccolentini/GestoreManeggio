package test.java.businessLogic;

import main.java.BusinessLogic.BookingsController;
import main.java.BusinessLogic.LessonsController;
import main.java.BusinessLogic.RidersController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

class BookingsControllerTest {
    private BookingsController bookingsController;
    private LessonsController lessonsController;
    private RidersController ridersController;
    private int testLesson1Id;
    private int testLesson2Id;
    private String testRiderFiscalCode;

    @BeforeAll
    public int initDb() throws SQLException, IOException {
        // Set up database
        StringBuilder resultStringBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/schema.sql"));
        String line;
        while ((line = br.readLine()) != null) {
            resultStringBuilder.append(line).append("\n");
        }

        Connection connection = DriverManager.getConnection("jdbc:sqlite: " + "maneggio.db");
        Statement stmt = DriverManager.getConnection("jdbc:sqlite: " + "maneggio.db").createStatement();
        int row = stmt.executeUpdate(resultStringBuilder.toString());

        stmt.close();
        connection.close();
        return row;
    }

    @BeforeEach
    public void resetDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite: " + "maneggio.db");
        // Delete data from all tables
        List<String> tables = Arrays.asList("trainers", "courses", "customers", "memberships", "bookings", "membership_extensions");
        for (String table : tables) connection.prepareStatement("DELETE FROM " + table).executeUpdate();

        // Reset autoincrement counters
        connection.prepareStatement("DELETE FROM sqlite_sequence").executeUpdate();
        connection.close();
    }



}
