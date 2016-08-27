package com.poipoipo.timeline.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.data.Event;
import com.poipoipo.timeline.messageEvent.EditedMessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EventCardAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "EventCardAdapter";
    private static final int EVENT_LINK = 1;
    private static final int EVENT_CARD = 2;
    private final SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private final List<Event> events;
    private final Context context;
    private int mExpandedPosition;

    public EventCardAdapter(List<Event> events, Context context) {
        this.events = events;
        this.context = context;
        EventBus.getDefault().register(this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == EVENT_CARD)
            return new CardsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_event_card, parent, false));
        else
            return new LinksViewHolder(LayoutInflater.from(context).inflate(R.layout.item_event_link, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == EVENT_CARD) {
            final boolean isExpanded = position == mExpandedPosition;
            ((CardsViewHolder) holder).detail.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            final Event event = events.get((holder.getAdapterPosition() - 1) / 2);
            ((CardsViewHolder) holder).text.setText("Title Here");
            ((CardsViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mExpandedPosition = isExpanded ? -1 : position;
//                    TransitionManager.beginDelayedTransition();
                    notifyDataSetChanged();
//                    EventEditorFragment fragment = EventEditorFragment.newInstance(event, holder.getAdapterPosition());
//                    fragment.show(((MainActivity) context).getFragmentManager(), "dialog");
                }
            });
        } else {
            final Event event = events.get((holder.getAdapterPosition()) / 2);
            ((LinksViewHolder) holder).textView.setText(format.format(event.getStart() * 1000L));
        }
    }

    @Subscribe
    public void onEventEdited(EditedMessageEvent msg) {
        Event event = events.get(msg.position);
        events.set(msg.position, event.editByChangeLog(msg.changeLog));
        notifyItemChanged(msg.position);
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) return EVENT_LINK;
        else return EVENT_CARD;
    }

    @Override
    public int getItemCount() {
        return events.size() * 2;
    }

    static class CardsViewHolder extends RecyclerView.ViewHolder {
        final CardView cardView;
        final TextView text;
        final RelativeLayout detail;

        public CardsViewHolder(final View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.event_card);
            text = (TextView) view.findViewById(R.id.event_text);
            detail = (RelativeLayout) view.findViewById(R.id.event_detail);
        }
    }

    static class LinksViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;

        public LinksViewHolder(final View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.event_link_date);
        }
    }
}
