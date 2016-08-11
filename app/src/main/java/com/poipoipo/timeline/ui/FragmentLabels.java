package com.poipoipo.timeline.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.data.Label;

public class FragmentLabels extends Fragment {

    public static final String LABEL_TYPE = "type";
    private int type;

    private TextView textView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        textView = (TextView) view.findViewById(R.id.label_text);
        switch (type) {
            case Label.TITLE:
                textView.setText("SUBTITLE");
                break;
            case Label.LOCATION:
                textView.setText("LOCATION");
                break;
            case Label.SUBTITLE:
                textView.setText("SUBTITLE");
        }
    }

    @Override
    public void setArguments(Bundle args) {
        type = args.getInt(LABEL_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_label, null);
    }
}