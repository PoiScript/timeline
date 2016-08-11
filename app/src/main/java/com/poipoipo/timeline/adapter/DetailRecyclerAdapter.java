package com.poipoipo.timeline.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.data.Label;
import com.poipoipo.timeline.ui.EditActivity;

import java.util.List;

public class DetailRecyclerAdapter
        extends RecyclerView.Adapter<DetailRecyclerAdapter.LabelsViewHolder> {
    private List<Label> labels;
    private Context context;

    public DetailRecyclerAdapter(List<Label> labels, Context context){
        this.labels = labels;
        this.context = context;
    }

    static class LabelsViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        public LabelsViewHolder(final View view){
            super(view);
            imageView = (ImageView) view.findViewById(R.id.dialog_label_icon);
            textView = (TextView) view.findViewById(R.id.dialog_label_text);
        }
    }

    @Override
    public LabelsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_label, parent, false);
        LabelsViewHolder holder = new LabelsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(LabelsViewHolder holder, int position) {
        holder.textView.setText("Test");
    }

    @Override
    public int getItemCount() {
        return labels.size();
    }
}
