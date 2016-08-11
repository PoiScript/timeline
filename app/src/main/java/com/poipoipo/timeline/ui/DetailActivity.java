package com.poipoipo.timeline.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.adapter.DetailRecyclerAdapter;
import com.poipoipo.timeline.data.Label;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.dialog_recycler);
        RecyclerView.Adapter adapter = new DetailRecyclerAdapter(initData(), this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Label> initData() {
        List<Label> datas = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            datas.add(new Label());
        }
        return datas;
    }
}
