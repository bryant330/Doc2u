package com.example.hou.doc2u.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.hou.doc2u.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> responseList;
    String JsonURL = "http://52.76.85.10/test/location.json";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        responseList = new ArrayList<String>();

        JsonArrayRequest arrayRequest = new JsonArrayRequest(JsonURL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jResponse = response.getJSONObject(i);
                                String area = jResponse.getString("area");
                                String city = jResponse.getString("city");
                                String place = area + ", " + city;
                                responseList.add(place);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Volley","error");
                    }
                }
        );
        requestQueue.add(arrayRequest);

        AutoCompleteTextView mAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleeteTextView);
        Button button = (Button) findViewById(R.id.button);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, responseList);
        mAutoCompleteTextView.setThreshold(1);
        mAutoCompleteTextView.setAdapter(adapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
                startActivity(intent);
            }
        });
    }

}
