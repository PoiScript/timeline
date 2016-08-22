package com.poipoipo.timeline.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.data.EditedMessageEvent;
import com.poipoipo.timeline.data.Event;
import com.poipoipo.timeline.dialog.EventEditorFragment;
import com.poipoipo.timeline.ui.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EventCardAdapter
        extends RecyclerView.Adapter<EventCardAdapter.EventsViewHolder> {
    private static final String TAG = "EventCardAdapter";
    private final SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private final List<Event> events;
    private final Context context;
    private EventEditorFragment fragment;

    public EventCardAdapter(List<Event> events, Context context) {
        this.events = events;
        this.context = context;
        EventBus.getDefault().register(this);
    }

    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        fragment = new EventEditorFragment();
        View view = LayoutInflater.from(context).inflate(R.layout.item_event_card, parent, false);
        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EventsViewHolder holder, int position) {
        final Event event = events.get(holder.getAdapterPosition());
        if (event.hasTitle && event.hasSubtitle) {
            holder.title.setText(new StringBuilder().append(event.getTitle()).append(": ").append(event.getSubtitle()));
        } else if (event.hasTitle) {
            holder.title.setText(event.getTitle());
        } else if (event.hasSubtitle) {
            holder.title.setText(event.getSubtitle());
        } else {
            holder.title.setText(R.string.dialog_no_title);
        }
        if (event.hasEndTime) {
            holder.time.setText(new StringBuilder(format.format(event.getStart() * 1000L)).append(" - ").append(format.format(event.getEnd() * 1000L)));
        } else {
            holder.time.setText(format.format(event.getStart() * 1000L));
        }
        holder.location.setText(event.getLocation());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.update(event, holder.getAdapterPosition());
                fragment.show(((MainActivity) context).getFragmentManager(), "dialog");
            }
        });
    }

    @Subscribe
    public void onEventEdited(EditedMessageEvent msg) {
        Log.d(TAG, "onEventEdited: ");
        events.set(msg.position, events.get(msg.position).editByChangeLog(msg.changeLog));
        notifyItemChanged(msg.position);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    static class EventsViewHolder extends RecyclerView.ViewHolder {
        final CardView cardView;
        final TextView title;
        final TextView time;
        final TextView location;

        public EventsViewHolder(final View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.event_card);
            title = (TextView) view.findViewById(R.id.event_title);
            time = (TextView) view.findViewById(R.id.event_time);
            location = (TextView) view.findViewById(R.id.event_location);
        }
    }
}
