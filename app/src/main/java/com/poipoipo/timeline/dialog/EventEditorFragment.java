package com.poipoipo.timeline.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.adapter.EditLabelAdapter;
import com.poipoipo.timeline.data.Event;

public class EventEditorFragment extends DialogFragment {
    private static final String TAG = "EventEditorFragment";
    RecyclerView recyclerView;
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
        Log.d(TAG, "onCreate: ");
        view = getActivity().getLayoutInflater().inflate(R.layout.fragment_edit_event, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.event_editor_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        event = (Event) getArguments().getSerializable("event");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        recyclerView.setAdapter(new EditLabelAdapter(event.getAllLabelList(), getActivity()));
        return builder.setView(view)
                .setTitle(event.getStart()+ "")
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
        } catch (ClassCastException e){
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
}
