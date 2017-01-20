package com.nhn.android.beview.fragment.admin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhn.android.beview.R;
import com.nhn.android.beview.adapter.MyConferenceRecyclerViewAdapter;
import com.nhn.android.beview.adapter.MySectionRecyclerViewAdapter;
import com.nhn.android.beview.fragment.dummy.SectionDummy;
import com.nhn.android.beview.listener.OnItemButtonClickListener;
import com.nhn.android.beview.model.Section;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnSectionFragmentListener}
 * interface.
 */
public class SectionFragment extends Fragment implements OnItemButtonClickListener {

    public static final String TAG = "SectionFragment";
    private OnSectionFragmentListener mListener;
    private MySectionRecyclerViewAdapter adapter;

    public SectionFragment() {
    }

    public static SectionFragment newInstance() {
        return new SectionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_container, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter = new MySectionRecyclerViewAdapter(SectionDummy.ITEMS, mListener, this);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSectionFragmentListener) {
            mListener = (OnSectionFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSectionFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(@IdRes int id) {
        switch (id) {
            case R.id.text_update : {
                requestUpdate();
                break;
            }
            case R.id.text_delete : {
                requestDelete();
                break;
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void requestDelete() {
        Log.d(TAG, "requestDelete()");
    }

    private void requestUpdate() {
        Log.d(TAG, "requestUpdate()");
    }

    public interface OnSectionFragmentListener {
        void onSectionFragmentInteraction(Section section);
    }

    @Override
    public String toString() {
        return TAG;
    }
}
