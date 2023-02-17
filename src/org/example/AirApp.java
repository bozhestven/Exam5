import java.sql.Connection;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class AirApp {
    Scanner scanner;
    final String URL;
    final String USER_NAME;
    final String PASSWORD;
    Connection connection;

    public AirApp() {
        this.scanner = new Scanner(System.in);
        this.URL = "jdbc:postgresql://localhost:5432/air_travel";
        this.USER_NAME = "postgres";
        this.PASSWORD = "postgres";
    }
    public Connection connect() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/air_travel", "postgres", "hr");
        return this.connection;
    }



    public void airInformation() throws SQLException {
        try {
            this.connection = this.connect();
            Statement statement = this.connection.createStatement();
            String SQL = "SELECT Company.name, Trip.plane, Passenger.name, Pass_in_trip.place ,Trip.town_to, Trip.town_from\nfrom Company\nJOIN Trip ON(Company.id = Trip.company_id)\nJOIN Pass_in_trip ON(Trip.id = Pass_in_trip.trip_id)\nJOIN Passenger ON(Pass_in_trip.passenger_id = Passenger.id)";
            ResultSet resultSet = statement.executeQuery(SQL);
            while(resultSet.next()) {
                System.out.print(resultSet.getString(1));
                System.out.print(resultSet.getString(2));
                System.out.print(resultSet.getString(3));
                System.out.print(resultSet.getString(4));
                System.out.print(resultSet.getString(5));
                System.out.print(resultSet.getString(6));
            }
        } finally {
            this.connection.close();
        }
    }



    public void addCompany(int id, String name) throws SQLException {
        this.connection = this.connect();
        PreparedStatement statement = this.connection.prepareStatement("INSERT INTO Company values (?, ?)");
        statement.setInt(1, id);
        statement.setString(2, name);
        statement.executeUpdate();
        System.out.println("Новая компания успешно добавлена!");
    }
}
