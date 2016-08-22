package com.poipoipo.timeline.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.data.Label;

public class FragmentLabels extends Fragment {

    private static final String LABEL_TYPE = "type";
    private int type;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        switch (type) {
            case Label.TITLE:
                break;
            case Label.LOCATION:
                break;
            case Label.SUBTITLE:
        }
    }

    @Override
    public void setArguments(Bundle args) {
        type = args.getInt(LABEL_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_label, container, false);
    }
}