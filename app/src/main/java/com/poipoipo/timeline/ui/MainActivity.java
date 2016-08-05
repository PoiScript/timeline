package com.poipoipo.timeline.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;

import com.poipoipo.timeline.R;
import com.poipoipo.timeline.data.Event;
import com.poipoipo.timeline.data.Label;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    public static final int MESSAGE_DIALOG = 1;
    Fragment fragment;
    DetailDialogFragment dialogFragment;
    FragmentManager manager;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragment = new FragmentTimeline();
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_frame, fragment).commit();


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

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

    @SuppressWarnings("StatementWithEmptyBody")
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
        manager.beginTransaction().replace(R.id.content_frame, fragment).commit();
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
}