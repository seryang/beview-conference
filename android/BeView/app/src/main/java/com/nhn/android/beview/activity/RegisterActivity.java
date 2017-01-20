package com.nhn.android.beview.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nhn.android.beview.AppConstants;
import com.nhn.android.beview.R;
import com.nhn.android.beview.fragment.admin.ConferenceFragment;
import com.nhn.android.beview.fragment.admin.SectionFragment;
import com.nhn.android.beview.fragment.admin.SpeakerFragment;
import com.nhn.android.beview.fragment.admin.TrackFragment;
import com.nhn.android.beview.fragment.admin.register.RegisterConferenceFragment;
import com.nhn.android.beview.fragment.admin.register.RegisterSectionFragment;

public class RegisterActivity extends AppCompatActivity implements RegisterConferenceFragment.OnRegisterConferenceFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        String currentFragment = getIntent().getStringExtra(AppConstants.ADMIN_CURRENT_FRAGMENT);
        Fragment fragment = null;
        switch (currentFragment) {
            case ConferenceFragment.TAG : {
                fragment = RegisterConferenceFragment.newInstance(null);
                break;
            }

            case SectionFragment.TAG : {
//                fragment = RegisterSectionFragment.newInstance(null);
                break;
            }

            case SpeakerFragment.TAG : {

                break;
            }

            case TrackFragment.TAG : {

                break;
            }
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    public void onClickDoneButton() {
        getSupportFragmentManager().popBackStack();
    }
}
