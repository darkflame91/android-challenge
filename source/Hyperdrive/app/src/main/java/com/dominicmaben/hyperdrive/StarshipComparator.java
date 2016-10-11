package com.dominicmaben.hyperdrive;

import java.util.Comparator;

public class StarshipComparator implements Comparator<Starship> {
    @Override
    public int compare(Starship ship1, Starship ship2) {
        return Long.compare(ship2.getCostLong(), ship1.getCostLong());
    }
}