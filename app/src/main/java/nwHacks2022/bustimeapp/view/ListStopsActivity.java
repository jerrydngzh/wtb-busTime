package nwHacks2022.bustimeapp.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import nwHacks2022.bustimeapp.R;
import nwHacks2022.bustimeapp.controller.StopManager;
import nwHacks2022.bustimeapp.controller.TempStopManager;
import nwHacks2022.bustimeapp.model.BusStop;

public class ListStopsActivity extends AppCompatActivity {
    private static final String EXTRA_STOPS_TO_SHOW = "stopsList";
    private static final String EXTRA_TEMP_LIST = "doTempList";

    private final StopManager stopManager = StopManager.getInstance();
    private TempStopManager tempStopManager = null;

    private boolean doTempList = false;



    public static final String BUS_NUMBER = "33333";
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_stops);

        getExtras();
        showStops();
        if (!doTempList) {
            setUpListTap();
        }
    }

    private void getExtras() {
        Intent intent = getIntent();
        if (intent.getBooleanExtra(EXTRA_TEMP_LIST, false)) {
            tempStopManager = intent.getParcelableExtra(EXTRA_STOPS_TO_SHOW);
            doTempList = true;
        }
    }

    private void setUpListTap() {
        ListView stopList = findViewById(R.id.stops_listview);
        stopList.setOnItemClickListener((parent, view, position, id) -> {
            Intent editStop = AddStopsActivity.makeIntent(ListStopsActivity.this, position);
            startActivity(editStop);
        });
    }

    private void showStops() {
        ArrayAdapter<BusStop> stopAdapter;
        if (doTempList) {
            stopAdapter = new StopListAdapter(tempStopManager.getAll());
        } else {
            stopAdapter = new StopListAdapter(stopManager.getAll());
        }
        ListView busList = findViewById(R.id.stops_listview);
        busList.setAdapter(stopAdapter);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, ListStopsActivity.class);
    }

    private class StopListAdapter extends ArrayAdapter<BusStop> {

        public StopListAdapter(ArrayList<BusStop> stops) {
            super(ListStopsActivity.this, R.layout.layout_bus_list, stops);
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            BusStop currentStop = stopManager.get(position);
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.layout_bus_list, parent, false);
            }

            Button textBus = itemView.findViewById(R.id.text_bus_btn);
            textBus.setOnClickListener(v -> sendSMSMessage(currentStop.getTextMessage()));

            TextView nameView = itemView.findViewById(R.id.layout_bus_name);
            nameView.setText(currentStop.getName());

            return itemView;
        }

    }


    protected void sendSMSMessage(String busCode) {
        message = busCode;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                Toast.makeText(this, "SMS Permission is need to text the bus", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(BUS_NUMBER, null, message, null, null);
                Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static Intent makeIntent(Context context, ArrayList<BusStop> stopsToShow) {
        TempStopManager tempStopManager = new TempStopManager(stopsToShow);
        Intent listStopsIntent = new Intent(context, ListStopsActivity.class);
        listStopsIntent.putExtra(EXTRA_STOPS_TO_SHOW, tempStopManager);
        listStopsIntent.putExtra(EXTRA_TEMP_LIST, true);
        return listStopsIntent;
    }
}