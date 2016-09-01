package com.poipoipo.timeline.ui;

import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.ArrayMap;
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
import java.util.List;
import java.util.Locale;

public class FragmentTimeline extends Fragment
        implements View.OnClickListener,
        View.OnLongClickListener,
        Toolbar.OnMenuItemClickListener,
        DatePickerFragment.OnDateSetListener {
    private static final String TAG = "FragmentTimeline";
    private final SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
    private MainActivity mainActivity;
    private Button editDate;
    private EventCardAdapter adapter;
    private List<ArrayMap<Integer, Integer>> events;
    private Calendar shownDate = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timeline, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.timeline_toolbar);
        toolbar.inflateMenu(R.menu.menu_timeline);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(this);
        toolbar.setOnMenuItemClickListener(this);
        editDate = (Button) view.findViewById(R.id.toolbar_date);
        editDate.setOnClickListener(this);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(layoutManager);
        initAdapterAndButton();
        adapter = new EventCardAdapter(events, mainActivity, recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        fab.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_date:
                DatePickerFragment datePicker = DatePickerFragment.newInstance(0, shownDate.get(Calendar.YEAR), shownDate.get(Calendar.MONTH), shownDate.get(Calendar.DAY_OF_MONTH));
                datePicker.setTargetFragment(this, 0);
                datePicker.show(getActivity().getFragmentManager(), "datePicker");
                break;
            case R.id.fab:
                EventEditorFragment eventEditor = EventEditorFragment.newInstance(new Event(TimestampUtil.getCurrentTimestamp(Calendar.getInstance())), 0);
                eventEditor.show(getActivity().getFragmentManager(), "eventEditor");
                break;
            default:
                break;
        }
    }

    private void initAdapterAndButton() {
        editDate.setText(format.format(shownDate.getTime()));
        events = mainActivity.databaseHelper.queryEvent(TimestampUtil.getDayTimestampByCalendar(Calendar.getInstance()));
    }

    @Override
    public boolean onLongClick(View view) {
        Calendar calendar = Calendar.getInstance();
        mainActivity.databaseHelper.insertEvent(TimestampUtil.getCurrentTimestamp(calendar));
        if (shownDate.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
                && shownDate.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
                && shownDate.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)) {
            events.add(mainActivity.databaseHelper.queryLastEvent());
            adapter.notifyItemInserted(adapter.getItemCount());
        }
        Toast.makeText(mainActivity, "Event Created", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onDateSet(int type, int year, int month, int day) {
        shownDate.set(Calendar.YEAR, year);
        shownDate.set(Calendar.MONTH, month);
        shownDate.set(Calendar.DAY_OF_MONTH, day);
        initAdapterAndButton();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(adapter);
        super.onStop();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Notification.Builder builder = new Notification.Builder(getActivity())
                .setSmallIcon(R.drawable.ic_content)
                .setContentTitle("Title Here")
                .setContentText("Content Here");
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
        return false;
    }
}
