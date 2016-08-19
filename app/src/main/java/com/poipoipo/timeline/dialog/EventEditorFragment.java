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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EventEditorFragment extends DialogFragment
        implements Toolbar.OnMenuItemClickListener,
        View.OnClickListener,
        EventEditorAdapter.OnItemChangedListener,
        TimePickerFragment.OnTimeSetListener,
        DatePickerFragment.OnDateSetListener {
    private static final String TAG = "EventEditorFragment";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy     HH:mm", Locale.getDefault());
    View view;
    Event event;
    Toolbar toolbar;
    private List<Map.Entry<Integer, Integer>> labelList = new ArrayList<>();
    private Map<Integer, Integer> changeLog = new HashMap<>();
    private EventEditorListener mListener;
    private EventEditorAdapter adapter;
    private RecyclerView recyclerView;
    private Calendar startCalendar = Calendar.getInstance();
    private Calendar endCalendar = Calendar.getInstance();
    private Button start;
    private Button end;

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
        start = (Button) view.findViewById(R.id.event_editor_start);
        end = (Button) view.findViewById(R.id.event_editor_end);
        toolbar = (Toolbar) view.findViewById(R.id.event_editor_toolbar);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        event = (Event) getArguments().getSerializable("event");
        startCalendar.setTimeInMillis(event.getStart() * 1000L);
        endCalendar.setTimeInMillis(event.getEnd() * 1000L);
        try {
            labelList = event.getLabelList();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return builder.setMessage("Error").create();
        }
        adapter = new EventEditorAdapter(labelList, getActivity(), this);
        recyclerView.setAdapter(adapter);
        start.setText(dateFormat.format(event.getStart() * 1000L));
        end.setText(dateFormat.format(event.getStart() * 1000L));
        end.setOnClickListener(this);
        start.setOnClickListener(this);
        toolbar.setTitle("Edit Event Info");
        toolbar.inflateMenu(R.menu.menu_event_editor);
        toolbar.setOnMenuItemClickListener(this);
        return builder.setView(view)
                .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onPositiveClick(event.getStart(), changeLog);
                    }
                })
                .setNegativeButton("CANCEL", null)
                .create();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.event_editor_start:
                DialogFragment fragment = TimePickerFragment.newInstance(Calendar.getInstance(), 1);
                fragment.setTargetFragment(this, 0);
                fragment.show(getFragmentManager(), "timePicker");
                break;
            case R.id.event_editor_end:
                DialogFragment fragment1 = TimePickerFragment.newInstance(Calendar.getInstance(), 2);
                fragment1.setTargetFragment(this, 0);
                fragment1.show(getFragmentManager(), "timePicker");
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                labelList.add(new MyEntry<>(1, 0));
                adapter.notifyItemInserted(labelList.size());
                changeLog.put(1, 0);
        }
        return false;
    }

    @Override
    public void onTimeSet(int type, int hour, int minute) {
        switch (type) {
            case Event.TYPE_START:
                startCalendar.set(Calendar.HOUR, hour);
                startCalendar.set(Calendar.MINUTE, minute);
                break;
            case Event.TYPE_END:
                endCalendar.set(Calendar.HOUR, hour);
                endCalendar.set(Calendar.MINUTE, minute);
        }
        Toast.makeText(getActivity(), "type = " + type + " hour = " + hour + " minute = " + minute, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateSet(int type, int year, int month, int day) {
        switch (type) {
            case Event.TYPE_START:
                startCalendar.set(Calendar.YEAR, year);
                startCalendar.set(Calendar.MONTH, month);
                startCalendar.set(Calendar.DAY_OF_MONTH, day);
                break;
            case Event.TYPE_END:
                endCalendar.set(Calendar.YEAR, year);
                endCalendar.set(Calendar.MONTH, month);
                endCalendar.set(Calendar.DAY_OF_MONTH, day);
        }
        Toast.makeText(getActivity(), "type = " + type + " year = " + year + " month = " + month + " day = " + day, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onItemChange(int key, int value) {
        changeLog.put(key, value);
        Toast.makeText(getActivity(), "Test", Toast.LENGTH_SHORT).show();
    }

    public interface EventEditorListener {
        void onPositiveClick(int start, Map<Integer, Integer> changeLog);
    }
}
