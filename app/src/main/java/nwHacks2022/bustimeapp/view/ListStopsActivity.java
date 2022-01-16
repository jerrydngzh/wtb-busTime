package nwHacks2022.bustimeapp.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Objects;

import nwHacks2022.bustimeapp.R;
import nwHacks2022.bustimeapp.controller.StopManager;
import nwHacks2022.bustimeapp.model.BusStop;

public class ListStopsActivity extends AppCompatActivity {
    private final StopManager stopManager = StopManager.getInstance();

    public static final String BUS_NUMBER = "33333";
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_stops);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        populateBusStopListView();
        createOnClickCallBack();
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

    private void createOnClickCallBack() {
        ListView stopList = findViewById(R.id.stops_listview);

        // TODO -- do we want this to be click the listview item and send the SMS and have an edit mode to trigger the Intent for EditActivity (could use the menu nav bar to configure)
        stopList.setOnItemClickListener((parent, view, position, id) -> {

            // TODO ?
//            BusStop busStop = stopManager.get(position);
//            sendSMSMessage(busStop.getBusStop() + " " + busStop.getBusNumber());

            Intent editStop = AddStopsActivity.makeIntent(ListStopsActivity.this, position);
            startActivity(editStop);
            overridePendingTransition(0, 0);
        });
    }

    private void populateBusStopListView() {
        ArrayAdapter<BusStop> stopAdapter = new StopListAdapter();

        ListView busList = findViewById(R.id.stops_listview);
        busList.setAdapter(stopAdapter);
    }

    // TODO - not used
    public static Intent makeIntent(Context context) {
        return new Intent(context, ListStopsActivity.class);
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

    private class StopListAdapter extends ArrayAdapter<BusStop> {

        public StopListAdapter() {
            super(ListStopsActivity.this, R.layout.bus_stop_item, stopManager.getAll());
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            BusStop currentStop = stopManager.get(position);
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.bus_stop_item, parent, false);
            }

            // Set Bus Stop Address Name
            TextView busStopAddress = itemView.findViewById(R.id.busStopAddress);
            busStopAddress.setText(currentStop.getName());

            // Set Bus Stop Number
            TextView busStopNum = itemView.findViewById(R.id.busStopNumber);
            busStopNum.setText(currentStop.getBusStop());

            // Setup Bus Stop Bus Number
            TextView busNum = itemView.findViewById(R.id.busNumber);
            busNum.setText(currentStop.getBusNumber());

            return itemView;
        }

    }


}