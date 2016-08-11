package com.poipoipo.timeline.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.data.Event;

public class FragmentEdit extends Fragment {
    private String mParam2;
    private Event event;

    public static FragmentEdit newInstance(int start) {
        FragmentEdit fragment = new FragmentEdit();
        Bundle args = new Bundle();
        args.putInt(Event.START, start);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        event = ((MainActivity) getActivity()).databaseHelper.query(getArguments().getInt(Event.START));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

}
