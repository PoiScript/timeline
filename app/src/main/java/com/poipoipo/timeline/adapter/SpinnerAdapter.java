package com.poipoipo.timeline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.poipoipo.timeline.R;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {
    Context context;
    List<String> texts;
    List<Integer> images;

    public SpinnerAdapter(Context context, List<String> texts, List<Integer> images) {
        super(context, android.R.layout.simple_spinner_dropdown_item, texts);
        this.context = context;
        this.texts = texts;
        this.images = images;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner1, parent, false);
        }
        ImageView image = (ImageView) convertView.findViewById(R.id.spinner_image);

        image.setImageResource(images.get(position));
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }

    //    @Override
//    public View getDropDownView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false);
//        }
//
//
//
//        TextView text = (TextView) convertView.findViewById(R.id.spinner_text);
//        ImageView image = (ImageView) convertView.findViewById(R.id.spinner_image);
//
//        text.setText(texts.get(position));
//        image.setImageResource(images.get(position));
//        return convertView;
//    }
}
