package com.sogeti.consumeapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.*;
import com.sogeti.consumeapi.DTO.PlanetDto;
import kong.unirest.Unirest;

import org.mapstruct.factory.Mappers;
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


    @GetMapping("/planet/{name}")
    public ResponseEntity<String> getPlanet(@PathVariable String name) {
        String body = Unirest.get("https://swapi.dev/api/planets").asString().getBody();
        JsonParser parser = new JsonParser();
        JsonObject gsonO = parser.parse(body).getAsJsonObject();
        //PlanetConverter converter = Mappers.getMapper(PlanetConverter.class);
        while (true) {
            JsonArray planetas = gsonO.get("results").getAsJsonArray();
            for (JsonElement obj : planetas) {
                if (obj.getAsJsonObject().get("name").getAsString().equals(name)) {
                    GsonBuilder gsonB = new GsonBuilder();
                    gsonB.registerTypeAdapter(PlanetDto.class,new PlanetDeserializer());
                    PlanetDto response = gsonB.create().fromJson(obj.toString(),PlanetDto.class);
                    Integer rot = Integer.valueOf(response.getRotation_period_minutes());
                    response.setRotation_period_minutes(String.valueOf(rot*60));
                    return ResponseEntity.ok().body(response.toString());
                }
            }
            if (!gsonO.get("next").isJsonNull()) {
                String url_next = gsonO.get("next").getAsString();
                body = Unirest.get(url_next).asString().getBody();
                gsonO = parser.parse(body).getAsJsonObject();
            } else break;
        }
        return new ResponseEntity<>("NOT FOUND",HttpStatus.NOT_FOUND);


    }
}
