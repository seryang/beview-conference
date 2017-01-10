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
import com.nhn.android.beview.adapter.MyScheduleRecyclerViewAdapter;
import com.nhn.android.beview.fragment.dummy.DummyContent;
import com.nhn.android.beview.fragment.dummy.DummyContent.DummyItem;

public class ScheduleFragment extends Fragment {

    private OnScheduleItemClickListener mListener;

    public ScheduleFragment() {
    }

    public static ScheduleFragment newInstance() {
        return new ScheduleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyScheduleRecyclerViewAdapter(DummyContent.ITEMS, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnScheduleItemClickListener) {
            mListener = (OnScheduleItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnScheduleItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnScheduleItemClickListener {
        void onClickScheduleItem(DummyItem item);
    }
}
