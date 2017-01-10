package com.nhn.android.beview.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nhn.android.beview.R;
import com.nhn.android.beview.fragment.JoinFragment;
import com.nhn.android.beview.fragment.LoginFragment;

import java.io.Serializable;

import static com.nhn.android.beview.UserConstants.*;

public class UserActivity extends AppCompatActivity implements LoginFragment.OnLoginFragmentListener, JoinFragment.OnJoinFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Intent intent = getIntent();
        Serializable userEnum = intent.getSerializableExtra(USER_EXTRA);

        Fragment fragment = null;
        if (userEnum == User.LOGIN) {
            fragment = new LoginFragment();
        } else if (userEnum == User.JOIN) {
            fragment = new JoinFragment();
        }

        moveToFragment(fragment);
    }

    private void moveToFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClickMoveToLoginFragment() {
        moveToFragment(LoginFragment.newInstance());
    }

    @Override
    public void onClickMoveToJoinFragment() {
        moveToFragment(JoinFragment.newInstance());
    }
}
