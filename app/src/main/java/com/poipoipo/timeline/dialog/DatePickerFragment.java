package com.poipoipo.timeline.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import com.poipoipo.timeline.data.DateMessageEvent;

import org.greenrobot.eventbus.EventBus;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "DatePickerFragment";
    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";
    private static final String TYPE = "type";
    private OnDateSetListener mListener;

    public static DatePickerFragment newInstance(int type, int year, int month, int day) {
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        args.putInt(YEAR, year);
        args.putInt(MONTH, month);
        args.putInt(DAY, day);
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
        return new DatePickerDialog(getActivity(), this, getArguments().getInt(YEAR),
                getArguments().getInt(MONTH), getArguments().getInt(DAY));
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        if (mListener != null) {
            mListener.onDateSet(getArguments().getInt(TYPE), i, i1, i2);
        }
        if (i != getArguments().getInt(YEAR) || i1 != getArguments().getInt(MONTH) || i2 != getArguments().getInt(DAY)) {
            EventBus.getDefault().post(new DateMessageEvent(getArguments().getInt(TYPE), i, i1, i2));
        }
    }

    public interface OnDateSetListener {
        void onDateSet(int type, int year, int month, int day);
    }
}
