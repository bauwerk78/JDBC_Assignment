package se.lexicon.lars;

import se.lexicon.lars.dao.CityDaoJDBC;
import se.lexicon.lars.model.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        CityDaoJDBC cityDaoJDBC = new CityDaoJDBC();
        List<City> listAll = cityDaoJDBC.findAll();
        System.out.println("Printing findall.");
        listAll.forEach(System.out::println);

        listAll = cityDaoJDBC.findByCode("SWE");
        System.out.println("Printing find by country code.");
        listAll.forEach(System.out::println);

        listAll = cityDaoJDBC.findByName("Stockholm");
        System.out.println("Printing by name of city.");
        listAll.forEach(System.out::println);

        City nisseCity = new City("Nissestad" , "SWE", "Nisseboa", 50);

        cityDaoJDBC.add(nisseCity);
        listAll = cityDaoJDBC.findByName("Nissestad");
        System.out.println("Added new city.");
        listAll.forEach(System.out::println);

        cityDaoJDBC.delete(nisseCity);
        System.out.println("Deleted the new city.");
    }
}