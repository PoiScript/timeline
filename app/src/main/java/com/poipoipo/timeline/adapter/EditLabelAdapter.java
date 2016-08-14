package com.poipoipo.timeline.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.data.Label;
import com.poipoipo.timeline.ui.LabelPicker;

import java.util.List;

public class EditLabelAdapter
        extends RecyclerView.Adapter<EditLabelAdapter.LabelsViewHolder> {
    private static final String TAG = "EditLabelAdapter";
    private LabelPicker picker;
    private FragmentManager manager;
    private List<Label> labels;
    private Context context;

    public EditLabelAdapter(List<Label> labels, Context context) {
        this.labels = labels;
        this.context = context;
        manager = ((Activity) context).getFragmentManager();
    }

    static class LabelsViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        Button button;

        public LabelsViewHolder(final View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.edit_label_icon);
            button = (Button) view.findViewById(R.id.edit_label_button);
        }
    }

    @Override
    public LabelsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_label_edit, parent, false);
        LabelsViewHolder holder = new LabelsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(LabelsViewHolder holder, int position) {
        Label label = labels.get(holder.getAdapterPosition());
        holder.button.setText(label.getValue());
        switch (label.getType()) {
            case Label.START:
                holder.imageView.setImageResource(R.drawable.ic_time);
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        picker = LabelPicker.newInstance(Label.START);
                        picker.show(manager, Label.TYPE);
                    }
                });
                break;
            case Label.END:
                holder.imageView.setImageResource(R.drawable.ic_empty);
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        picker = LabelPicker.newInstance(Label.START);
                        picker.show(manager, Label.TYPE);
                    }
                });
                break;
            case Label.LOCATION:
                holder.imageView.setImageResource(R.drawable.ic_location);
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        picker = LabelPicker.newInstance(Label.LOCATION);
                        picker.show(manager, Label.TYPE);
                        Log.d(TAG, "onClick: end");
                    }
                });
                break;
            case Label.TEACHER:
                holder.imageView.setImageResource(R.drawable.ic_teacher);
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        picker = LabelPicker.newInstance(Label.START);
                        picker.show(manager, Label.TYPE);
                        Log.d(TAG, "onClick: teacher");
                    }
                });
                break;
            case Label.COST:
                holder.imageView.setImageResource(R.drawable.ic_cost);
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        picker = LabelPicker.newInstance(Label.START);
                        picker.show(manager, Label.TYPE);
                        Log.d(TAG, "onClick: cost");
                    }
                });
                break;
            case Label.NOTE:
                holder.imageView.setImageResource(R.drawable.ic_note);
        }
    }

    @Override
    public int getItemCount() {
        return labels.size();
    }

}
