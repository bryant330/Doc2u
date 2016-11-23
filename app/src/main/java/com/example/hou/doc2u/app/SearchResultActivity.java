package com.example.hou.doc2u.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.LauncherApps;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.telecom.Call;
import android.telecom.RemoteConnection;
import android.util.Log;
import android.view.InputQueue;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

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

    private Spinner spinner;
    private ArrayList<String> pageNumbers = new ArrayList<String>();
    private LinearLayout mLinearScroll;
    private List<Doctor> doctorListTemp = new ArrayList<Doctor>();
    final int rowSize = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        mLinearScroll = (LinearLayout) findViewById(R.id.linear_scroll);
        spinner = (Spinner) findViewById(R.id.spinner);

        listView = (ListView) findViewById(R.id.list);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        parseDoctorDetails(new CallBack() {
            @Override
            public void onSuccess(List<Doctor> doctorDetails) {
                int rem = doctorDetails.size() % rowSize;
                if (rem > 0) {
                    for (int i = 0; i < rowSize - rem; i++) {
                        Doctor doctor = new Doctor();
                        doctorDetails.add(doctor);
                    }
                }

                addItem(0);

                int size = doctorDetails.size() / rowSize;

                for (int j = 0; j < size; j++) {
                    pageNumbers.add(String.valueOf(j + 1));
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(120, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(5, 2, 2, 2);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchResultActivity.this, R.layout.support_simple_spinner_dropdown_item, pageNumbers);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        addItem(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    private void hideDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    private void addItem(int i) {
        doctorListTemp.clear();
        i = i * rowSize;
        // fill temp array list to set on page change
        for (int j = 0; j < rowSize; j++) {
            doctorListTemp.add(j, doctorList.get(i));
            i = i + 1;
        }

        setView();
    }

    private void setView() {
        adapter = new CustomAdapter(this, doctorListTemp);
        listView.setAdapter(adapter);
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

    public void parseDoctorDetails(final CallBack onCallBack) {
        final JsonArrayRequest doctorRequest = new JsonArrayRequest(URL,
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
                                onCallBack.onFail(e.toString());
                            }
                        }
                        onCallBack.onSuccess(doctorList);
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
    }

    public interface CallBack {
        void onSuccess(List<Doctor> doctorDetails);

        void onFail(String message);
    }

}
