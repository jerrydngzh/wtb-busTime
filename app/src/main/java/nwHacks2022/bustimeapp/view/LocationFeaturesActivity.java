package nwHacks2022.bustimeapp.view;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Tasks;
//import com.google.android.gms.common.api.GoogleApiClient;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import nwHacks2022.bustimeapp.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.net.ssl.HttpsURLConnection;


public class LocationFeaturesActivity extends AppCompatActivity {
    //    GoogleApiClient googleApiClient;
    FusedLocationProviderClient fusedLocationProviderClient;
    String longitude = "";
    String latitude = "";
    StringBuilder stringBuilder = new StringBuilder();
    InputStreamReader in = null;
    TextView info = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_features);

        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(100)
                .setFastestInterval(3000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(100);

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        //TODO: UI updates.
                    }
                }
            }
        };
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 2);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e("Welp, ", "We failed.");
            return;
        }
        LocationServices.getFusedLocationProviderClient(this.getApplicationContext()).requestLocationUpdates(locationRequest, locationCallback, null);

        LocationServices.getFusedLocationProviderClient(this.getApplicationContext()).getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    //System.out.println(location);
                    Log.e("Success?", String.valueOf(location));
                    info = findViewById(R.id.info_text);
                    //info.setText(String.valueOf(location).substring(15,24));
                    //info.setText(String.valueOf(location).substring(25,37));
                    info.setText(String.valueOf(location).substring(15,37));
                    latitude = String.valueOf(location).substring(15,24);
                    longitude = String.valueOf(location).substring(25,37);
                }
            }
        });

        //try {
        //    String baseURL = new String("https://api.translink.ca/rttiapi/v1/stops?apikey=D40X8fr5be6zO3Lqk8t9&lat=");
        //    baseURL.concat(latitude+"&long="+longitude);
        //    URL url = new URL(baseURL);
        //    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        //    if (connection.getResponseCode() == 200) {
        //        Log.e("Success.", "");
        //        System.exit(0);
        //    }
        //    //if (connection != null) {
        //    //    connection.setReadTimeout(5 * 1000);
        //    //    if (connection.getInputStream() != null) {
        //    //        in = new InputStreamReader(connection.getInputStream(), Charset.defaultCharset());
        //    //        BufferedReader bufferedReader = new BufferedReader(in);
        //    //        if (bufferedReader != null) {
        //    //            int characterCount;
        //    //            while ((characterCount = bufferedReader.read()) != -1) {
        //    //                stringBuilder.append((char) characterCount);
        //    //            }
        //    //            bufferedReader.close();
        //    //        }
        //    //    }
        //    //}
        //in.close();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //    System.exit(0);
        //}
//
        //info.setText(stringBuilder.toString());

        //if (Permissions()) {
        //    return;
        //}
        //TextView info = findViewById(R.id.info_text);
        //info.setText(String.valueOf(location));
//                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        // Got last known location. In some rare situations this can be null.
//                        if (location != null) {
//                            //System.out.println(location);
//                            Log.e("Success?", String.valueOf(location));
//                            TextView info = findViewById(R.id.info_text);
//                            info.setText(String.valueOf(location));
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        TextView info = findViewById(R.id.info_text);
//                        info.setText("We failed...");
//                }
//        });
    }
    //private boolean Permissions() {
    //    boolean fineLocation = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
    //    boolean coarseLocation = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;
    //    return fineLocation && coarseLocation;
    //}

//    @Override
//    protected void onStart() {
//        googleApiClient.connect();
//        super.onStart();
//    }
//    @Override
//    protected void onStop() {
//        googleApiClient.connect();
//        super.onStop();
//    }

}