package com.poipoipo.timeline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.database.DatabaseHelper;
import com.poipoipo.timeline.ui.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventEditorAdapter
        extends RecyclerView.Adapter<EventEditorAdapter.LabelViewHolder> {
    private static final String TAG = "EventEditorAdapter";
    private List<Map.Entry<Integer, Integer>> labelList = new ArrayList<>();
    private Map<Integer, String> labelNameMap = new HashMap<>();
    private OnItemChangedListener mListenre;
    private Context context;
    private DatabaseHelper databaseHelper;

    public EventEditorAdapter(List<Map.Entry<Integer, Integer>> labelList, Context context, OnItemChangedListener mListenre) {
        this.labelList = labelList;
        this.context = context;
        this.mListenre = mListenre;
        databaseHelper = ((MainActivity) context).databaseHelper;
        labelNameMap = databaseHelper.labelNameMap;
    }

    @Override
    public void onBindViewHolder(final LabelViewHolder holder, int position) {
        final int key = labelList.get(position).getKey();
        final int value = labelList.get(position).getValue();
        holder.imageButton.setImageResource(databaseHelper.getLabelIcon(key));
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popup = new PopupMenu(context, view);
                int i = 0;
                for (Map.Entry<Integer, String> entry : labelNameMap.entrySet()) {
                    popup.getMenu().add(1, entry.getKey(), i++, entry.getValue());
                }
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        mListenre.onItemChange(233, 233);
                        setSpinner(holder, menuItem.getItemId(), 0);
                        holder.imageButton.setImageResource(databaseHelper.getLabelIcon(menuItem.getItemId()));
                        return false;
                    }
                });
                popup.show();
            }
        });
        if (position <= 1 && key != 999) {
            setSpinner(holder, key, value);
        }
    }

    private void setSpinner(LabelViewHolder holder, final int label, int position) {
        ArrayAdapter adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_dropdown_item,
                new ArrayList<>(databaseHelper.labelMap.get(label).name.values()));
        holder.spinner.setAdapter(adapter);
//        holder.spinner.setSelection(databaseHelper.labelMap.get(label).index.get(position));
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mListenre.onItemChange(label, ++i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
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

    public interface OnItemChangedListener {
        void onItemChange(int key, int value);
    }

    static class LabelViewHolder extends RecyclerView.ViewHolder {
        ImageButton imageButton;
        Spinner spinner;

        public LabelViewHolder(final View view) {
            super(view);
            imageButton = (ImageButton) view.findViewById(R.id.edit_label_icon);
            spinner = (Spinner) view.findViewById(R.id.edit_label_button);
        }
    }
}
