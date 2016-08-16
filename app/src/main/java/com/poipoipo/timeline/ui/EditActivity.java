package com.poipoipo.timeline.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.adapter.EditLabelAdapter;
import com.poipoipo.timeline.data.Event;

public class EditActivity extends AppCompatActivity
        implements View.OnClickListener, Toolbar.OnMenuItemClickListener {
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_edit);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(this);
        event = (Event) getIntent().getSerializableExtra(Event.EVENT);
        Button title = (Button) findViewById(R.id.edit_title);
        if (event.hasTitle && event.hasSubtitle) {
            title.setText(event.getTitle() + ": " + event.hasSubtitle);
        } else if (event.hasTitle) {
            title.setText(event.getTitle());
        } else if (event.hasSubtitle) {
            title.setText(event.getSubtitle());
        } else {
            title.setText(R.string.dialog_no_title);
        }
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        appBarLayout.setBackgroundResource(R.color.blue);
        event = (Event) getIntent().getSerializableExtra(Event.EVENT);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_edit);
        RecyclerView.Adapter adapter = new EditLabelAdapter(event.getAllLabelList(), this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Log.d("test", "test");
        return false;
    }

    @Override
    public void onClick(View view) {
        Log.d("test", "test");
    }
}
