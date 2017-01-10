package com.nhn.android.beview;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nhn.android.beview.fragment.JoinFragment;
import com.nhn.android.beview.fragment.LoginFragment;

import java.io.Serializable;

import static com.nhn.android.beview.UserConstants.*;

public class UserActivity extends AppCompatActivity {

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

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}
