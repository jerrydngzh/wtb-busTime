package nwHacks2022.bustimeapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import nwHacks2022.bustimeapp.view.AddStopsActivity;
import nwHacks2022.bustimeapp.view.ListStopsActivity;
import nwHacks2022.bustimeapp.view.LocationFeaturesActivity;
import nwHacks2022.bustimeapp.view.ReadNfcActivity;

public class MainActivity extends AppCompatActivity {

    // TODO - remove
    private String message = "Hi Kevin - 🅱usTimeApp";
    private String busNum = "6044456342";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpButtons();
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

        // TODO - button as placeholder for sms feature
        Button sendSMSBtn = findViewById(R.id.send_sms);
    }


}