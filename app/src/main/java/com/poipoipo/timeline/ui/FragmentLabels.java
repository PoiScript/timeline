package com.poipoipo.timeline.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poipoipo.timeline.R;

public class FragmentLabels extends Fragment {

    private int layout;
    public static final String FRAGMENT_TYPE = "type";
    public static final int TIMELINE = 0;
    public static final int DASHBOARD = 1;

    @Override
    public void setArguments(Bundle args) {
        switch (args.getInt(FRAGMENT_TYPE)){
            case TIMELINE:
                layout = R.layout.fragment_timeline;
                break;
            case DASHBOARD:
                layout = R.layout.fragment_dashboard;
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layout, null);
    }
}