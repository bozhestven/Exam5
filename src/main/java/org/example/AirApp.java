package org.example;

import java.sql.*;

public class AirApp {
    final String URL = "jdbc:postgresql://localhost:5432/air_travel";
    final String USER_NAME = "postgres";
    final String PASSWORD = "postgres";
    Connection connection;
    public Connection connect() throws SQLException {
        connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        return connection;
    }



    public void airInformation() throws SQLException {
        try {
            connection = connect();
            Statement statement = connection.createStatement();
            String SQL = "SELECT Company.name, Trip.plane, Passenger.name, Pass_in_trip.place ,Trip.town_to, Trip.town_from\n" +
                            "from Company\n" +
                            "JOIN Trip ON(Company.id = Trip.company_id)\n" +
                            "JOIN Pass_in_trip ON(Trip.id = Pass_in_trip.trip_id)\n" +
                            "JOIN Passenger ON(Pass_in_trip.passenger_id = Passenger.id)";
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                System.out.print(resultSet.getString(1));
                System.out.print(resultSet.getString(2));
                System.out.print(resultSet.getString(3));
                System.out.print(resultSet.getString(4));
                System.out.print(resultSet.getString(5));
                System.out.print(resultSet.getString(6));
            }
        } finally {
            connection.close();
        }
    }

    public void addCompany(int id, String name) throws SQLException {
        try {
            connection = connect();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Company values (?, ?)");
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.executeUpdate();
            System.out.println("Новая компания успешно добавлена!");
        } finally {
            connection.close();
        }
    }
}