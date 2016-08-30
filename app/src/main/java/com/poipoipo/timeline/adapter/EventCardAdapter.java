package com.poipoipo.timeline.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.TextView;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.data.Event;
import com.poipoipo.timeline.database.DatabaseHelper;
import com.poipoipo.timeline.messageEvent.EditedMessageEvent;
import com.poipoipo.timeline.ui.MainActivity;

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
    private RecyclerView recyclerView;
    private DatabaseHelper helper;

    public EventCardAdapter(List<Event> events, Context context, RecyclerView recyclerView) {
        this.events = events;
        this.context = context;
        this.recyclerView = recyclerView;
        this.helper = ((MainActivity) context).databaseHelper;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == EVENT_CARD) {
            final boolean isExpanded = position == mExpandedPosition;
            final Event event = events.get((holder.getAdapterPosition() - 1) / 2);
            final CardsViewHolder viewHolder = (CardsViewHolder) holder;
            viewHolder.category.setText("title");
            viewHolder.duration.setText("23 hours 33 mins");
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mExpandedPosition = isExpanded ? -1 : viewHolder.getAdapterPosition();
                    TransitionManager.beginDelayedTransition(recyclerView);
                    notifyDataSetChanged();
                }
            });
            if (isExpanded) {
                if (viewHolder.detail.getParent() != null) {
                    viewHolder.detail.inflate();
                    viewHolder.duration.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand_more_black_24dp, 0);
//                    viewHolder.editor.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            EventEditorFragment fragment = EventEditorFragment.newInstance(event, viewHolder.getAdapterPosition());
//                            fragment.show(((MainActivity) context).getFragmentManager(), "dialog");
//                        }
//                    });
                } else {
                    viewHolder.detail.setVisibility(View.VISIBLE);
                }
            } else {
                viewHolder.detail.setVisibility(View.GONE);
                viewHolder.duration.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand_less_black_24dp, 0);
            }
//            ((CardsViewHolder) holder).detail.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        } else {
            final Event event = events.get((holder.getAdapterPosition()) / 2);
            ((LinksViewHolder) holder).textView.setText(format.format(event.getStart() * 1000L));
        }
    }

    @Subscribe
    public void onEventEdited(EditedMessageEvent msg) {
        Event event = events.get(msg.position);
        events.set(msg.position, event.update(msg.changeLog));
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
        final TextView category;
        final ViewStub detail;
        final TextView duration;
        final TextView content;
        final TextView date;
        final TextView location;
        final ImageButton editor;

        public CardsViewHolder(final View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.event_card);
            category = (TextView) view.findViewById(R.id.event_category);
            duration = (TextView) view.findViewById(R.id.event_duration);

            detail = (ViewStub) view.findViewById(R.id.event_detail_view_stub);
            content = (TextView) view.findViewById(R.id.event_content);
            date = (TextView) view.findViewById(R.id.event_date);
            location = (TextView) view.findViewById(R.id.event_location);
            editor = (ImageButton) view.findViewById(R.id.event_editor);
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
