package com.poipoipo.timeline.ui;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.adapter.EventCardAdapter;
import com.poipoipo.timeline.data.Event;
import com.poipoipo.timeline.data.TimestampUtil;
import com.poipoipo.timeline.dialog.DatePickerFragment;
import com.poipoipo.timeline.dialog.EventEditorFragment;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FragmentTimeline extends Fragment
        implements View.OnClickListener,
        View.OnLongClickListener,
        Toolbar.OnMenuItemClickListener,
        DatePickerFragment.OnDateSetListener {
    private static final String TAG = "FragmentTimeline";
    EventEditorFragment eventEditor;
    private SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
    private MainActivity mainActivity;
    private Calendar calendar = Calendar.getInstance();
    private DialogFragment datePicker;
    private EventCardAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timeline, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        int todayTimestamp = TimestampUtil.getTodayTimestamp();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.timeline_toolbar);
        toolbar.inflateMenu(R.menu.menu_timeline);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(this);
        toolbar.setOnMenuItemClickListener(this);
        Button editDate = (Button) view.findViewById(R.id.toolbar_date);
        editDate.setOnClickListener(this);
        editDate.setText(format.format(TimestampUtil.getTodayTimestamp() * 1000L));
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new EventCardAdapter(mainActivity.databaseHelper.query(todayTimestamp, todayTimestamp + 24 * 60 * 60), mainActivity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        fab.setOnLongClickListener(this);
        Button button = (Button) toolbar.findViewById(R.id.toolbar_date);
        button.setOnClickListener(this);
        datePicker = DatePickerFragment.newInstance(2, 3, 3, 3);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_date:
                datePicker.setTargetFragment(this, 0);
                datePicker.show(getActivity().getFragmentManager(), "datePicker");
                break;
            case R.id.fab:
                eventEditor = EventEditorFragment.newInstance(new Event(TimestampUtil.getCurrentTimestamp()));
                eventEditor.show(getActivity().getFragmentManager(), "eventEditor");
                break;
            default:
        }
    }

    @Override
    public void onDateSet(int type, int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        Toast.makeText(getActivity(), "type = " + type + " year = " + year + " month = " + month + " day = " + day, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View view) {
        mainActivity.databaseHelper.insertEvent(TimestampUtil.getCurrentTimestamp());
        Toast.makeText(mainActivity, "Event Created", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(adapter);
        super.onStop();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Log.d("TEST", "TEST");
        return false;
    }
}
