package com.poipoipo.timeline.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.adapter.EventEditorAdapter;
import com.poipoipo.timeline.data.Event;
import com.poipoipo.timeline.data.MyEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EventEditorFragment extends DialogFragment
        implements Toolbar.OnMenuItemClickListener {
    private static final String TAG = "EventEditorFragment";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy     HH:mm", Locale.getDefault());
    RecyclerView recyclerView;
    Button start;
    Button end;
    View view;
    EventEditorListener mListener;
    Event event;
    Toolbar toolbar;
    private EventEditorAdapter adapter;
    private List<Map.Entry<Integer, Integer>> labelList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getActivity().getLayoutInflater().inflate(R.layout.fragment_edit_event, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.event_editor_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        start = (Button) view.findViewById(R.id.event_editor_start);
        end = (Button) view.findViewById(R.id.event_editor_end);
        toolbar = (Toolbar) view.findViewById(R.id.event_editor_toolbar);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        event = (Event) getArguments().getSerializable("event");
        labelList = event.getLabelList();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        adapter = new EventEditorAdapter(labelList, getActivity());
        recyclerView.setAdapter(adapter);
        start.setText(dateFormat.format(event.getStart() * 1000L));
        end.setText(dateFormat.format(event.getStart() * 1000L));
        toolbar.setTitle("Edit Event Info");
        toolbar.inflateMenu(R.menu.menu_event_editor);
        toolbar.setOnMenuItemClickListener(this);
        return builder.setView(view)
                .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onPositiveClick(event.getStart());
                    }
                })
                .setNegativeButton("CANCEL", null)
                .create();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                labelList.add(new MyEntry<>(2, 2));
                adapter.notifyItemInserted(labelList.size());
                Toast.makeText(getActivity(), "You Clicked Add", Toast.LENGTH_SHORT).show();
        }
        return false;
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
        Map<Integer, Integer> map = new LinkedHashMap<>();
        map.put(1, 1);
        map.put(2, 5);
        for (int i = 4; i < 9; i++) {
            map.put(i * 2, i * 3);
        }
        return map;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Log.d(TAG, "onDismiss: ");
        super.onDismiss(dialog);
    }

    public interface EventEditorListener {
        void onPositiveClick(int start);
    }
}
