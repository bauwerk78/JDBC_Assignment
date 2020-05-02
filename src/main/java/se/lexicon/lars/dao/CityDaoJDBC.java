package se.lexicon.lars.dao;


import se.lexicon.lars.model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDaoJDBC implements CityDao {

    private final String connectionString = "jdbc:mysql://localhost:3306/world?&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Berlin";
    private final String userName = "root";
    private final String password = "root";

    @Override
    public City findById(int id) {
        City city = null;
        try (Connection connection = DriverManager.getConnection(connectionString, userName, password);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM city WHERE id = ?");) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                city = new City(
                        resultSet.getString("Name"),
                        resultSet.getString("CountryCode"),
                        resultSet.getString("District"),
                        resultSet.getInt("Population"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Find by id failed.");
        }
        if (city != null) {
            return city;
        }
        return null;
    }

    @Override
    public List<City> findByCode(String code) {
        List<City> tempList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(connectionString, userName, password);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM city WHERE CountryCode = ?");) {

            preparedStatement.setString(1, code.toUpperCase());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                City city = new City(
                        resultSet.getString("Name"),
                        resultSet.getString("CountryCode"),
                        resultSet.getString("District"),
                        resultSet.getInt("Population"));
                tempList.add(city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Find by code failed.");
        }
        if (tempList.size() > 0) {
            return tempList;
        }
        return null;
    }

    @Override
    public List<City> findByName(String name) {
        List<City> tempList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(connectionString, userName, password);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM city WHERE name = ?");) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                City city = new City(
                        resultSet.getString("Name"),
                        resultSet.getString("CountryCode"),
                        resultSet.getString("District"),
                        resultSet.getInt("Population"));
                tempList.add(city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Find by name failed.");
        }
        if (tempList.size() > 0) {
            return tempList;
        }
        return null;
    }

    @Override
    public List<City> findAll() {
        List<City> tempList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(connectionString, userName, password);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM city");) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                City city = new City(
                        resultSet.getString("Name"),
                        resultSet.getString("CountryCode"),
                        resultSet.getString("District"),
                        resultSet.getInt("Population"));
                tempList.add(city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Find all failed.");
        }
        if (tempList.size() > 0) {
            return tempList;
        }
        return null;
    }

    //TODO why return a city object that is not getting modified? This method could be void.
    @Override
    public City add(City city) {
        try (Connection connection = DriverManager.getConnection(connectionString, userName, password);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO city (Name, CountryCode, District, Population) VALUES (?,?,?,?)")) {

            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getCountryCode());
            preparedStatement.setString(3, city.getDistrict());
            preparedStatement.setInt(4, city.getPopulation());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return city;
    }

    //TODO why return a city object that is not getting modified? This method could be void.
    //This method is pointless as it is since it cannot be updated with a city object
    //while you don't know what in the database to update. I'm just following instructions.
    @Override
    public City update(City city) {
        try (Connection connection = DriverManager.getConnection(connectionString, userName, password);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO city (Name, CountryCode, District, Population) VALUES (?,?,?,?)")) {

            //No execute statement.

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int delete(City city) {
        int postsDeleted = 0;
        try (Connection connection = DriverManager.getConnection(connectionString, userName, password);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM city WHERE Name = ? AND  CountryCode = ? AND  District = ? AND Population = ?")) {

            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getCountryCode());
            preparedStatement.setString(3, city.getDistrict());
            preparedStatement.setInt(4, city.getPopulation());

            postsDeleted = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Number of posts deleted: " + postsDeleted);
        return postsDeleted;
    }
}
