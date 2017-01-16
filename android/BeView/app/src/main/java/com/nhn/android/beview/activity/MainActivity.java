package com.nhn.android.beview.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;

import com.nhn.android.beview.R;
import com.nhn.android.beview.AppConstants;
import com.nhn.android.beview.fragment.HomeFragment;
import com.nhn.android.beview.fragment.ScheduleFragment;
import com.nhn.android.beview.fragment.dummy.DummyContent;
import com.nhn.android.beview.model.User;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static android.R.attr.process;
import static java.net.HttpURLConnection.HTTP_OK;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFragmentButtonListener, ScheduleFragment.OnScheduleItemClickListener {

    private static final String TAG = "MainActivity";
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
        changeMenuItem();
    }

    private void changeMenuItem() {
        Menu menu = navigationView.getMenu();
        //TODO 로그인 성공
        MenuItem joinItem = menu.findItem(R.id.nav_join);
        MenuItem loginItem = menu.findItem(R.id.nav_login);
        MenuItem logoutItem = menu.findItem(R.id.nav_logout);
        MenuItem dashboardItem = menu.findItem(R.id.nav_dashboard);

        if (getCookie() != null) {
            joinItem.setVisible(false);
            loginItem.setVisible(false);
            logoutItem.setVisible(true);

            //TODO 어드민 아이디
            if (getUserId().equals("admin")) {
                dashboardItem.setVisible(true);
            } else {
                dashboardItem.setVisible(false);
            }

        } else {
            joinItem.setVisible(true);
            loginItem.setVisible(true);
            logoutItem.setVisible(false);
        }
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
                processLogout();
                break;
            }
            case R.id.nav_dashboard: {
                Intent intent = new Intent(this, AdminActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_login: {
                Intent intent = new Intent(this, UserActivity.class);
                intent.putExtra(AppConstants.USER_EXTRA, AppConstants.User.LOGIN);
                startActivity(intent);
                break;
            }
            case R.id.nav_join: {
                Intent intent = new Intent(this, UserActivity.class);
                intent.putExtra(AppConstants.USER_EXTRA, AppConstants.User.JOIN);
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

    private void processLogout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().removeAllCookies(null);
        } else {
            CookieManager.getInstance().removeAllCookie();
        }
        changeMenuItem();
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

    private String getCookie() {
        return CookieManager.getInstance().getCookie(AppConstants.HOST_URL);
    }

    private String getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences(AppConstants.USER_SHARED_PREFER, MODE_PRIVATE);
        String id = sharedPreferences.getString(AppConstants.USER_ID_SHARED_PREFER, "");
        return id;
    }
}
