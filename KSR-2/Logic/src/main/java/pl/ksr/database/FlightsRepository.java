package pl.ksr.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FlightsRepository {

    public static List<Flight> findAll() {
        List<Flight> allFlights = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String ALL_RECORDS_QUERY = "SELECT * FROM public.flights";
            PreparedStatement preparedStatement = connection.prepareStatement(ALL_RECORDS_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String departureTime = resultSet.getString("departure_daytime");

                Flight flight = new Flight.FlightBuilder()
                        .departureDay(resultSet.getInt("departure_day_of_year"))
                        .departureTime(parseTimeToMinutes(departureTime))
                        .departureDelay(resultSet.getFloat("dep_delay"))
                        .airTime(resultSet.getInt("air_time"))
                        .distance(resultSet.getFloat("distance"))
                        .yearOfManufacture(resultSet.getInt("year_of_manufacture"))
                        .windDir(resultSet.getFloat("wind_dir"))
                        .windSpeed(resultSet.getFloat("wind_spd"))
                        .windGust(resultSet.getFloat("wind_gust"))
                        .temperature(resultSet.getFloat("temperature"))
                        .relativeHumidity(resultSet.getFloat("rel_humidity"))
                        .lowestCloudLayer(resultSet.getFloat("lowest_cloud_layer"))
                        .build();
                allFlights.add(flight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allFlights;
    }

    public static List<Double> findAllByName(String columnName) {
        List<Double> all = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String ALL_RECORDS_QUERY = "SELECT " + columnName + " FROM public.flights";
            PreparedStatement preparedStatement = connection.prepareStatement(ALL_RECORDS_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (Objects.equals(columnName, "departure_daytime")) {
                    String departureTime = resultSet.getString("departure_daytime");
                    all.add((double) parseTimeToMinutes(departureTime));
                } else {
                    double row = resultSet.getDouble(columnName);
                    all.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return all;
    }

    public static List<String> getAttributesNames() {
        List<String> attributes = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, "public", "flights", null);
            while (resultSet.next()) {
                attributes.add(resultSet.getString("COLUMN_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attributes;
    }

    public static List<String> getAllSubjects() {
        List<String> subjects = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String SUBJECT_QUERY = "SELECT distinct manufacturer FROM public.flights";
            PreparedStatement preparedStatement = connection.prepareStatement(SUBJECT_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                subjects.add(resultSet.getString("manufacturer"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }
    public static List<Flight> findAllBySubject(String subject) {
        List<Flight> allFlights = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String SUBJECT_QUERY = "SELECT * FROM public.flights where manufacturer = " + "'" + subject +  "'";
            PreparedStatement preparedStatement = connection.prepareStatement(SUBJECT_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String departureTime = resultSet.getString("departure_daytime");

                Flight flight = new Flight.FlightBuilder()
                        .departureDay(resultSet.getInt("departure_day_of_year"))
                        .departureTime(parseTimeToMinutes(departureTime))
                        .departureDelay(resultSet.getFloat("dep_delay"))
                        .airTime(resultSet.getInt("air_time"))
                        .distance(resultSet.getFloat("distance"))
                        .yearOfManufacture(resultSet.getInt("year_of_manufacture"))
                        .windDir(resultSet.getFloat("wind_dir"))
                        .windSpeed(resultSet.getFloat("wind_spd"))
                        .windGust(resultSet.getFloat("wind_gust"))
                        .temperature(resultSet.getFloat("temperature"))
                        .relativeHumidity(resultSet.getFloat("rel_humidity"))
                        .lowestCloudLayer(resultSet.getFloat("lowest_cloud_layer"))
                        .build();
                allFlights.add(flight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allFlights;
    }



    private static int parseTimeToMinutes(String time) {
        int hours = Integer.parseInt(time.substring(0, 2));
        int minutes = Integer.parseInt(time.substring(3, 5));
        return hours * 60 + minutes;
    }
}
