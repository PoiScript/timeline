package com.poipoipo.timeline.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.adapter.EventEditorAdapter;
import com.poipoipo.timeline.data.EditedMessageEvent;
import com.poipoipo.timeline.data.Event;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class EventEditorFragment extends DialogFragment
        implements Toolbar.OnMenuItemClickListener,
        EventEditorAdapter.OnEventChangedListener {
    private static final String TAG = "EventEditorFragment";
    View view;
    Event event;
    Toolbar toolbar;
    private Map<Integer, Integer> changeLog = new HashMap<>();
    private EventEditorListener mListener;
    private EventEditorAdapter adapter;
    private RecyclerView recyclerView;
    private Snackbar errorSnackbar;

    public static EventEditorFragment newInstance(Event event) {
        Bundle args = new Bundle();
        args.putSerializable("event", event);
        EventEditorFragment fragment = new EventEditorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(getActivity(), R.layout.fragment_edit_event, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.event_editor_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        toolbar = (Toolbar) view.findViewById(R.id.event_editor_toolbar);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        changeLog.clear();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        event = (Event) getArguments().getSerializable("event");
        adapter = new EventEditorAdapter(event, getActivity(), this);
        recyclerView.setAdapter(adapter);
//        recyclerView.addItemDecoration(new MyDecoration(getActivity(), MyDecoration.VERTICAL_LIST));
        toolbar.setTitle("Edit Event Info");
        toolbar.inflateMenu(R.menu.menu_event_editor);
        toolbar.setOnMenuItemClickListener(this);
        builder.setView(view)
                .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onPositiveClick(event.getId(), changeLog);
//                        changeLog.put(Event.POSITION, getArguments().getInt("position"));
                        EventBus.getDefault().post(new EditedMessageEvent(getArguments().getInt("position"), changeLog));
                    }
                })
                .setNegativeButton("CANCEL", null);
        return builder.create();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    public void update(Event event, int position) {
        if (getArguments() != null) {
            getArguments().putSerializable("event", event);
            getArguments().putInt("position", position);
        } else {
            Bundle args = new Bundle();
            args.putSerializable("event", event);
            args.putInt("position", position);
            setArguments(args);
        }
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(adapter);
        super.onStop();
    }

    @Override
    public void onEventChanged(int key, final int value) {
        Log.d(TAG, "onEventChanged: get key = " + key + " value = " + value);
        changeLog.put(key, value);
        if (errorSnackbar == null) {
            errorSnackbar = Snackbar.make(recyclerView, R.string.editor_error_time, Snackbar.LENGTH_INDEFINITE);
        }
        if (changeLog.containsKey(Event.ERROR_TIME)) {
            errorSnackbar.setAction("Reset", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(changeLog.get(Event.ERROR_TIME));
                    errorSnackbar.dismiss();
                    changeLog.remove(Event.ERROR_TIME);
                }
            }).show();
        }
//        if ((changeLog.containsKey(Event.ERROR_TIME) && key == Event.ERROR_TIME) || (changeLog.containsKey(Event.ERROR_TIME) && !errorSnackbar.isShown())) {
//            errorSnackbar.setText(R.string.editor_error_time).show();
//        } else if ((changeLog.containsKey(Event.ERROR_LABEL) && key == Event.ERROR_LABEL) || (changeLog.containsKey(Event.ERROR_LABEL) && !errorSnackbar.isShown())) {
//            errorSnackbar.setText(R.string.editor_error_label).show();
//        }
    }

    @Override
    public void onKeyRemoved(int key) {
        if (errorSnackbar == null) {
            errorSnackbar = Snackbar.make(recyclerView, R.string.editor_error_time, Snackbar.LENGTH_INDEFINITE);
        }
        if (changeLog.containsKey(key)) {
//            if (errorSnackbar.isShown() && changeLog.containsKey(Event.ERROR_LABEL) && key == Event.ERROR_TIME) {
//                errorSnackbar.setText(R.string.editor_error_label);
//            }
//            if (errorSnackbar.isShown() && changeLog.containsKey(Event.ERROR_TIME) && key == Event.ERROR_LABEL) {
//                errorSnackbar.setText(R.string.editor_error_time);
//            }
            changeLog.remove(key);
            if (!changeLog.containsKey(Event.ERROR_TIME)) {
                errorSnackbar.dismiss();
            }
//            if (!changeLog.containsKey(Event.ERROR_LABEL) && !changeLog.containsKey(Event.ERROR_TIME) && errorSnackbar.isShown()) {
//                errorSnackbar.dismiss();
//            }
            Log.d(TAG, "onKeyRemoved: remove key = " + key);
        }
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

    public interface EventEditorListener {
        void onPositiveClick(int start, Map<Integer, Integer> changeLog);
    }
}
