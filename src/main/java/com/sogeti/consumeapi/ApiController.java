package com.sogeti.consumeapi;

import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kong.unirest.Unirest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
