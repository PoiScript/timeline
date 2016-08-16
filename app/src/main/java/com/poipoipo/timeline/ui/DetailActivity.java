package com.poipoipo.timeline.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.adapter.ShowLabelAdapter;
import com.poipoipo.timeline.data.Event;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        event = (Event) getIntent().getSerializableExtra(Event.EVENT);
        if (event.hasTitle && event.hasSubtitle) {
            toolbar.setTitle(event.getTitle() + ": " + event.hasSubtitle);
        } else if (event.hasTitle) {
            toolbar.setTitle(event.getTitle());
        } else if (event.hasSubtitle) {
            toolbar.setTitle(event.getSubtitle());
        } else {
            toolbar.setTitle(R.string.dialog_no_title);
        }
        LinearLayoutManager manager = new LinearLayoutManager(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.dialog_recycler);
        RecyclerView.Adapter adapter = new ShowLabelAdapter(event.getAvailableLabelList(), this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        Button cancel = (Button) findViewById(R.id.dialog_cancel);
        cancel.setOnClickListener(this);
        Button edit = (Button) findViewById(R.id.dialog_edit);
        edit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.dialog_cancel) {
            finish();
        } else {
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra(Event.EVENT, event);
            startActivity(intent);
        }
    }
}
