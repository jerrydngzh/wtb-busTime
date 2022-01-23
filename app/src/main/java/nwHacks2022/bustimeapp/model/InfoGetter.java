package nwHacks2022.bustimeapp.model;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.telephony.SmsManager;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class InfoGetter {
    public static final String BUS_NUMBER = "33333";
    public static final String apiKey = "D40X8fr5be6zO3Lqk8t9";


    public static void sendMessage(String msg) throws Exception {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(BUS_NUMBER, null, msg, null, null);
        } catch (Exception e) {
            throw new IOException("Could not send message!");
        }
    }

    public static Location getCoodrinates(Context context, String stopNumber) {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = (String.format("https://api.translink.ca/rttiapi/v1/stops/%s?apikey=%s", stopNumber, apiKey));

        Location busLocation = new Location("API Info");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    double stopLat = response.getDouble("Latitude");
                    double stopLong = response.getDouble("Longitude");


                    busLocation.setLongitude(stopLong);
                    busLocation.setLatitude(stopLat);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("Information", busLocation.toString() + "" + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Didn't work!");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();
                params.put("Accept", "application/json");
                params.put("Content-Type", "application/json");

                return params;
            }
        };


        queue.add(jsonObjectRequest);

        return busLocation;
    }

}
