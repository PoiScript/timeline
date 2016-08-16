package com.poipoipo.timeline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.data.Label;

import java.util.List;

public class ShowLabelAdapter
        extends RecyclerView.Adapter<ShowLabelAdapter.LabelsViewHolder> {
    private List<Label> labels;
    private Context context;

    public ShowLabelAdapter(List<Label> labels, Context context){
        this.labels = labels;
        this.context = context;
    }

    static class LabelsViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView info;
        public LabelsViewHolder(final View view){
            super(view);
            name = (TextView) view.findViewById(R.id.label_name);
            info = (TextView) view.findViewById(R.id.label_info);
        }
    }

    @Override
    public LabelsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_label_select, parent, false);
        LabelsViewHolder holder = new LabelsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(LabelsViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return labels.size();
    }
}
