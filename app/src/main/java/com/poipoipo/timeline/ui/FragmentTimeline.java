package com.poipoipo.timeline.ui;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.adapter.EventCardAdapter;
import com.poipoipo.timeline.data.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FragmentTimeline extends Fragment
        implements View.OnClickListener, View.OnLongClickListener, Toolbar.OnMenuItemClickListener {
    private SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
    private MainActivity mainActivity;
    List<Event> events = new ArrayList<>();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        int todayTimestamp = mainActivity.getTodayTimestamp();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.timeline_toolbar);
        toolbar.inflateMenu(R.menu.menu_timeline);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(this);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setTitle(null);
        Button editDate = (Button) view.findViewById(R.id.edit_date);
        editDate.setOnClickListener(this);
        editDate.setText(format.format(mainActivity.getTodayTimestamp() * 1000L));
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(layoutManager);
        EventCardAdapter adapter = new EventCardAdapter(mainActivity.databaseHelper.query(todayTimestamp, todayTimestamp + 24 * 60 * 60), mainActivity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
//        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
//        fab.setOnClickListener(this);
//        fab.setOnLongClickListener(this);
//        Button button = (Button) toolbar.findViewById(R.id.edit_date);
//        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_date:
                Log.d("TEST", "TEST");
                break;
            case R.id.fab:
                Intent intent = new Intent(mainActivity, EditActivity.class);
                intent.putExtra(Event.EVENT, new Event((mainActivity.getCurrentTimestamp())));
                startActivity(intent);
                break;
            default:
        }
    }

    @Override
    public boolean onLongClick(View view) {
        mainActivity.databaseHelper.insert(new Event(mainActivity.getCurrentTimestamp()));
        Toast.makeText(mainActivity, "Event Created", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Log.d("TEST", "TEST");
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timeline, container, false);
    }
}
