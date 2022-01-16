package nwHacks2022.bustimeapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.w3c.dom.Text;

import nwHacks2022.bustimeapp.R;
import nwHacks2022.bustimeapp.controller.StopManager;
import nwHacks2022.bustimeapp.model.BusStop;

public class AddStopsActivity extends AppCompatActivity {

    StopManager stopManager = StopManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stops);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_save) {
            saveStop();
        }

        if (item.getItemId() == R.id.item_delete) {
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_delete_appbar, menu);
        return true;
    }

    private void saveStop() {
        EditText stopName = findViewById(R.id.stop_name_field);
        TextView nfcCode = findViewById(R.id.nfc_info_text);
        EditText lat = findViewById(R.id.lat_field);
        EditText lon = findViewById(R.id.long_field);
        EditText busNo = findViewById(R.id.bus_number_field);
        EditText busStopNo = findViewById(R.id.stop_number_field);

        try {
            String name = stopName.getText().toString();
            String id = nfcCode.getText().toString();
            String latitude = lat.getText().toString();
            String longitude = lon.getText().toString();
            String busNum = busNo.getText().toString();
            String busStop = busStopNo.getText().toString();

            stopManager.add(new BusStop(name, Integer.parseInt(busNum), Integer.parseInt(busStop), latitude, longitude, id));
            finish();
        } catch (Exception e) {
            new MaterialAlertDialogBuilder(AddStopsActivity.this)
                    .setTitle("Warning")
                    .setMessage("You have unfilled fields!")
                    .show();
        }

    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddStopsActivity.class);
    }

    public static Intent makeIntent(Context context, int index) {
        return new Intent(context, AddStopsActivity.class);
    }
}