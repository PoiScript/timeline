package com.poipoipo.timeline.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.data.Label;
import com.poipoipo.timeline.database.DatabaseHelper;
import com.poipoipo.timeline.ui.LabelPicker;

import java.util.List;

public class EditLabelAdapter
        extends RecyclerView.Adapter<EditLabelAdapter.LabelsViewHolderS> {
    private static final String TAG = "EditLabelAdapter";
    private List<Label> labels;
    private Context context;

    public EditLabelAdapter(List<Label> labels, Context context) {
        this.labels = labels;
        this.context = context;
    }

    static class LabelsViewHolderS extends RecyclerView.ViewHolder {
        ImageView imageView;
        Spinner spinner;

        public LabelsViewHolderS(final View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.edit_label_icon);
            spinner = (Spinner) view.findViewById(R.id.edit_label_button);
        }
    }

    @Override
    public LabelsViewHolderS onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_label_edit, parent, false);
        return new LabelsViewHolderS(view);
    }

    @Override
    public void onBindViewHolder(LabelsViewHolderS holder, int position) {
        Label label = labels.get(holder.getAdapterPosition());
        switch (label.getType()) {
            case Label.START:
                holder.imageView.setImageResource(R.drawable.ic_time);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                        R.array.pref_example_list_titles, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                holder.spinner.setAdapter(adapter);
                break;
            case Label.END:
                holder.imageView.setImageResource(R.drawable.ic_empty);
//                holder.spinner.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        picker = LabelPicker.newInstance(Label.START);
//                        picker.show(manager, Label.TYPE);
//                    }
//                });
                break;
            case Label.LOCATION:
                holder.imageView.setImageResource(R.drawable.ic_location);
//                holder.spinner.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        picker = LabelPicker.newInstance(Label.LOCATION);
//                        picker.show(manager, Label.TYPE);
//                        Log.d(TAG, "onClick: end");
//                    }
//                });
                break;
            case Label.TEACHER:
                holder.imageView.setImageResource(R.drawable.ic_teacher);
//                holder.spinner.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        picker = LabelPicker.newInstance(Label.START);
//                        picker.show(manager, Label.TYPE);
//                        Log.d(TAG, "onClick: teacher");
//                    }
//                });
                break;
            case Label.COST:
                holder.imageView.setImageResource(R.drawable.ic_cost);
//                holder.spinner.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        picker = LabelPicker.newInstance(Label.START);
//                        picker.show(manager, Label.TYPE);
//                        Log.d(TAG, "onClick: cost");
//                    }
//                });
//                break;
            case Label.NOTE:
                holder.imageView.setImageResource(R.drawable.ic_note);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return labels.size();
    }

}
