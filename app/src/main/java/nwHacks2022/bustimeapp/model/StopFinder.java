package nwHacks2022.bustimeapp.model;

import android.location.Location;

import java.util.ArrayList;

import nwHacks2022.bustimeapp.controller.StopManager;

public class StopFinder {
    public static int SEARCH_RANGE = 10;

    private final StopManager stopManager = StopManager.getInstance();

    private final Location currentLocation;

    public StopFinder(Location location) {
        currentLocation = location;
    }

    public ArrayList<BusStop> findSavedStops() {
        return checkLocations(stopManager.getAll());
    }

    public ArrayList<BusStop> findAllStops() {
        return checkLocations(getApiStops());
    }

    private ArrayList<BusStop> getApiStops() {
        //waiting for completion.
        return new ArrayList<BusStop>();
    }

    private ArrayList<BusStop> checkLocations(ArrayList<BusStop> stops) {
        ArrayList<BusStop> nearbyStops = new ArrayList<>();
        for (BusStop stop : stops) {
            Location stopLocation = new Location("Bus Stop");
            stopLocation.setLatitude(stop.getLatitude());
            stopLocation.setLongitude(stop.getLongitude());
            if (currentLocation.distanceTo(stopLocation) < SEARCH_RANGE) {
                nearbyStops.add(stop);
            }
        }
        return nearbyStops;
    }
}
