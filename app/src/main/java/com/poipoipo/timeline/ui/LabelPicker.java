package com.poipoipo.timeline.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.adapter.SelectLabelAdapter;
import com.poipoipo.timeline.data.Label;
import com.poipoipo.timeline.database.DatabaseHelper;

import java.util.ArrayList;

public class LabelPicker extends DialogFragment {
    private final static String TAG = "LabelPicker";
    private View view;

    public static LabelPicker newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(Label.TYPE, type);
        LabelPicker fragment = new LabelPicker();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = getActivity();
        view = activity.getLayoutInflater().inflate(R.layout.fragment_label_picker, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_label_picker);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(manager);
        DatabaseHelper databaseHelper = new DatabaseHelper(activity);
        switch (getArguments().getInt(Label.TYPE)) {
            case Label.LOCATION:
                RecyclerView.Adapter adapter = new SelectLabelAdapter(new ArrayList<>(databaseHelper.titles.values()), activity);
                recyclerView.setAdapter(adapter);
                Log.d(TAG, "onCreate: case location");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        return builder.create();
    }
}
