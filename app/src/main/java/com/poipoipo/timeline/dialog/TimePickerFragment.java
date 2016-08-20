package com.poipoipo.timeline.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.poipoipo.timeline.TimeMessageEvent;

import org.greenrobot.eventbus.EventBus;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    private static final String HOUR = "hour";
    private static final String MIN = "min";
    private static final String TYPE = "type";

    public static TimePickerFragment newInstance(int type, int hour, int minute) {
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        args.putInt(HOUR, hour);
        args.putInt(MIN, minute);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), this, getArguments().getInt(HOUR),
                getArguments().getInt(MIN), DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        if (getArguments() != null) {
            EventBus.getDefault().post(new TimeMessageEvent(getArguments().getInt(TYPE), i, i1));
        }
    }
}
