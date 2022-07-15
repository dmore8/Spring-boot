package com.sogeti.consumeapi.DTO;

import com.google.gson.JsonArray;

import java.util.ArrayList;

public class PlanetDto {
    private String name;
    private String rotation_period_minutes;
    private String orbital_period;
    private String diameter;
    private String climate;
    private String gravity;
    private String terrain;
    private String surface_water;
    private String population;
    private JsonArray residents;
    private JsonArray films;
    private String created;
    private String edited;
    private String url;

    public void setName(String name) {
        this.name = name;
    }

    public void setRotation_period_minutes(String rotation_period_minutes) {
        this.rotation_period_minutes = rotation_period_minutes;
    }

    public void setOrbital_period(String orbital_period) {
        this.orbital_period = orbital_period;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public void setGravity(String gravity) {
        this.gravity = gravity;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public void setSurface_water(String surface_water) {
        this.surface_water = surface_water;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public void setResidents(JsonArray residents) {
        this.residents = residents;
    }

    public void setFilms(JsonArray films) {
        this.films = films;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setEdited(String edited) {
        this.edited = edited;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PlanetDto{" +
                "name='" + name + '\'' +
                ", rotation_period_minutes='" + rotation_period_minutes + '\'' +
                ", orbital_period='" + orbital_period + '\'' +
                ", diameter='" + diameter + '\'' +
                ", climate='" + climate + '\'' +
                ", gravity='" + gravity + '\'' +
                ", terrain='" + terrain + '\'' +
                ", surface_water='" + surface_water + '\'' +
                ", population='" + population + '\'' +
                ", residents=" + residents +
                ", films=" + films +
                ", created='" + created + '\'' +
                ", edited='" + edited + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
