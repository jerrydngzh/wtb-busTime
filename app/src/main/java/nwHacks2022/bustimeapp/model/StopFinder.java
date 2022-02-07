package nwHacks2022.bustimeapp.model;

import android.location.Location;
import android.util.Log;

import java.util.ArrayList;

import nwHacks2022.bustimeapp.controller.StopManager;

public class StopFinder {
    public static int SEARCH_RANGE = 1000;

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
        Log.d("checkLocations", Double.toString(currentLocation.getLatitude()) + Double.toString(currentLocation.getLongitude()));
        ArrayList<BusStop> nearbyStops = new ArrayList<>();
        for (BusStop stop : stops) {
            Location stopLocation = new Location("Bus Stop");
            stopLocation.setLatitude(stop.getLatitude());
            stopLocation.setLongitude(stop.getLongitude());
            Log.d("compareWith", Double.toString(stopLocation.getLatitude()) + Double.toString(stopLocation.getLongitude()));
            Log.d("Distance is", Double.toString(currentLocation.distanceTo(stopLocation)));
            if (currentLocation.distanceTo(stopLocation) < SEARCH_RANGE) {
                nearbyStops.add(stop);
                Log.d("adding stop", "this one");
            } else {
                Log.d("Error", "" + currentLocation.distanceTo(stopLocation) + " less than " + SEARCH_RANGE);
            }
        }
        return nearbyStops;
    }
}
