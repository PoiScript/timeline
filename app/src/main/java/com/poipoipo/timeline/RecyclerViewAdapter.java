package com.poipoipo.timeline;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.EventsViewHolder> {
    private List<Event> events;
    private Context context;

    public RecyclerViewAdapter(List<Event> events, Context context){
        this.events = events;
        this.context = context;
    }

    static class EventsViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView title;
        TextView time;
        ImageView icon;
        Button showMore;

        public EventsViewHolder(final View view) {
            super(view);
            cardView = (CardView) view.findViewById()
        }
    }
}
