package com.poipoipo.timeline.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.poipoipo.timeline.R;
import com.poipoipo.timeline.data.TimestampUtil;
import com.poipoipo.timeline.database.DatabaseHelper;
import com.poipoipo.timeline.dialog.EventEditorFragment;

import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, EventEditorFragment.EventEditorListener {
    public static final int MESSAGE_DRAWER = 0, MESSAGE_CREATE = 1, MESSAGE_QUICK_CREATE = 2, MESSAGE_DIALOG_DETAIL = 3;
    private static final String TAG = "MainActivity";
    public DatabaseHelper databaseHelper;
    FragmentManager manager;
    private DrawerLayout drawerLayout;
    public final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_DRAWER:
                    drawerLayout.openDrawer(GravityCompat.START);
                    break;
                case MESSAGE_CREATE:
                    break;
                case MESSAGE_QUICK_CREATE:
                    databaseHelper.insertEvent(TimestampUtil.getCurrentTimestamp());
                    Toast.makeText(getApplicationContext(), "Event Created", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);

        /*Stetho Debug*/
        Stetho.initializeWithDefaults(this);
        Log.d(TAG, "onCreate: Stetho Running");
        fragment = new FragmentTimeline();
        manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

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
    public boolean onNavigationItemSelected(MenuItem item) {
        Bundle args = new Bundle();
        switch (item.getItemId()) {
            case R.id.nav_timeline:
                fragment = new FragmentTimeline();
                break;
        }
        manager.beginTransaction().add(R.id.content_frame, fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onPositiveClick(int start, Map<Integer, Integer> changeLog) {
        databaseHelper.update(start, changeLog);
        Toast.makeText(this, "Change Saved", Toast.LENGTH_SHORT).show();
    }
}