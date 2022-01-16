package nwHacks2022.bustimeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import nwHacks2022.bustimeapp.controller.StopManager;
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
    }


}