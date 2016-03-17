package com.example.sharath.moviesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sharath on 8/3/16.
 */
public class ImageListAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;

    private ArrayList<String> imageUrls;

    public ImageListAdapter(Context context, ArrayList<String> imageUrls) {
        super(context, R.layout.grid_image, imageUrls);

        this.context = context;
        this.imageUrls = imageUrls;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.grid_image, parent, false);
        }

        Picasso
                .with(context)
                .load(imageUrls.get(position))
//                .fit() // will explain later
                .into((ImageView) convertView);

        return convertView;
    }
}