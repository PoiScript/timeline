package com.poipoipo.timeline.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.data.Event;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.EventsViewHolder> {
    private List<Event> events;
    private Context context;
    private DialogFragmentDetail dialogFragment;

    public RecyclerViewAdapter(List<Event> events, Context context) {
        this.events = events;
        this.context = context;
    }

    static class EventsViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView title;
        TextView time;
        TextView location;

        public EventsViewHolder(final View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.event_card);
            title = (TextView) view.findViewById(R.id.event_title);
            time = (TextView) view.findViewById(R.id.event_time);
            location = (TextView) view.findViewById(R.id.event_location);
        }
    }

    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_event, parent, false);
        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EventsViewHolder holder, int position) {
        if (events.get(position).getCategory() == ""){
            holder.title.setText(events.get(position).getTitle());
        } else {
            holder.title.setText(events.get(position).getCategory() + ": " + events.get(position).getTitle());
        }
        holder.time.setText(events.get(position).getStart() + "");
        holder.location.setText(events.get(position).getLocation());
//        holder.category.setText("Category" + ": ");
//        holder.title.setText("Title");
//        holder.location.setText("Location");
//        holder.time.setText("23:33-23:33");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((Activity) context).getFragmentManager();
                dialogFragment = DialogFragmentDetail.newInstance(events.get(holder.getAdapterPosition()));
                dialogFragment.show(manager, "dialog");
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
