package com.nhn.android.beview.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhn.android.beview.R;
import com.nhn.android.beview.adapter.MyConferenceRecyclerViewAdapter;
import com.nhn.android.beview.fragment.dummy.ConferenceDummy;
import com.nhn.android.beview.fragment.dummy.DummyContent;
import com.nhn.android.beview.fragment.dummy.DummyContent.DummyItem;
import com.nhn.android.beview.model.Conference;

public class ConferenceFragment extends Fragment {


    private OnConferenceFragmentListener mListener;
    public ConferenceFragment() {
    }

    public static ConferenceFragment newInstance() {
        return new ConferenceFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conference_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyConferenceRecyclerViewAdapter(ConferenceDummy.ITEMS, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnConferenceFragmentListener) {
            mListener = (OnConferenceFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnConferenceFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnConferenceFragmentListener {
        void onConferenceFragmentInteraction(Conference conference);
    }
}
