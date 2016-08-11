package com.poipoipo.timeline.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.adapter.TimelineRecyclerAdapter;
import com.poipoipo.timeline.data.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentTimeline extends Fragment
        implements View.OnClickListener, View.OnLongClickListener, Toolbar.OnMenuItemClickListener {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("M d");

    FloatingActionButton fab;
    Toolbar toolbar;
    Button button;
    int timestamp;
    List<Event> events = new ArrayList<>();
    Handler handler;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        timestamp = ((MainActivity) getActivity()).getTodayTimestamp();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp * 1000L);
        toolbar = (Toolbar) view.findViewById(R.id.timeline_toolbar);
        toolbar.inflateMenu(R.menu.menu_timeline);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        handler = ((MainActivity) getActivity()).handler;
        toolbar.setNavigationOnClickListener(this);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setTitle(dateFormat.format(calendar.getTime()));
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        TimelineRecyclerAdapter adapter = new TimelineRecyclerAdapter(initData(), getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        fab.setOnLongClickListener(this);
        button = (Button) toolbar.findViewById(R.id.edit_date);
        button.setOnClickListener(this);
    }

    public List<Event> initData() {
        List<Event> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Event(233));
            list.add(new Event(233));
            list.add(new Event(233));
            list.add(new Event(233));
        }
        return list;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edit_date:
                Log.d("TEST", "TEST");
                break;
            case R.id.fab:
                handler.obtainMessage(MainActivity.MESSAGE_DIALOG_CREATE).sendToTarget();
                break;
            default:
                handler.obtainMessage(MainActivity.MESSAGE_DRAWER).sendToTarget();
        }
    }

    @Override
    public boolean onLongClick(View view) {
        handler.obtainMessage(MainActivity.MESSAGE_QUICK_CREATE).sendToTarget();
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_date:
                Log.d("TEST", "TEST");
        }
        return false;
    }

    public void refresh() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timeline, null);
    }
}
