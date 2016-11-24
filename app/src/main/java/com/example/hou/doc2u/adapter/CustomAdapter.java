package com.example.hou.doc2u.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.hou.doc2u.R;
import com.example.hou.doc2u.app.AppController;
import com.example.hou.doc2u.model.Doctor;

import java.util.Currency;
import java.util.List;

/**
 * Created by Hou on 11/22/2016.
 */

public class CustomAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Doctor> doctorItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomAdapter(Activity activity, List<Doctor> doctorItems) {
        this.activity = activity;
        this.doctorItems = doctorItems;
    }

    @Override
    public int getCount() {
        return doctorItems.size();
    }

    @Override
    public Object getItem(int location) {
        return doctorItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row, null);
        }
        if (imageLoader == null) {
            imageLoader = AppController.getInstance().getImageLoader();
        }

        NetworkImageView thumbnail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView area = (TextView) convertView.findViewById(R.id.area);
        TextView speciality = (TextView) convertView.findViewById(R.id.speciality);
        TextView currency = (TextView) convertView.findViewById(R.id.currency);
        TextView rate = (TextView) convertView.findViewById(R.id.rate);

        Doctor doctor = doctorItems.get(position);

        thumbnail.setImageUrl(doctor.getThumbnailUrl(), imageLoader);

        name.setText(doctor.getName());
        area.setText(doctor.getArea());
        speciality.setText(doctor.getSpeciality());
        currency.setText(doctor.getCurrency());
        rate.setText(String.valueOf(doctor.getRate()));

        if(doctor.getId() == null) {
            //convertView = inflater.inflate(R.layout.empty_row, null);
            convertView.setVisibility(View.GONE);
        } else {
            convertView.setVisibility(View.VISIBLE);
        }

        return convertView;
    }
}
