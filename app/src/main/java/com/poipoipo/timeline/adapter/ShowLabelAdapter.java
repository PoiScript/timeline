package com.poipoipo.timeline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_label_show, parent, false);
        LabelsViewHolder holder = new LabelsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(LabelsViewHolder holder, int position) {
        Label label = labels.get(holder.getAdapterPosition());
        switch (label.getType()){
            case Label.START:
                holder.imageView.setImageResource(R.drawable.ic_time);
                break;
            case Label.END:
                holder.imageView.setImageResource(R.drawable.ic_empty);
                break;
            case Label.LOCATION:
                holder.imageView.setImageResource(R.drawable.ic_location);
                break;
            case Label.TEACHER:
                holder.imageView.setImageResource(R.drawable.ic_teacher);
                break;
            case Label.COST:
                holder.imageView.setImageResource(R.drawable.ic_cost);
                break;
            case Label.NOTE:
                holder.imageView.setImageResource(R.drawable.ic_note);
        }
        holder.textView.setText(label.getValue());
    }

    @Override
    public int getItemCount() {
        return labels.size();
    }
}
