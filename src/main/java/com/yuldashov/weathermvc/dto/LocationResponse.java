package com.yuldashov.weathermvc.dto;

public class LocationResponse {
    private String name;
    private double temp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}
