package com.poipoipo.timeline.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.poipoipo.timeline.R;
import com.poipoipo.timeline.data.Event;
import com.poipoipo.timeline.data.Label;
import com.poipoipo.timeline.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    private DrawerLayout drawerLayout;
    public static final int MESSAGE_CREATE_EVENT = 1, MESSAGE_QUICK_CREATE = 2, MESSAGE_DETAIL_DIALOG = 3, MESSAGE_REFRESH = 4;
    Fragment fragment;
    DialogFragmentDetail dialogFragment;
    FragmentManager manager;
    ActionBar actionBar;
    public DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        databaseHelper = new DatabaseHelper(this);

        /*Stetho Debug*/
        Stetho.initializeWithDefaults(this);
        Log.d(TAG, "onCreate: Stetho Running");

        fragment = new FragmentTimeline();
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_frame, fragment).commit();


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                handler.obtainMessage(MainActivity.MESSAGE_CREATE_EVENT).sendToTarget();
//            }
//        });
//        fab.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                handler.obtainMessage(MainActivity.MESSAGE_QUICK_CREATE).sendToTarget();
//                return true;
//            }
//        });
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawerLayout.setDrawerListener(toggle);
//        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(getApplicationContext(), R.string.not_done, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        actionBarModify(item.getItemId());
//        fragment = new FragmentLabels();
        Bundle bundle = new Bundle();
        switch (item.getItemId()) {
            case R.id.nav_timeline:
                fragment = new FragmentTimeline();
                break;
            case R.id.nav_dashboard:
                fragment = new FragmentDashboard();
                break;
            case R.id.nav_category:
                fragment = new FragmentLabels();
                bundle.putInt(FragmentLabels.LABEL_TYPE, Label.CATEGORY);
                fragment.setArguments(bundle);
                break;
            case R.id.nav_title:
                fragment = new FragmentLabels();
                bundle.putInt(FragmentLabels.LABEL_TYPE, Label.LOCATION);
                fragment.setArguments(bundle);
                break;
            case R.id.nav_location:
                fragment = new FragmentLabels();
                bundle.putInt(FragmentLabels.LABEL_TYPE, Label.TITLE);
                fragment.setArguments(bundle);
                break;
//            case R.id.nav_archive:
//            case R.id.nav_settings:
//            case R.id.nav_about:
//                Toast.makeText(getApplicationContext(), R.string.not_done, Toast.LENGTH_SHORT).show();
        }
//        manager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void actionBarModify(int navName) {
        actionBar = getSupportActionBar();
        switch (navName) {
            case R.id.nav_timeline:
                actionBar.setTitle(R.string.drawer_timeline);
                break;
            case R.id.nav_dashboard:
                actionBar.setTitle(R.string.drawer_dashboard);
                break;
            case R.id.nav_category:
                actionBar.setTitle(R.string.drawer_category);
                break;
            case R.id.nav_title:
                actionBar.setTitle(R.string.drawer_title);
                break;
            case R.id.nav_location:
                actionBar.setTitle(R.string.drawer_location);
                break;
        }
    }

    public void doPositiveClick() {
        Log.d("DEBUGGING", "User clicks on OK");
    }

    public final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_QUICK_CREATE:
                    Long time = System.currentTimeMillis() / 1000;
                    Integer timeStamp = time.intValue();
                    databaseHelper.insert(new Event(timeStamp));
                    Toast.makeText(getApplicationContext(), "Event Created", Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_REFRESH:
//                    ((FragmentTimeline) getSupportFragmentManager().findFragmentById(R.id.content_frame)).refreshDone();
                    break;
                case MESSAGE_DETAIL_DIALOG:
                    dialogFragment = DialogFragmentDetail.newInstance(databaseHelper.query(msg.arg1));
                    dialogFragment.show(getFragmentManager(), "dialog");
            }
        }
    };

}