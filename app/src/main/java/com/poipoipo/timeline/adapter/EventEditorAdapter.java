package com.poipoipo.timeline.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.data.Event;
import com.poipoipo.timeline.data.Label;
import com.poipoipo.timeline.data.TimestampUtil;
import com.poipoipo.timeline.database.DatabaseHelper;
import com.poipoipo.timeline.dialog.DatePickerFragment;
import com.poipoipo.timeline.dialog.TimePickerFragment;
import com.poipoipo.timeline.messageEvent.DateMessageEvent;
import com.poipoipo.timeline.messageEvent.TimeMessageEvent;
import com.poipoipo.timeline.ui.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EventEditorAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener, Serializable {
    private static final String TAG = "EventEditorAdapter";
    private static final int TYPE_NOR = 1, TYPE_HEADER = 0, TYPE_FOOTER = 2;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy", Locale.getDefault());
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private final OnEventChangedListener mListener;
    private final Context context;
    private final FragmentManager manager;
    private final DatabaseHelper databaseHelper;
    private ArrayMap<Integer, Integer> map;
    private Calendar startCalendar = Calendar.getInstance();
    private Calendar endCalendar = Calendar.getInstance();
    private HeaderViewHolder headerViewHolder;
    private boolean hadError;
    private SpinnerAdapter adapter;

    public EventEditorAdapter(Event event_old, ArrayMap<Integer, Integer> event, Context context, OnEventChangedListener mListener) {
        this.map = event;
        startCalendar.setTimeInMillis(event.get(DatabaseHelper.EVENT_START) * 1000L);
        if (event_old.getEnd() != 0)
            endCalendar.setTimeInMillis(event.get(DatabaseHelper.EVENT_END) * 1000L);
        else
            endCalendar = startCalendar;
        this.context = context;
        manager = ((MainActivity) context).getFragmentManager();
        this.mListener = mListener;
        databaseHelper = ((MainActivity) context).databaseHelper;
        EventBus.getDefault().register(this);
        setAdapter();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NOR) {
            final LabelViewHolder viewHolder = (LabelViewHolder) holder;
            final int key = map.keyAt(position - 1);
            final int value = map.valueAt(position - 1);
            final Label label = databaseHelper.map.get(key);
            viewHolder.icon.setAdapter(adapter);
            viewHolder.icon.setSelection(label.position);
            viewHolder.icon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (++i == viewHolder.icon.getAdapter().getCount()) {
                        map.remove(key);
                        notifyItemRemoved(viewHolder.getAdapterPosition());
                    } else if (i != key) {
                        mListener.onEventChanged(i, 1);
                        map.put(i, 1);
                        map.remove(key);
                        setAdapter();
                        notifyDataSetChanged();
                        Log.d(TAG, "onItemSelected: i = " + i + "count" + viewHolder.icon.getAdapter().getCount());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            viewHolder.text.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, new ArrayList<>(label.name.values())));
            if (value != 0) {
                viewHolder.text.setSelection(label.index.get(value));
            }
            viewHolder.text.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (++i != value) {
                        mListener.onEventChanged(key, i);
                        map.put(key, i);
                        notifyItemChanged(viewHolder.getAdapterPosition());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        } else if (getItemViewType(position) == TYPE_HEADER) {
            headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.startDate.setOnClickListener(this);
            headerViewHolder.startTime.setOnClickListener(this);
            headerViewHolder.endDate.setOnClickListener(this);
            headerViewHolder.endTime.setOnClickListener(this);
            headerViewHolder.startCurrent.setOnClickListener(this);
            headerViewHolder.endCurrent.setOnClickListener(this);
            setTime(true, true, true);
            setTime(false, true, true);
        } else {
            ((FooterViewHolder) holder).button.setOnClickListener(this);
        }
    }

    private void setAdapter() {
        List<String> text = new ArrayList<>();
        List<Integer> icon = new ArrayList<>();
        for (int i = 0; i < databaseHelper.map.size(); i++) {
            text.add(databaseHelper.map.valueAt(i).value);
            icon.add(databaseHelper.map.valueAt(i).icon);
        }
        text.add("Remove");
        icon.add(R.drawable.ic_remove);
        adapter = new SpinnerAdapter(context, text, icon);
    }

    private void setTime(boolean start, boolean date, boolean time) {
        if (start) {
            if (date) {
                headerViewHolder.startDate.setText(dateFormat.format(startCalendar.getTime()));
            }
            if (time) {
                headerViewHolder.startTime.setText(timeFormat.format(startCalendar.getTime()));
            }
        } else {
            if (date) {
                headerViewHolder.endDate.setText(dateFormat.format(endCalendar.getTime()));
            }
            if (time) {
                headerViewHolder.endTime.setText(timeFormat.format(endCalendar.getTime()));
            }
        }
    }

    private void setFoundError(boolean foundError) {
        if (hadError && !foundError) {
            headerViewHolder.endDate.setTextColor(Color.BLACK);
            headerViewHolder.endTime.setTextColor(Color.BLACK);
            hadError = false;
        }
        if (!hadError && foundError) {
            headerViewHolder.endDate.setTextColor(Color.RED);
            headerViewHolder.endTime.setTextColor(Color.RED);
            hadError = true;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.event_editor_start_date:
                DatePickerFragment.newInstance(Event.START, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH)).show(manager, "DatePicker");
                break;
            case R.id.event_editor_start_time:
                TimePickerFragment.newInstance(Event.START, startCalendar.get(Calendar.HOUR), startCalendar.get(Calendar.MINUTE)).show(manager, "TimePicker");
                break;
            case R.id.event_editor_end_date:
                DatePickerFragment.newInstance(Event.END, endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH)).show(manager, "DatePicker");
                break;
            case R.id.event_editor_end_time:
                TimePickerFragment.newInstance(Event.END, endCalendar.get(Calendar.HOUR), endCalendar.get(Calendar.MINUTE)).show(manager, "TimePicker");
                break;
            case R.id.event_editor_start_current:
                if (endCalendar.before(Calendar.getInstance())) {
                    setFoundError(true);
                    mListener.onEventChanged(Event.ERROR_TIME, TimestampUtil.getTimestampByCalendar(startCalendar));
                } else {
                    setFoundError(false);
                }
                startCalendar = Calendar.getInstance();
                mListener.onEventChanged(Event.START, TimestampUtil.getTimestampByCalendar(startCalendar));
                setTime(true, true, true);
                break;
            case R.id.event_editor_end_current:
                if (endCalendar.before(Calendar.getInstance())) {
                    setFoundError(true);
                    mListener.onEventChanged(Event.ERROR_TIME, TimestampUtil.getTimestampByCalendar(endCalendar));
                } else {
                    setFoundError(false);
                }
                endCalendar = Calendar.getInstance();
                mListener.onEventChanged(Event.END, TimestampUtil.getTimestampByCalendar(endCalendar));
                setTime(false, true, true);
                break;
            case R.id.event_editor_add_label:
                if (map.size() < databaseHelper.map.size()) {
                    for (int i = 0; i < databaseHelper.map.size(); i++) {
                        if (!map.containsKey(databaseHelper.map.keyAt(i))) {
                            map.put(databaseHelper.map.keyAt(i), 1);
                            notifyItemInserted(getItemCount());
                            mListener.onEventChanged(databaseHelper.map.keyAt(i), 1);
                            break;
                        }
                    }
                }
        }
    }

    @Subscribe
    public void onResetMessageEvent(Integer timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp * 1000L);
        if (calendar.after(startCalendar)) {
            endCalendar = calendar;
            setTime(false, true, true);
        } else {
            startCalendar = calendar;
            setTime(true, true, true);
        }
        setFoundError(false);
    }

    @Subscribe
    public void onTimeMessageEvent(TimeMessageEvent event) {
        switch (event.type) {
            case Event.START:
                Calendar temp = startCalendar;
                startCalendar.set(Calendar.HOUR, event.hour);
                startCalendar.set(Calendar.MINUTE, event.minute);
                if (startCalendar.after(endCalendar)) {
                    setFoundError(true);
                    mListener.onEventChanged(Event.ERROR_TIME, TimestampUtil.getTimestampByCalendar(temp));
                } else {
                    setFoundError(false);
                }
                mListener.onEventChanged(Event.START, TimestampUtil.getTimestampByCalendar(startCalendar));
                setTime(true, false, true);
                break;
            case Event.END:
                Calendar anotherTemp = endCalendar;
                endCalendar.set(Calendar.HOUR, event.hour);
                endCalendar.set(Calendar.MINUTE, event.minute);
                if (startCalendar.after(endCalendar)) {
                    setFoundError(true);
                    mListener.onEventChanged(Event.ERROR_TIME, TimestampUtil.getTimestampByCalendar(anotherTemp));
                } else {
                    setFoundError(false);
                }
                mListener.onEventChanged(Event.END, TimestampUtil.getTimestampByCalendar(endCalendar));
                setTime(false, false, true);
        }
    }

    @Subscribe
    public void onDateMessageEvent(DateMessageEvent event) {
        switch (event.type) {
            case Event.START:
                Calendar temp = startCalendar;
                startCalendar.set(Calendar.YEAR, event.year);
                startCalendar.set(Calendar.MONTH, event.month);
                startCalendar.set(Calendar.DAY_OF_MONTH, event.day);
                if (startCalendar.after(endCalendar)) {
                    setFoundError(true);
                    mListener.onEventChanged(Event.ERROR_TIME, TimestampUtil.getTimestampByCalendar(temp));
                } else {
                    setFoundError(false);
                }
                mListener.onEventChanged(Event.START, TimestampUtil.getTimestampByCalendar(startCalendar));
                setTime(true, true, false);
                break;
            case Event.END:
                Calendar anotherTemp = endCalendar;
                endCalendar.set(Calendar.YEAR, event.year);
                endCalendar.set(Calendar.MONTH, event.month);
                endCalendar.set(Calendar.DAY_OF_MONTH, event.day);
                if (startCalendar.after(endCalendar)) {
                    setFoundError(true);
                    mListener.onEventChanged(Event.ERROR_TIME, TimestampUtil.getTimestampByCalendar(anotherTemp));
                } else {
                    setFoundError(false);
                }
                mListener.onEventChanged(Event.END, TimestampUtil.getTimestampByCalendar(endCalendar));
                setTime(false, true, false);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == map.size() + 1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NOR;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NOR) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_label_edit, parent, false);
            return new LabelViewHolder(view);
        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_footer, parent, false);
            return new FooterViewHolder(view);
        }
    }

    @Override
    public int getItemCount() {
        return map.size() + 2;
    }

    public interface OnEventChangedListener {
        void onEventChanged(int key, int value);

        void onKeyRemoved(int key);
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        final Button startDate;
        final Button startTime;
        final Button endDate;
        final Button endTime;
        final ImageButton startCurrent;
        final ImageButton endCurrent;

        public HeaderViewHolder(final View view) {
            super(view);
            startDate = (Button) view.findViewById(R.id.event_editor_start_date);
            startTime = (Button) view.findViewById(R.id.event_editor_start_time);
            endDate = (Button) view.findViewById(R.id.event_editor_end_date);
            endTime = (Button) view.findViewById(R.id.event_editor_end_time);
            startCurrent = (ImageButton) view.findViewById(R.id.event_editor_start_current);
            endCurrent = (ImageButton) view.findViewById(R.id.event_editor_end_current);
        }
    }

    static class LabelViewHolder extends RecyclerView.ViewHolder {
        final Spinner icon;
        final Spinner text;

        public LabelViewHolder(final View view) {
            super(view);
            icon = (Spinner) view.findViewById(R.id.edit_label_icon);
            text = (Spinner) view.findViewById(R.id.edit_label_text);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        final Button button;

        public FooterViewHolder(final View view) {
            super(view);
            button = (Button) view.findViewById(R.id.event_editor_add_label);
        }
    }
}
