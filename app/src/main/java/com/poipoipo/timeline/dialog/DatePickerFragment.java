package com.poipoipo.timeline.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import com.poipoipo.timeline.DateMessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";
    private static final String TYPE = "type";
    Calendar calendar = Calendar.getInstance();
    private OnDateSetListener mListener;

    public static DatePickerFragment newInstance(Calendar calendar, int type) {
        Bundle args = new Bundle();
        args.putInt(YEAR, calendar.get(Calendar.YEAR));
        args.putInt(MONTH, calendar.get(Calendar.MONTH));
        args.putInt(DAY, calendar.get(Calendar.DAY_OF_MONTH));
        args.putInt(TYPE, type);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mListener = (OnDateSetListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling Fragment must implement OnDateListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (calendar == null) {
            calendar.set(Calendar.YEAR, getArguments().getInt(YEAR));
            calendar.set(Calendar.MONTH, getArguments().getInt(MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, getArguments().getInt(DAY));
        }
        return new DatePickerDialog(getActivity(), this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        calendar.set(Calendar.YEAR, i);
        calendar.set(Calendar.MONTH, ++i1);
        calendar.set(Calendar.DAY_OF_MONTH, i2);
        if (mListener != null) {
            mListener.onDateSet(getArguments().getInt(TYPE), i, i1, i2);
        }
        EventBus.getDefault().post(new DateMessageEvent(getArguments().getInt(TYPE), i, i1, i2));
    }

    public interface OnDateSetListener {
        void onDateSet(int type, int year, int month, int day);
    }
}
