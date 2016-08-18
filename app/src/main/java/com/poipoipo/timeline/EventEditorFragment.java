package com.poipoipo.timeline;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.poipoipo.timeline.data.Event;

public class EventEditorFragment extends DialogFragment {
    private static final String TAG = "EventEditorFragment";
    private Event event;

    public static EventEditorFragment newInstance(Event event) {
        Bundle args = new Bundle();
        args.putSerializable("event", event);
        EventEditorFragment fragment = new EventEditorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Event event = (Event) getArguments().getSerializable("event");
        Log.d(TAG, "onCreateDialog: event start" + event.getStart());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.fragment_edit_event).setTitle(event.getStart() + "");
        return builder.create();
    }

    public void update(Event event){
        this.event = event;
    }

}
