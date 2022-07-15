package com.sogeti.consumeapi;

import com.google.gson.*;
import com.sogeti.consumeapi.DTO.PlanetDto;

import java.lang.reflect.Type;

public class PlanetDeserializer  implements JsonDeserializer<PlanetDto> {
    @Override
    public PlanetDto deserialize(JsonElement jElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String nombre = jElement.getAsJsonObject().get("name").getAsString();
        String rotation_period_minutes = jElement.getAsJsonObject().get("rotation_period").getAsString();
        String orbital_period = jElement.getAsJsonObject().get("orbital_period").getAsString();
        String diameter = jElement.getAsJsonObject().get("diameter").getAsString();
        String climate = jElement.getAsJsonObject().get("climate").getAsString();
        String gravity = jElement.getAsJsonObject().get("gravity").getAsString();
        String terrain = jElement.getAsJsonObject().get("terrain").getAsString();
        String surface_water = jElement.getAsJsonObject().get("surface_water").getAsString();
        String population = jElement.getAsJsonObject().get("population").getAsString();
        JsonArray residents = jElement.getAsJsonObject().get("residents").getAsJsonArray();
        JsonArray films = jElement.getAsJsonObject().get("films").getAsJsonArray();
        String created = jElement.getAsJsonObject().get("created").getAsString();
        String edited = jElement.getAsJsonObject().get("edited").getAsString();
        String url = jElement.getAsJsonObject().get("url").getAsString();
        return new PlanetDto(nombre,rotation_period_minutes,orbital_period,diameter,climate,gravity,terrain,surface_water,population,residents,films,created,edited,url);
    }
}
