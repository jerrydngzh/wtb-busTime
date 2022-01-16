package nwHacks2022.bustimeapp.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import nwHacks2022.bustimeapp.R;
import nwHacks2022.bustimeapp.controller.StopManager;
import nwHacks2022.bustimeapp.model.BusStop;

public class ListStopsActivity extends AppCompatActivity {
    private StopManager stopManager = StopManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_stops);

        stopManager.add(new BusStop("Hello", 10, 10, "1--", "100", "00"));

        showStops();
    }

    private void showStops() {
        ArrayAdapter<BusStop> stopAdapter = new StopListAdapter();
        ListView busList = findViewById(R.id.stops_listview);
        busList.setAdapter(stopAdapter);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, ListStopsActivity.class);
    }

    private class StopListAdapter extends ArrayAdapter<BusStop> {

        public StopListAdapter() {
            super(ListStopsActivity.this, R.layout.layout_bus_list, stopManager.getAll());
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            BusStop currentStop = stopManager.get(position);
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.layout_bus_list, parent, false);
            }

            TextView nameView = itemView.findViewById(R.id.layout_bus_name);
            nameView.setText(currentStop.getName());

            return itemView;
        }

    }
}