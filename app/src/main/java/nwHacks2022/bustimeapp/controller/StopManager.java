package nwHacks2022.bustimeapp.controller;

import java.util.ArrayList;
import java.util.HashMap;

import nwHacks2022.bustimeapp.model.BusStop;

public class StopManager {
    private final HashMap<String, BusStop> busStops = new HashMap<>();
    public static StopManager instance;

    private StopManager() {

    }

    public static StopManager getInstance() {
        if (instance == null) {
            instance = new StopManager();
        }
        return instance;
    }

    public void add(BusStop busStop) {
        busStops.put(busStop.getId(),busStop);
    }

    public ArrayList<BusStop> getAll() {
        return new ArrayList<>(busStops.values());
    }

    public BusStop get(int position) {
        return getAll().get(position);
    }

    public BusStop findById(String id) {
        return busStops.get(id);
    }
}
