package com.poipoipo.timeline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.poipoipo.timeline.R;

import java.util.List;

public class SelectLabelAdapter
        extends RecyclerView.Adapter<SelectLabelAdapter.LabelsViewHolder> {
    private List<String> strings;
    private Context context;

    public SelectLabelAdapter(List<String> strings, Context context){
        this.strings = strings;
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
        String s = strings.get(holder.getAdapterPosition());
        holder.textView.setText(s);
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }
}
