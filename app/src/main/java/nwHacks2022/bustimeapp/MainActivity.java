package nwHacks2022.bustimeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import nwHacks2022.bustimeapp.view.AddStopsActivity;
import nwHacks2022.bustimeapp.view.ListStopsActivity;
import nwHacks2022.bustimeapp.view.ReadNfcActivity;

public class MainActivity extends AppCompatActivity {

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
    }
}