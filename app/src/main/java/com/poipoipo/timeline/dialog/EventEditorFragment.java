package com.poipoipo.timeline.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.adapter.EventEditorAdapter;
import com.poipoipo.timeline.data.Event;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EventEditorFragment extends DialogFragment {
    private static final String TAG = "EventEditorFragment";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy     HH:mm", Locale.getDefault());
    RecyclerView recyclerView;
    Button start;
    Button end;
    View view;
    EventEditorListener mListener;
    Event event;

    public interface EventEditorListener {
        void onPositiveClick(DialogFragment fragment);

        void onNegativeClick(DialogFragment fragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getActivity().getLayoutInflater().inflate(R.layout.fragment_edit_event, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.event_editor_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        start = (Button) view.findViewById(R.id.event_editor_start);
        end = (Button) view.findViewById(R.id.event_editor_end);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        event = (Event) getArguments().getSerializable("event");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        recyclerView.setAdapter(new EventEditorAdapter(init(), getActivity()));
        start.setText(dateFormat.format(event.getStart() * 1000L));
        end.setText(dateFormat.format(event.getStart() * 1000L));
        return builder.setView(view)
                .setTitle("Edit Event Info")
                .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onPositiveClick(EventEditorFragment.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onNegativeClick(EventEditorFragment.this);
                    }
                })
                .create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (EventEditorListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement EventEditorListener");
        }
    }

    public void update(Event event) {
        if (getArguments() != null) {
            getArguments().putSerializable("event", event);
        } else {
            Bundle args = new Bundle();
            args.putSerializable("event", event);
            setArguments(args);
        }
    }

    private Map<Integer, Integer> init() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
        map.put(2, 5);
        for (int i = 4; i < 9; i++) {
            map.put(i * 2, i * 3);
        }
        return map;
    }
}
