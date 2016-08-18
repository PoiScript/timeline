package com.poipoipo.timeline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.database.DatabaseHelper;
import com.poipoipo.timeline.ui.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class EventEditorAdapter extends RecyclerView.Adapter<EventEditorAdapter.LabelViewHolder> {
    private static final String TAG = "EventEditorAdapter";
    private Map<Integer, Integer> index = new HashMap<>();
    private Map<Integer, Integer> labels;
    private Context context;
    private DatabaseHelper databaseHelper;

    public EventEditorAdapter(Map<Integer, Integer> labels, Context context) {
        this.labels = labels;
        this.context = context;
        int i = 0;
        for (Integer key : labels.keySet()) {
            index.put(i++, key);
        }
        databaseHelper = ((MainActivity) context).databaseHelper;
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

    @Override
    public LabelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_label_edit, parent, false);
        return new LabelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LabelViewHolder holder, int position) {
//        Log.d(TAG, "onBindViewHolder: key" + index.get(position));
//        Log.d(TAG, "onBindViewHolder: value" + labels.get(index.get(position)));

    }

    @Override
    public int getItemCount() {
        return labels.size();
    }
}
