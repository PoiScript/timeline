package com.poipoipo.timeline.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.data.Event;

public class DialogFragmentDetail extends DialogFragment {
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

    static DialogFragmentDetail newInstance(Event event) {
        DialogFragmentDetail fragment = new DialogFragmentDetail();
        Bundle args = new Bundle();
        args.putString(Event.CATEGORY, event.getCategory());
        args.putString(Event.TITLE, event.getTitle());
        args.putInt(Event.START, event.getStart());
        args.putInt(Event.END, event.getEnd());
        args.putString(Event.LOCATION, event.getLocation());
        args.putString(Event.NOTE, event.getNote());
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
        category.setText(getArguments().getString(Event.CATEGORY));
        title.setText(getArguments().getString(Event.TITLE));
        time.setText(getArguments().getInt(Event.START) + "");
        location.setText(getArguments().getString(Event.LOCATION));
        note.setText(getArguments().getString(Event.NOTE));
        return builder.create();
    }
}
