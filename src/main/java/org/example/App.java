package org.example;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{

    static ArrayList<City> cities = new ArrayList<>();

    public static void main( String[] args )
    {

        buildCitiesList();

        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8000),0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();
    }

    static void buildCitiesList() {
        cities.add(new City(3,"Toronto",15.9));
        cities.add(new City(33,"Milan",25.94));
        cities.add(new City(55,"Rome",35.4));
        System.out.println(cities);
    }
}
