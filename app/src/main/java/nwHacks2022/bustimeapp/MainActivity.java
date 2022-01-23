package nwHacks2022.bustimeapp;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import nwHacks2022.bustimeapp.controller.StopManager;
import nwHacks2022.bustimeapp.model.BusStop;
import nwHacks2022.bustimeapp.model.InfoGetter;
import nwHacks2022.bustimeapp.model.StopFinder;
import nwHacks2022.bustimeapp.view.AddStopsActivity;
import nwHacks2022.bustimeapp.view.ListStopsActivity;
import nwHacks2022.bustimeapp.view.LocationFeaturesActivity;
import nwHacks2022.bustimeapp.view.ReadNfcActivity;

public class MainActivity extends AppCompatActivity {
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    SharedPreferences sp;


    private String message;

    public static final String STOPS_SAVE_SP = "stopsSave";
    public static final String STOP_SAVE_NAME = "JsonStops";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpSaving();
        setUpButtons();
        loadStops();

        pendingIntent = makePendingIntent(MainActivity.this);

        initializeNFC();
    }

    @Override
    protected void onResume() {
        super.onResume();
        assert nfcAdapter != null;
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        resolveIntent(intent);
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
            InfoGetter.getCoodrinates(MainActivity.this, "55612");
            Intent intent = new Intent(getApplicationContext(), LocationFeaturesActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        Button nearbyButton = findViewById(R.id.nearby_stops_btn);
        nearbyButton.setOnClickListener(v -> getNearbyStops());
    }

    private void getNearbyStops() {
        StopFinder stopFinder = new StopFinder(getCurrentLocation());
        ArrayList<BusStop> nearbyStops = stopFinder.findSavedStops();
        if (nearbyStops.isEmpty()) {
            Toast.makeText(this, "No nearby stops found!", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d("getNearbyStops", "TEST");

        if (nearbyStops.size() == 1) {
            //TODO: don't open the activity just send the text of the only nearby bus
            BusStop busStop = nearbyStops.get(0);
            try {
                InfoGetter.sendMessage(busStop.getBusStop() + " " + busStop.getBusNumber());
            } catch (Exception e) {
                new MaterialAlertDialogBuilder(MainActivity.this)
                        .setTitle("Error")
                        .setMessage("Unable to send text message")
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            }

        } else {
            startActivity(ListStopsActivity.makeIntent(this, nearbyStops));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        overridePendingTransition(0, 0);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    private void resolveIntent(Intent intent) {
        String action = intent.getAction();
        Tag tag;
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            assert tag != null;


            Log.d("resolveIntent", "TEST");
            getNearbyStops();
        }
    }



    private void initializeNFC() {
        //initialize adapter
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (nfcAdapter == null) {
            Toast.makeText(this, "NO NFC FUNCTIONALITY", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public static PendingIntent makePendingIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE);
    }

}