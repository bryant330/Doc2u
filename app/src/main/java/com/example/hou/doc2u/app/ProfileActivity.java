package com.example.hou.doc2u.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.hou.doc2u.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    TextView recommendation;
    TextView schedule;
    TextView name;
    TextView area;
    TextView speciality;
    TextView currency;
    TextView experience;
    TextView description;
    NetworkImageView thumbnail;
    String imageURL = "";
    double latitude;
    double longtitude;
    LatLng latLng;
    private GoogleMap googleMap;
    String URL = "http://52.76.85.10/test/profile/";
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        recommendation = (TextView) findViewById(R.id.recommendation);
        schedule = (TextView) findViewById(R.id.schedule);
        name = (TextView) findViewById(R.id.name);
        area = (TextView) findViewById(R.id.area);
        speciality = (TextView) findViewById(R.id.speciality);
        currency = (TextView) findViewById(R.id.currency);
        experience = (TextView) findViewById(R.id.experience);
        description = (TextView) findViewById(R.id.description);
        thumbnail = (NetworkImageView) findViewById(R.id.thumbnail);
        latLng = new LatLng(0.0, 0.0);
        // Creates the Volley request queue
        requestQueue = AppController.getInstance().getRequestQueue();
        String doctorID = getIntent().getStringExtra("id");
        URL = URL + doctorID + ".json";
        makeRequest();
        try {
            initializeMap();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void makeRequest(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
                    recommendation.setText(response.getString("recommendation") + "\n Recommendations");
                    schedule.setText("Schedule on \n" + response.getString("schedule"));
                    name.setText(response.getString("name"));
                    area.setText(response.getString("area"));
                    speciality.setText(response.getString("speciality"));
                    currency.setText(response.getString("currency") + " " +response.getString("rate"));
                    experience.setText(response.getString("experience") + " Years experience");
                    description.setText(response.getString("description"));
                    latitude = response.getDouble("latitude");
                    longtitude = response.getDouble("longitute");
                    latLng = new LatLng(latitude, longtitude);
                    imageURL = response.getString("photo");
                    thumbnail.setImageUrl(imageURL, imageLoader);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        requestQueue.add(jsonObjectRequest);
    }

    private void initializeMap() {
        if (googleMap == null) {
            ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
                    // create marker
                    MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longtitude)).title("Doctor is here");
                    // adding marker
                    googleMap.addMarker(marker);
                }
            });
        }
    }

}
