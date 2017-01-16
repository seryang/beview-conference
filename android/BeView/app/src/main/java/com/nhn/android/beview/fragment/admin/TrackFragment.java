package com.nhn.android.beview.fragment.admin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhn.android.beview.R;
import com.nhn.android.beview.adapter.MyTrackRecyclerViewAdapter;
import com.nhn.android.beview.fragment.dummy.DummyContent;
import com.nhn.android.beview.fragment.dummy.DummyContent.DummyItem;
import com.nhn.android.beview.fragment.dummy.TrackDummy;
import com.nhn.android.beview.listener.OnItemButtonClickListener;
import com.nhn.android.beview.model.Track;

public class TrackFragment extends Fragment implements OnItemButtonClickListener{

    public static final String TAG = "TrackFragment";
    private MyTrackRecyclerViewAdapter adapter;

    public TrackFragment() {
    }

    public static TrackFragment newInstance() {
        return new TrackFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyTrackRecyclerViewAdapter(TrackDummy.ITEMS, this));
        }
        return view;
    }

    @Override
    public String toString() {
        return TAG;
    }

    @Override
    public void onItemClick(@IdRes int id) {
        switch (id) {
            case R.id.text_update : {

                break;
            }

            case R.id.text_delete : {

                break;
            }
        }
        adapter.notifyDataSetChanged();
    }
}
