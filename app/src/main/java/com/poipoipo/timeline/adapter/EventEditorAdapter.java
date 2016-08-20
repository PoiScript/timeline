package com.poipoipo.timeline.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.poipoipo.timeline.DateMessageEvent;
import com.poipoipo.timeline.R;
import com.poipoipo.timeline.TimeMessageEvent;
import com.poipoipo.timeline.data.Event;
import com.poipoipo.timeline.data.MyEntry;
import com.poipoipo.timeline.database.DatabaseHelper;
import com.poipoipo.timeline.dialog.DatePickerFragment;
import com.poipoipo.timeline.dialog.TimePickerFragment;
import com.poipoipo.timeline.ui.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EventEditorAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener {
    private static final String TAG = "EventEditorAdapter";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy", Locale.getDefault());
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private List<Map.Entry<Integer, Integer>> labelList = new ArrayList<>();
    private Map<Integer, String> labelNameMap = new HashMap<>();
    private OnEventChangedListener mListener;
    private Calendar startCalendar = Calendar.getInstance();
    private Calendar endCalendar = Calendar.getInstance();
    private Context context;
    private FragmentManager manager;
    private DatabaseHelper databaseHelper;

    public EventEditorAdapter(Event event, Context context, OnEventChangedListener mListener) {
        this.labelList = event.getLabelList();
        startCalendar.setTimeInMillis(event.getStart() * 1000L);
        endCalendar.setTimeInMillis(event.getEnd() * 1000L);
        this.context = context;
        manager = ((MainActivity) context).getFragmentManager();
        this.mListener = mListener;
        databaseHelper = ((MainActivity) context).databaseHelper;
        labelNameMap = databaseHelper.labelNameMap;
        EventBus.getDefault().register(this);
        labelList.add(new MyEntry<>(1, 0));
        notifyItemInserted(labelList.size());
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
        viewHolder.startDate.setText(dateFormat.format(startCalendar.getTime()));
        viewHolder.startTime.setText(timeFormat.format(startCalendar.getTime()));
        viewHolder.endDate.setText(dateFormat.format(endCalendar.getTime()));
        viewHolder.endTime.setText(timeFormat.format(endCalendar.getTime()));
        viewHolder.startDate.setOnClickListener(this);
        viewHolder.startTime.setOnClickListener(this);
        viewHolder.endDate.setOnClickListener(this);
        viewHolder.endTime.setOnClickListener(this);
//        final int key = labelList.get(position).getKey();
//        final int value = labelList.get(position).getValue();
//        holder.imageButton.setImageResource(databaseHelper.getLabelIcon(key));
//        holder.imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final PopupMenu popup = new PopupMenu(context, view);
//                int i = 0;
//                for (Map.Entry<Integer, String> entry : labelNameMap.entrySet()) {
//                    popup.getMenu().add(1, entry.getKey(), i++, entry.getValue());
//                }
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//                        mListener.onEventChange(233, 233);
//                        setSpinner(holder, menuItem.getItemId(), 0);
//                        holder.imageButton.setImageResource(databaseHelper.getLabelIcon(menuItem.getItemId()));
//                        return false;
//                    }
//                });
//                popup.show();
//            }
//        });
//        if (position <= 1 && key != 999) {
//            setSpinner(holder, key, value);
//        }
    }

    private void setSpinner(LabelViewHolder holder, final int label, int position) {
        ArrayAdapter adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_dropdown_item,
                new ArrayList<>(databaseHelper.labelMap.get(label).name.values()));
        holder.spinner.setAdapter(adapter);
//        holder.spinner.setSelection(databaseHelper.labelMap.get(label).index.get(position));
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mListener.onEventChange(label, ++i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.event_editor_start_date:
                DatePickerFragment fragment1 = DatePickerFragment.newInstance(startCalendar, Event.START);
                fragment1.show(manager, "DatePicker");
                break;
            case R.id.event_editor_start_time:
                TimePickerFragment fragment2 = TimePickerFragment.newInstance(startCalendar, Event.START);
                fragment2.show(manager, "TimePicker");
                break;
            case R.id.event_editor_end_date:
                DatePickerFragment fragment3 = DatePickerFragment.newInstance(endCalendar, Event.END);
                fragment3.show(manager, "DatePicker");
                break;
            case R.id.event_editor_end_time:
                TimePickerFragment fragment4 = TimePickerFragment.newInstance(endCalendar, Event.END);
                fragment4.show(manager, "TimePicker");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTimeMessageEvent(TimeMessageEvent event) {
        Log.d(TAG, "onTimeMessageEvent: type = " + event.type + " hour = " + event.hour + " minute = " + event.minute);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDateMessageEvent(DateMessageEvent event) {
        Log.d(TAG, "onDateMessageEvent: type = " + event.type + " year = " + event.year + " month = " + event.month + " day = " + event.day);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_label_edit, parent, false);
//        return new LabelViewHolder(view);
        View view = LayoutInflater.from(context).inflate(R.layout.item_header, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return labelList.size();
    }

    public interface OnEventChangedListener {
        void onEventChange(int key, int value);
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        Button startDate;
        Button startTime;
        Button endDate;
        Button endTime;

        public HeaderViewHolder(final View view) {
            super(view);
            startDate = (Button) view.findViewById(R.id.event_editor_start_date);
            startTime = (Button) view.findViewById(R.id.event_editor_start_time);
            endDate = (Button) view.findViewById(R.id.event_editor_end_date);
            endTime = (Button) view.findViewById(R.id.event_editor_end_time);
        }
    }

    static class LabelViewHolder extends RecyclerView.ViewHolder {
        ImageButton imageButton;
        Spinner spinner;

        public LabelViewHolder(final View view) {
            super(view);
            imageButton = (ImageButton) view.findViewById(R.id.edit_label_icon);
            spinner = (Spinner) view.findViewById(R.id.edit_label_button);
        }
    }
}
