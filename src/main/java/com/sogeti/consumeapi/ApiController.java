package com.sogeti.consumeapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sogeti.consumeapi.DTO.PlanetDto;
import com.sogeti.consumeapi.Entity.Planet;
import kong.unirest.Unirest;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    private static String url ="https://swapi.dev/api/people";


    @GetMapping("/people")
    public ResponseEntity<String> getPeople(){
        String body = Unirest.get(url).asString().getBody();
        JsonParser parser = new JsonParser();
        JsonObject gsonO = parser.parse(body).getAsJsonObject();
        JsonArray personas = new JsonArray();
        while (true) {
            personas.addAll(gsonO.get("results").getAsJsonArray());
            if(!gsonO.get("next").isJsonNull()) {
                String url_next = gsonO.get("next").getAsString();
                body = Unirest.get(url_next).asString().getBody();
                gsonO = parser.parse(body).getAsJsonObject();
            }
            else break;
        }
        return new ResponseEntity<>(personas.toString(), HttpStatus.OK);
    }

    @GetMapping("/people/{name}")
    public ResponseEntity<String> getPerson(@PathVariable String name ) {
        String body = Unirest.get(url).asString().getBody();
        JsonParser parser = new JsonParser();
        JsonObject gsonO = parser.parse(body).getAsJsonObject();
        while (true) {
            JsonArray personas = gsonO.get("results").getAsJsonArray();
            for(JsonElement obj : personas) {
                if(obj.getAsJsonObject().get("name").getAsString().equals(name)) {
                    return new ResponseEntity<>(obj.toString(),HttpStatus.OK);
                }
            }
            if(!gsonO.get("next").isJsonNull()) {
                String url_next = gsonO.get("next").getAsString();
                body = Unirest.get(url_next).asString().getBody();
                gsonO = parser.parse(body).getAsJsonObject();
            }
            else break;
        }
        return new ResponseEntity<>("NOT FOUND",HttpStatus.NOT_FOUND);
    }

    private PlanetDto convertEntityToDto(Planet planet) {
        PlanetDto pDTO = new PlanetDto();
        pDTO.setName(planet.getName());
        Integer period = Integer.valueOf(planet.getRotation_period())*60;
        pDTO.setRotation_period_minutes(String.valueOf(period));
        pDTO.setOrbital_period(planet.getOrbital_period());
        pDTO.setDiameter(planet.getDiameter());
        pDTO.setClimate(planet.getClimate());
        pDTO.setGravity(planet.getGravity());
        pDTO.setTerrain(planet.getTerrain());
        pDTO.setSurface_water(planet.getSurface_water());
        pDTO.setPopulation(planet.getPopulation());
        pDTO.setResidents(planet.getResidents());
        pDTO.setFilms(planet.getFilms());
        pDTO.setCreated(planet.getCreated());
        pDTO.setEdited(planet.getEdited());
        pDTO.setUrl(planet.getUrl());
        return pDTO;
    }

    @GetMapping("/planet/{name}")
    public ResponseEntity<String> getPlanet(@PathVariable String name) {
        String body = Unirest.get("https://swapi.dev/api/planets").asString().getBody();
        JsonParser parser = new JsonParser();
        JsonObject gsonO = parser.parse(body).getAsJsonObject();
        while (true) {
            JsonArray planetas = gsonO.get("results").getAsJsonArray();
            for (JsonElement obj : planetas) {
                if (obj.getAsJsonObject().get("name").getAsString().equals(name)) {
                    String nombre = obj.getAsJsonObject().get("name").getAsString();
                    String rotation_period = obj.getAsJsonObject().get("rotation_period").getAsString();
                    String orbital_period = obj.getAsJsonObject().get("orbital_period").getAsString();
                    String diameter = obj.getAsJsonObject().get("diameter").getAsString();
                    String climate = obj.getAsJsonObject().get("climate").getAsString();
                    String gravity = obj.getAsJsonObject().get("gravity").getAsString();
                    String terrain = obj.getAsJsonObject().get("terrain").getAsString();
                    String surface_water = obj.getAsJsonObject().get("surface_water").getAsString();
                    String population = obj.getAsJsonObject().get("population").getAsString();
                    JsonArray residents = obj.getAsJsonObject().get("residents").getAsJsonArray();
                    JsonArray films = obj.getAsJsonObject().get("films").getAsJsonArray();
                    String created = obj.getAsJsonObject().get("created").getAsString();
                    String edited = obj.getAsJsonObject().get("edited").getAsString();
                    String url = obj.getAsJsonObject().get("url").getAsString();
                    Planet planet = new Planet(1L, nombre, rotation_period, orbital_period, diameter, climate, gravity, terrain, surface_water, population, residents, films, created, edited, url);
                    //ModelMapper modelMapper = new ModelMapper();
                    //PlanetDto response = modelMapper.map(planet,PlanetDto.class);
                    PlanetDto response = this.convertEntityToDto(planet);
                    return ResponseEntity.ok().body(response.toString());
                }
            }
            if (!gsonO.get("next").isJsonNull()) {
                String url_next = gsonO.get("next").getAsString();
                body = Unirest.get(url_next).asString().getBody();
                gsonO = parser.parse(body).getAsJsonObject();
            } else break;
        }
        return new ResponseEntity<>(new PlanetDto().toString(),HttpStatus.NOT_FOUND);


    }
}
