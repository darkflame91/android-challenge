package com.dominicmaben.hyperdrive;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Starship {

    private String name, cost, film;
    private long costLong;

    // Constructor to take the Starship's JSON object and parse it
    public Starship(JsonObject jsonStarship, String[] filmList) {
        this.name = jsonStarship.get("name").getAsString();
        this.cost = "Cost: " + jsonStarship.get("cost_in_credits").getAsString();
        this.costLong = jsonStarship.get("cost_in_credits").getAsLong();
        JsonArray jsonFilm = jsonStarship.getAsJsonArray("films");
        String stringFilm = "";
        for (int i = 0; i < jsonFilm.size(); i++) {
            char filmNo = jsonFilm.get(i).getAsString().charAt(jsonFilm.get(i).getAsString().length() - 2);
            stringFilm += filmList[Character.getNumericValue(filmNo) - 1] + ", ";
        }
        this.film = "Films: " + stringFilm.substring(0, stringFilm.length() - 2);
    }

    // Straightforward constructor. Just in case.
    public Starship(String name, String cost, String film) {
        this.name = name;
        this.cost = cost;
        this.film = film;
    }

    // Getter-Setters for ALL the variables. Because why not.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public long getCostLong() {
        return costLong;
    }

    public void setCostLong(int costLong) {
        this.costLong = costLong;
    }

    public String getFilm() {
        return film;
    }

    public void setFilm(String film) {
        this.film = film;
    }
}