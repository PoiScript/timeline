package com.poipoipo.timeline.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.data.Event;

import java.util.ArrayList;
import java.util.List;

public class FragmentTimeline extends Fragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(initData(), getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    public List<Event> initData(){
        List<Event> list = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            list.add(new Event(233));
            list.add(new Event(233));
            list.add(new Event(233));
            list.add(new Event(233));
        }
        return list;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timeline, null);
    }
}
