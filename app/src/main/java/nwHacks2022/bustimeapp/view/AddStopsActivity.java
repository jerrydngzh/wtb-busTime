package nwHacks2022.bustimeapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import nwHacks2022.bustimeapp.R;

public class AddStopsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stops);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddStopsActivity.class);
    }
}