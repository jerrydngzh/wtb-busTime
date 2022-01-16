package nwHacks2022.bustimeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import nwHacks2022.bustimeapp.controller.StopManager;
import nwHacks2022.bustimeapp.model.BusStop;
import nwHacks2022.bustimeapp.model.StopFinder;
import nwHacks2022.bustimeapp.view.AddStopsActivity;
import nwHacks2022.bustimeapp.view.ListStopsActivity;
import nwHacks2022.bustimeapp.view.LocationFeaturesActivity;
import nwHacks2022.bustimeapp.view.ReadNfcActivity;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sp;

    public static final String STOPS_SAVE_SP = "stopsSave";
    public static final String STOP_SAVENAME = "JsonStops";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpSaving();
        setUpButtons();
        loadStops();
    }

    private void setUpSaving() {
        StopManager stopManager = StopManager.getInstance();
        stopManager.setSaveOption(new StopManager.SaveManager() {
            @Override
            public String load() {
                sp = getApplicationContext().getSharedPreferences(STOPS_SAVE_SP, MODE_PRIVATE);
                return sp.getString(STOP_SAVENAME, "");
            }

            @Override
            public void save(String saveJson) {
                sp = getApplicationContext().getSharedPreferences(STOPS_SAVE_SP, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(STOP_SAVENAME, saveJson);
                editor.apply();
            }
        });
        stopManager.load();
    }


    private void loadStops() {
        StopManager stopManager = StopManager.getInstance();
        sp = this.getSharedPreferences(STOPS_SAVE_SP, Context.MODE_PRIVATE);
        stopManager.fromJson(sp.getString(STOP_SAVENAME, ""));
    }

    private void setUpButtons() {
        Button addStopButton = findViewById(R.id.add_stop_btn);
        addStopButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddStopsActivity.class);
            startActivity(intent);
        });

        Button listStopButton = findViewById(R.id.list_stops_btn);
        listStopButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ListStopsActivity.class);
            startActivity(intent);
        });

        Button readNfcButton = findViewById(R.id.read_nfc_btn);
        readNfcButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ReadNfcActivity.class);
            startActivity(intent);
        });

        Button locationFeaturesButton = findViewById(R.id.find_location_btn);
        locationFeaturesButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LocationFeaturesActivity.class);
            startActivity(intent);
        });

        Button nearbyButton = findViewById(R.id.nearby_stops_button);
        nearbyButton.setOnClickListener(v -> {
            getNearbyStops();

        });
    }

    private void getNearbyStops() {
        StopFinder stopFinder = new StopFinder(getCurrentLocation());
        ArrayList<BusStop> nearby = stopFinder.findSavedStops();
        if (nearby.isEmpty()) {
            Toast.makeText(this, "No nearby stops found!", Toast.LENGTH_LONG).show();
            return;
        }
        if (nearby.size() == 1) {
            //TODO: don't open the activity just send the text of the only nearby bus
        } else {
            startActivity(ListStopsActivity.makeIntent(this, nearby));
        }
    }

    private Location getCurrentLocation() {
        //TODO: hook this up so it actually works
        Location location = new Location("test");
        location.setLongitude(0);
        location.setLatitude(0);
        return location;
    }


}