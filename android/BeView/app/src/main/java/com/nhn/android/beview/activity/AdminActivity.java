package com.nhn.android.beview.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.nhn.android.beview.R;
import com.nhn.android.beview.UserConstants;
import com.nhn.android.beview.fragment.ConferenceFragment;
import com.nhn.android.beview.fragment.HomeFragment;
import com.nhn.android.beview.fragment.RegisterConferenceFragment;
import com.nhn.android.beview.fragment.RegisterFragment;
import com.nhn.android.beview.fragment.ScheduleFragment;
import com.nhn.android.beview.fragment.SessionFragment;
import com.nhn.android.beview.fragment.SpeakerFragment;
import com.nhn.android.beview.fragment.TrackFragment;
import com.nhn.android.beview.fragment.dummy.DummyContent;
import com.nhn.android.beview.model.Conference;

import static android.R.attr.fragment;
import static android.R.transition.move;

public class AdminActivity extends AppCompatActivity implements ConferenceFragment.OnConferenceFragmentListener,
        TrackFragment.OnTrackFragmentListener, SessionFragment.OnSessionFragmentListener, SpeakerFragment.OnSpeakerFragmentListener,
        BottomNavigationView.OnNavigationItemSelectedListener, RegisterConferenceFragment.OnRegisterConferenceFragmentListener {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        moveToFragment(ConferenceFragment.newInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof RegisterFragment) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings : {
                moveToRegisterFragment();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void moveToRegisterFragment() {
        addToFragment(RegisterConferenceFragment.newInstance(null));
    }

    private void addToFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onConferenceFragmentInteraction(Conference conference) {

    }

    @Override
    public void onSpeakerFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onTrackFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onSessionFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = null;
        switch (id) {
            case R.id.nav_conference: {
                fragment = ConferenceFragment.newInstance();
                break;
            }
            case R.id.nav_track: {
                fragment = TrackFragment.newInstance();
                break;
            }
            case R.id.nav_session: {
                fragment = SessionFragment.newInstance();
                break;
            }
            case R.id.nav_speaker: {
                fragment = SpeakerFragment.newInstance();
                break;
            }
        }
        if (fragment != null) {
            moveToFragment(fragment);
        }
        return true;
    }

    private void moveToFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    @Override
    public void onClickDoneButton() {
        getSupportFragmentManager().popBackStack();
    }
}
