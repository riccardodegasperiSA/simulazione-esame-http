package org.example;

public class City {
    int id;
    String name;
    Double temp;
    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", temp=" + temp +
                '}';
    }
    public City(int id, String name, Double
            temp) {
        this.id = id;
        this.name = name;
        this.temp = temp;
    }
}
