package com.poipoipo.timeline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.database.DatabaseHelper;
import com.poipoipo.timeline.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventEditorAdapter
        extends RecyclerView.Adapter<EventEditorAdapter.LabelViewHolder> {
    private static final String TAG = "EventEditorAdapter";
    private List<Map.Entry<Integer, Integer>> labelList = new ArrayList<>();
    private Context context;
    private DatabaseHelper databaseHelper;

    public EventEditorAdapter(List<Map.Entry<Integer, Integer>> labelList, Context context) {
        this.labelList = labelList;
        this.context = context;
        databaseHelper = ((MainActivity) context).databaseHelper;
    }

    @Override
    public void onBindViewHolder(LabelViewHolder holder, int position) {
        final int key = labelList.get(position).getKey();
        final int value = labelList.get(position).getValue();
        holder.imageView.setImageResource(databaseHelper.getLabelIcon(key));
        if (position <= 1) {
            ArrayAdapter adapter = new ArrayAdapter<>(context,
                    android.R.layout.simple_spinner_dropdown_item,
                    new ArrayList<>(databaseHelper.labelMap.get(key).name.values()));
            holder.spinner.setAdapter(adapter);
            holder.spinner.setSelection(databaseHelper.labelMap.get(key).index.get(value));
            holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i + 1 == value) {
                        ((MainActivity) context).changeLog(key, 0, false);
                    } else {
                        ((MainActivity) context).changeLog(key, i + 1, true);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }
    }

    @Override
    public LabelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_label_edit, parent, false);
        return new LabelViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return labelList.size();
    }

    static class LabelViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        Spinner spinner;

        public LabelViewHolder(final View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.edit_label_icon);
            spinner = (Spinner) view.findViewById(R.id.edit_label_button);
        }
    }
}
