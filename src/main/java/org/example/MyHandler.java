package org.example;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MyHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();

        URI uri = exchange.getRequestURI();
        String query = uri.getQuery();

        //legge POST
        String s = read(is);

        if (s != null) {
            query = s;
        }

        String risultato = processa(query);

        String response = new String();

        if (s == null) {
            response = "<!doctype html>\n" +
                    "<html lang=en>\n" +
                    "<head>\n" +
                    "<meta charset=utf-8>\n" +
                    "<title>MyJava Sample</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    risultato + "\n" +
                    "</body>\n" +
                    "</html>\n";
        } else {
            response = risultato + "\n";
        }

        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    private String processa(String query) {

        if (query.contains("=")){
            query = query.split("=")[1];
        }
        switch (query) {
            case "hottest": {
                return hottest(App.cities);
            }
            case "all": {
                return every(App.cities);
            }
            case "sorted_by_name": {
                return sortByName(App.cities);
            }
            case "sorted_by_temp": {
                return sortByTemp(App.cities);
            }
            default: {
                return "not a command";
            }
        }
    }

    private String sortByName(ArrayList<City> cities) {
        ArrayList<City> sortedCities = cities;

        sortedCities.sort((o1, o2) -> {
            return o1.name.compareTo(o2.name);
        });

//        String [] nameArray = new String[cities.size()];
//        int i = 0;
//
//        for (City c : cities) {
//            nameArray[i] = c.name;
//            i++;
//        }
//        Arrays.sort(nameArray);
//
//        for (i = 0; i < nameArray.length; i++) {
//            for (City c : cities) {
//                if (c.name == nameArray[i]){
//                    sortedCities.add(c);
//                    break;
//                }
//            }
////            cities.remove(sortedCities.get(i));
//        }

        Gson gson = new Gson();
        String jsonString = new String();

        jsonString = gson.toJson(sortedCities);

        return jsonString;
    }

    private String sortByTemp(ArrayList<City> cities) {

        ArrayList<City> sortedCities = cities;

        sortedCities.sort((o1, o2) -> {
            return o1.temp.compareTo(o2.temp);
        });

//        ArrayList<City> sortedCities = new ArrayList<>();
//        double [] tempArray = new double[cities.size()];
//        int i = 0;
//
//        for (City c : cities) {
//            tempArray[i] = c.temp;
//            i++;
//        }
//        Arrays.sort(tempArray);
//
//        for (i = 0; i < tempArray.length; i++) {
//            for (City c : cities) {
//                if (c.temp == tempArray[i]){
//                    sortedCities.add(c);
//                    break;
//                }
//            }
////            cities.remove(cities.get(i));
//        }
//
        Gson gson = new Gson();
        String jsonString = new String();

        jsonString = gson.toJson(sortedCities);

        return jsonString;
    }

    private String every(ArrayList<City> cities) {
        Gson gson = new Gson();
        String jsonString = new String();

        jsonString = gson.toJson(cities);

        return jsonString;

    }

    private String hottest(ArrayList<City> cities) {
        double maxtemp = -100;
        for (City c : cities) {
            if (c.temp > maxtemp) {
                maxtemp = c.temp;
            }
        }

        Gson gson = new Gson();
        String jsonString = new String();

        for (City c : cities){
            if (c.temp == maxtemp){
                jsonString = gson.toJson(c);
                break;
            }
        }

        return jsonString;
    }

    private String read(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        System.out.println("\n");
        String recieved = null;

        while (true) {
            String s = null;
            try {
                if ((s = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(s);
            recieved += s;
        }
        return recieved;
    }
}
