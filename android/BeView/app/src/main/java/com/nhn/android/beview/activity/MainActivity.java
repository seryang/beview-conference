package com.nhn.android.beview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nhn.android.beview.R;
import com.nhn.android.beview.UserConstants;
import com.nhn.android.beview.fragment.HomeFragment;
import com.nhn.android.beview.fragment.ScheduleFragment;
import com.nhn.android.beview.fragment.dummy.DummyContent;

import static android.R.id.toggle;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFragmentButtonListener, ScheduleFragment.OnScheduleItemClickListener {

    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        moveToFragment(HomeFragment.newInstance());
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Menu menu = navigationView.getMenu();
        menu.getItem(0).setVisible(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = null;
        switch (id) {
            case R.id.nav_home: {
                fragment = HomeFragment.newInstance();
                break;
            }
            case R.id.nav_schedule: {
                fragment = ScheduleFragment.newInstance();
                break;
            }
            case R.id.nav_logout: {
                break;
            }
            case R.id.nav_dashboard: {
                break;
            }
            case R.id.nav_login: {
                Intent intent = new Intent(this, UserActivity.class);
                intent.putExtra(UserConstants.USER_EXTRA, UserConstants.User.LOGIN);
                startActivity(intent);
                break;
            }
            case R.id.nav_join: {
                Intent intent = new Intent(this, UserActivity.class);
                intent.putExtra(UserConstants.USER_EXTRA, UserConstants.User.JOIN);
                startActivity(intent);
                break;
            }
        }
        if (fragment != null) {
            moveToFragment(fragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClickFragmentButton() {
        moveToFragment(ScheduleFragment.newInstance());
    }

    private void moveToFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    @Override
    public void onClickScheduleItem(DummyContent.DummyItem item) {

    }
}
