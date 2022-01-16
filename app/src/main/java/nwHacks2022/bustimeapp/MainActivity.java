package nwHacks2022.bustimeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
    public static final String STOP_SAVE_NAME = "JsonStops";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO -- deserializing/serializing failing on empty string
//        setUpSaving();
        setUpButtons();
//        loadStops();
    }

    private void setUpSaving() {
        StopManager stopManager = StopManager.getInstance();
        stopManager.setSaveOption(new StopManager.SaveManager() {
            @Override
            public String load() {
                sp = getApplicationContext().getSharedPreferences(STOPS_SAVE_SP, MODE_PRIVATE);
                return sp.getString(STOP_SAVE_NAME, "");
            }

            @Override
            public void save(String saveJson) {
                sp = getApplicationContext().getSharedPreferences(STOPS_SAVE_SP, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(STOP_SAVE_NAME, saveJson);
                editor.apply();
            }
        });
        stopManager.load();
    }

    private void loadStops() {
        StopManager stopManager = StopManager.getInstance();
        sp = this.getSharedPreferences(STOPS_SAVE_SP, Context.MODE_PRIVATE);
        stopManager.fromJson(sp.getString(STOP_SAVE_NAME, ""));
    }

    private void setUpButtons() {
        Button addStopButton = findViewById(R.id.add_stop_btn);
        addStopButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddStopsActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        Button listStopButton = findViewById(R.id.list_stops_btn);
        listStopButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ListStopsActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        Button readNfcButton = findViewById(R.id.read_nfc_btn);
        readNfcButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ReadNfcActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        Button locationFeaturesButton = findViewById(R.id.find_location_btn);
        locationFeaturesButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LocationFeaturesActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        Button nearbyButton = findViewById(R.id.nearby_stops_btn);
        nearbyButton.setOnClickListener(v -> getNearbyStops());
    }

    private void getNearbyStops() {
        StopFinder stopFinder = new StopFinder(getCurrentLocation());
        ArrayList<BusStop> nearby = stopFinder.findSavedStops();
        if (nearby.isEmpty()) {
            Toast.makeText(this, "No nearby stops found!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (nearby.size() == 1) {
            //TODO: don't open the activity just send the text of the only nearby bus

        } else {
            startActivity(ListStopsActivity.makeIntent(this, nearby));
            overridePendingTransition(0,0);
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