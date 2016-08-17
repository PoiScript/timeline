package com.poipoipo.timeline.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    private static final String HOUR = "hour";
    private static final String MIN = "min";
    Calendar calendar = Calendar.getInstance();

    public static TimePickerFragment newInstance(Calendar calendar) {
        Bundle args = new Bundle();
        args.putInt(HOUR, calendar.get(Calendar.HOUR));
        args.putInt(MIN, calendar.get(Calendar.MINUTE));
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (calendar == null){
            calendar.set(Calendar.HOUR, getArguments().getInt(HOUR));
            calendar.set(Calendar.MINUTE, getArguments().getInt(MIN));
        }
        return new TimePickerDialog(getActivity(), this, calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        calendar.set(Calendar.HOUR, i);
        calendar.set(Calendar.MINUTE, i1);
    }

}
