package com.poipoipo.timeline.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.poipoipo.timeline.data.TimestampUtil;
import com.poipoipo.timeline.database.DatabaseHelper;
import com.poipoipo.timeline.dialog.DatePickerFragment;
import com.poipoipo.timeline.dialog.TimePickerFragment;
import com.poipoipo.timeline.ui.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    private static final int TYPE_NOR = 1, TYPE_HEADER = 0, TYPE_FOOTER = 2;
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
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NOR) {
            final LabelViewHolder viewHolder = (LabelViewHolder) holder;
            final int key = labelList.get(position - 1).getKey();
            final int value = labelList.get(position - 1).getValue();
            viewHolder.imageButton.setImageResource(databaseHelper.getLabelIcon(key));
            viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final PopupMenu popup = new PopupMenu(context, view);
                    int i = 0;
                    for (Map.Entry<Integer, String> entry : labelNameMap.entrySet()) {
                        popup.getMenu().add(1, entry.getKey(), i++, entry.getValue());
                    }
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            mListener.onEventChange(233, 233);
                            setSpinner(viewHolder, menuItem.getItemId(), 0);
                            viewHolder.imageButton.setImageResource(databaseHelper.getLabelIcon(menuItem.getItemId()));
                            return false;
                        }
                    });
                    popup.show();
                }
            });
            if (position <= 1 && key != 999) {
                setSpinner(viewHolder, key, value);
            }
        } else if (getItemViewType(position) == TYPE_HEADER) {
            HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
            viewHolder.startDate.setText(dateFormat.format(startCalendar.getTime()));
            viewHolder.startTime.setText(timeFormat.format(startCalendar.getTime()));
            viewHolder.endDate.setText(dateFormat.format(endCalendar.getTime()));
            viewHolder.endTime.setText(timeFormat.format(endCalendar.getTime()));
            viewHolder.startDate.setOnClickListener(this);
            viewHolder.startTime.setOnClickListener(this);
            viewHolder.endDate.setOnClickListener(this);
            viewHolder.endTime.setOnClickListener(this);
        } else {
            ((FooterViewHolder) holder).button.setOnClickListener(this);
        }
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
                DatePickerFragment fragment1 = DatePickerFragment.newInstance(Event.START, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH));
                fragment1.show(manager, "DatePicker");
                break;
            case R.id.event_editor_start_time:
                TimePickerFragment fragment2 = TimePickerFragment.newInstance(Event.START, startCalendar.get(Calendar.HOUR), startCalendar.get(Calendar.MINUTE));
                fragment2.show(manager, "TimePicker");
                break;
            case R.id.event_editor_end_date:
                DatePickerFragment fragment3 = DatePickerFragment.newInstance(Event.END, endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH));
                fragment3.show(manager, "DatePicker");
                break;
            case R.id.event_editor_end_time:
                TimePickerFragment fragment4 = TimePickerFragment.newInstance(Event.END, endCalendar.get(Calendar.HOUR), endCalendar.get(Calendar.MINUTE));
                fragment4.show(manager, "TimePicker");
                break;
            case R.id.event_editor_add_label:
                labelList.add(new MyEntry<>(999, 0));
                notifyItemInserted(labelList.size());
        }
    }

    @Subscribe
    public void onTimeMessageEvent(TimeMessageEvent event) {
        switch (event.type) {
            case Event.START:
                startCalendar.set(Calendar.HOUR, event.hour);
                startCalendar.set(Calendar.MINUTE, event.minute);
                mListener.onEventChange(Event.START, TimestampUtil.getTimestampByCalendar(startCalendar));
                break;
            case Event.END:
                endCalendar.set(Calendar.HOUR, event.hour);
                endCalendar.set(Calendar.MINUTE, event.minute);
                mListener.onEventChange(Event.END, TimestampUtil.getTimestampByCalendar(endCalendar));
        }
        Log.d(TAG, "onTimeMessageEvent: type = " + event.type + " hour = " + event.hour + " minute = " + event.minute);
    }

    @Subscribe
    public void onDateMessageEvent(DateMessageEvent event) {
        switch (event.type) {
            case Event.START:
                startCalendar.set(Calendar.YEAR, event.year);
                startCalendar.set(Calendar.MONTH, event.month);
                startCalendar.set(Calendar.DAY_OF_MONTH, event.day);
                mListener.onEventChange(Event.START, TimestampUtil.getTimestampByCalendar(startCalendar));
                break;
            case Event.END:
                endCalendar.set(Calendar.YEAR, event.year);
                endCalendar.set(Calendar.MONTH, event.month);
                endCalendar.set(Calendar.DAY_OF_MONTH, event.day);
                mListener.onEventChange(Event.END, TimestampUtil.getTimestampByCalendar(endCalendar));
        }
        Log.d(TAG, "onDateMessageEvent: type = " + event.type + " year = " + event.year + " month = " + event.month + " day = " + event.day);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == labelList.size() + 1) {
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
        return labelList.size() + 2;
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

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        Button button;

        public FooterViewHolder(final View view) {
            super(view);
            button = (Button) view.findViewById(R.id.event_editor_add_label);
        }
    }
}
