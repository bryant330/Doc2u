package com.example.hou.doc2u.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.hou.doc2u.R;
import com.example.hou.doc2u.adapter.CustomAdapter;
import com.example.hou.doc2u.model.Doctor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    private static final String URL = "http://52.76.85.10/test/datalist.json";
    private ProgressDialog dialog;
    private List<Doctor> doctorList = new ArrayList<Doctor>();
    private ListView listView;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomAdapter(this, doctorList);
        listView.setAdapter(adapter);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        JsonArrayRequest doctorRequest = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        hideDialog();

                        for(int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Doctor doctor = new Doctor();
                                doctor.setId(obj.getString("id"));
                                doctor.setThumbnailUrl(obj.getString("photo"));
                                doctor.setName(obj.getString("name"));
                                doctor.setArea(obj.getString("area"));
                                doctor.setSpeciality(obj.getString("speciality"));
                                doctor.setCurrency(obj.getString("currency"));
                                doctor.setRate(obj.getInt("rate"));
                                doctorList.add(doctor);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("test", "Error: " + error.getMessage());
                hideDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(doctorRequest);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Doctor doctor = (Doctor) adapterView.getItemAtPosition(position);

                Intent intent = new Intent(SearchResultActivity.this, ProfileActivity.class);
                intent.putExtra("id",doctor.getId());
                startActivity(intent);
            }
        });
    }

    private void hideDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
