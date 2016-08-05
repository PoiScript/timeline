package com.poipoipo.timeline.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.data.Event;

public class DetailDialogFragment extends DialogFragment {
    LayoutInflater inflater;
    View view;
    private TextView category;
    private EditText title;
    private EditText time;
    private EditText location;
    private EditText note;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            inflater = getActivity().getLayoutInflater();
            view = inflater.inflate(R.layout.dialog_detail, null);
            category = (EditText) view.findViewById(R.id.dialog_category);
            title = (EditText) view.findViewById(R.id.dialog_title);
            time = (EditText) view.findViewById(R.id.dialog_time);
            location = (EditText) view.findViewById(R.id.dialog_location);
            note = (EditText) view.findViewById(R.id.dialog_note);
    }

    static DetailDialogFragment newInstance(Event event) {
        DetailDialogFragment fragment = new DetailDialogFragment();
        Bundle args = new Bundle();
        args.putInt(Event.CATEGORY, event.getCategory());
        args.putInt(Event.TITLE, event.getTitle());
        args.putInt(Event.START, event.getStart());
        args.putInt(Event.END, event.getEnd());
        args.putInt(Event.LOCATION, event.getLocation());
        args.putInt(Event.NOTE, event.getNote());
        fragment.setArguments(args);
        return fragment;
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((MainActivity) getActivity()).doPositiveClick();
                    }
                });
        return builder.create();
    }
}
