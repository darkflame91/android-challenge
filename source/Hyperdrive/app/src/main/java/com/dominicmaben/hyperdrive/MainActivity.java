package com.dominicmaben.hyperdrive;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private int MOVIESNO = 7; //NUMBER OF STAR WARS MOVIES
    private int STARSHIPPAGESNO = 4; //NUMBER OF PAGES; Better implementation would be to check if nextpage in the JSON is null
    private int SHIPSLISTNO = 15; //NUMBER OF SHIPS TO BE DISPLAYED
    private List<Starship> starshipList = new ArrayList<>();
    private List<Starship> starshipShortlist = new ArrayList<>();
    private RecyclerView recyclerView;
    private StarshipAdapter sAdapter;
    private String[] filmList = new String[MOVIESNO];
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        sAdapter = new StarshipAdapter(starshipList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(sAdapter);

        getFilmList();
        braceForHyperspace();
        enterHyperspace();
    }

    // Preload the movie names to avoid repeated API calls to get movie names every time.
    private void getFilmList() {
        for (i = 0; i < MOVIESNO; i++) {
            filmList[i] = "";
        }
        for (i = 0; i < MOVIESNO; i++) {
            JsonObject filmJsonObject = null;
            try {
                filmJsonObject = Ion.with(getApplicationContext()).load("http://swapi.co/api/films/" + (i + 1) + "/").asJsonObject().get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            filmList[i] = filmJsonObject.get("title").getAsString();
        }
    }

    // Get all the starship JSON objects
    private void braceForHyperspace() {
        for (i = 0; i < STARSHIPPAGESNO; i++) {
            JsonObject starshipsJsonObject = null;
            try {
                starshipsJsonObject = Ion.with(getApplicationContext()).load("http://www.swapi.co/api/starships?page=" + (i + 1)).asJsonObject().get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            JsonArray results = starshipsJsonObject.getAsJsonArray("results");
            for (int j = 0; j < results.size(); j++) {
                String cost = results.get(j).getAsJsonObject().get("cost_in_credits").getAsString();
                if (!cost.equals("unknown"))
                    starshipShortlist.add(new Starship(results.get(j).getAsJsonObject(), filmList));
            }
        }
    }
    // Add the top [SHIPSLISTNO] ships to the recycler view
    private void enterHyperspace() {
        Collections.sort(starshipShortlist, new StarshipComparator());
        for (i = 0; i < SHIPSLISTNO; i++) {
            starshipList.add(starshipShortlist.get(i));
            sAdapter.notifyDataSetChanged();
        }
    }
}

