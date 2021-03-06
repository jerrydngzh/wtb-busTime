package nwHacks2022.bustimeapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

import nwHacks2022.bustimeapp.R;
import nwHacks2022.bustimeapp.controller.StopManager;
import nwHacks2022.bustimeapp.model.BusStop;

public class AddStopsActivity extends AppCompatActivity {

    private static final String EXTRA_DO_EDIT = "doEdit";
    private static final String EXTRA_STOP_POSITION = "position";

    StopManager stopManager = StopManager.getInstance();
    boolean doEdit = false;

    BusStop busStop = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stops);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getExtras();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_save) {
            saveStop();
        } else if (item.getItemId() == R.id.item_delete) {
            deleteStop();
        }else{
            finish();
            overridePendingTransition(0, 0);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_delete_appbar, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    private void getExtras() {
        //TODO: get editing bus stops working.
        Intent intent = getIntent();
        doEdit = intent.getBooleanExtra(EXTRA_DO_EDIT, false);

        try {
            busStop = intent.getParcelableExtra("busStop");
            setTextFields(busStop.getName(), R.id.stop_name_field);
            setTextFields(busStop.getBusNumber(), R.id.stop_number_field);
            setTextFields(busStop.getBusStop(), R.id.bus_number_field);
            setIntFields(busStop.getLatitude(), R.id.lat_field);
            setIntFields(busStop.getLongitude(), R.id.long_field);
        } catch (Exception e) {
            //nothing
        }
    }

    private void setTextFields(String toSet, int id) {
        if (doEdit) {
            EditText editText = this.findViewById(id);
            editText.setText(toSet);
        }
    }

    private void setIntFields(double toSet, int id) {
        if (doEdit) {
            EditText editText = this.findViewById(id);
            editText.setText(Double.toString(toSet));
        }
    }

    private void saveStop() {
        EditText stopName = findViewById(R.id.stop_name_field);
        EditText lat = findViewById(R.id.lat_field);
        EditText lon = findViewById(R.id.long_field);
        EditText busNo = findViewById(R.id.bus_number_field);
        EditText busStopNo = findViewById(R.id.stop_number_field);

        try {
            String name = stopName.getText().toString();
            String latitude = lat.getText().toString();
            String longitude = lon.getText().toString();
            String busNum = busNo.getText().toString();
            String busStop = busStopNo.getText().toString();

            stopManager.add(new BusStop(name, busNum, busStop, Double.parseDouble(latitude), Double.parseDouble(longitude)));
            finish();
            overridePendingTransition(0, 0);
        } catch (Exception e) {
            new MaterialAlertDialogBuilder(AddStopsActivity.this)
                    .setTitle("Warning")
                    .setMessage("You have unfilled fields!")
                    .show();
        }
    }

    private void deleteStop() {
        try {
            stopManager.deleteStop(busStop);
            finish();
        } catch (Exception e) {
            finish();
        }
    }

    // TODO - unused!
    public static Intent makeIntent(Context context) {
        return new Intent(context, AddStopsActivity.class);
    }

    public static Intent makeIntent(Context context, int index) {
        Intent editStopIntent = new Intent(context, AddStopsActivity.class);
        editStopIntent.putExtra(EXTRA_DO_EDIT, true);
        editStopIntent.putExtra(EXTRA_STOP_POSITION, index);

        return editStopIntent;
    }
}