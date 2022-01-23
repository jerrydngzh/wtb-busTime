package nwHacks2022.bustimeapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Objects;

import nwHacks2022.bustimeapp.R;
import nwHacks2022.bustimeapp.controller.StopManager;
import nwHacks2022.bustimeapp.controller.TempStopManager;
import nwHacks2022.bustimeapp.model.BusStop;
import nwHacks2022.bustimeapp.model.InfoGetter;

public class ListStopsActivity extends AppCompatActivity {
    private final StopManager stopManager = StopManager.getInstance();
    private static final String EXTRA_STOPS_TO_SHOW = "stopsList";
    private static final String EXTRA_TEMP_LIST = "doTempList";

    private TempStopManager tempStopManager = null;

    private boolean doTempList = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_stops);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        getExtras();
        populateBusStopListView();
        createOnClickCallBack();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateBusStopListView();
    }

    private void getExtras() {
        Intent intent = getIntent();
        if (intent.getBooleanExtra(EXTRA_TEMP_LIST, false)) {
            tempStopManager = intent.getParcelableExtra(EXTRA_STOPS_TO_SHOW);
            doTempList = true;
        }
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
            BusStop busStop;
            if (doTempList) {
                busStop = tempStopManager.get(position);
            } else {
                busStop = stopManager.get(position);
            }

            try {
                InfoGetter.sendMessage(busStop.getBusStop() + " " + busStop.getBusNumber());
            } catch (Exception e) {
                new MaterialAlertDialogBuilder(ListStopsActivity.this)
                        .setTitle("Error")
                        .setMessage("Unable to send text message")
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            }

            Intent editStop = AddStopsActivity.makeIntent(ListStopsActivity.this, position);
            editStop.putExtra("busStop", busStop);
            startActivity(editStop);
            //overridePendingTransition(0, 0);
        });
    }

    private void populateBusStopListView() {
        ArrayAdapter<BusStop> stopAdapter;
        if (doTempList) {
            stopAdapter = new StopListAdapter(tempStopManager.getAll());
        } else {
            stopAdapter = new StopListAdapter(stopManager.getAll());
        }
        ListView busList = findViewById(R.id.stops_listview);
        busList.setAdapter(stopAdapter);
    }

    // TODO - unused
    public static Intent makeIntent(Context context) {
        return new Intent(context, ListStopsActivity.class);
    }

    private class StopListAdapter extends ArrayAdapter<BusStop> {

        public StopListAdapter(ArrayList<BusStop> stops) {
            super(ListStopsActivity.this, R.layout.bus_stop_item, stops);
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


    public static Intent makeIntent(Context context, ArrayList<BusStop> stopsToShow) {
        TempStopManager tempStopManager = new TempStopManager(stopsToShow);
        Intent listStopsIntent = new Intent(context, ListStopsActivity.class);
        listStopsIntent.putExtra(EXTRA_STOPS_TO_SHOW, tempStopManager);
        listStopsIntent.putExtra(EXTRA_TEMP_LIST, true);
        return listStopsIntent;
    }
}