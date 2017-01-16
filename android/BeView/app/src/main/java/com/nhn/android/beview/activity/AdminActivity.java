package com.nhn.android.beview.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.nhn.android.beview.AppConstants;
import com.nhn.android.beview.R;
import com.nhn.android.beview.fragment.admin.ConferenceFragment;
import com.nhn.android.beview.fragment.admin.SectionFragment;
import com.nhn.android.beview.fragment.admin.SpeakerFragment;
import com.nhn.android.beview.fragment.admin.TrackFragment;
import com.nhn.android.beview.fragment.dummy.DummyContent;
import com.nhn.android.beview.model.Conference;
import com.nhn.android.beview.model.Section;

public class AdminActivity extends AppCompatActivity implements ConferenceFragment.OnConferenceFragmentListener
        , SectionFragment.OnSectionFragmentListener, BottomNavigationView.OnNavigationItemSelectedListener {

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
        switch (item.getItemId()) {
            case R.id.action_add: {
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.putExtra(AppConstants.ADMIN_CURRENT_FRAGMENT, fragment.toString());
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConferenceFragmentInteraction(Conference conference) {

    }


    @Override
    public void onSectionFragmentInteraction(Section section) {

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
            case R.id.nav_section: {
                fragment = SectionFragment.newInstance();
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


}
