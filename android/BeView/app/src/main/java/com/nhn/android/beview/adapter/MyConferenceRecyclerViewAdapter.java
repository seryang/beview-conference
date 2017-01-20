package com.nhn.android.beview.adapter;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nhn.android.beview.R;
import com.nhn.android.beview.fragment.admin.ConferenceFragment;
import com.nhn.android.beview.listener.OnItemButtonClickListener;
import com.nhn.android.beview.model.Conference;

import java.util.List;

public class MyConferenceRecyclerViewAdapter extends RecyclerView.Adapter<MyConferenceRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private final List<Conference> conferenceList;
    private final ConferenceFragment.OnConferenceFragmentListener onConferenceFragmentListener;
    private OnItemButtonClickListener onItemButtonClickListener;

    public MyConferenceRecyclerViewAdapter(List<Conference> items,
                                           ConferenceFragment.OnConferenceFragmentListener listener,
                                           OnItemButtonClickListener onItemButtonClickListener) {
        conferenceList = items;
        onConferenceFragmentListener = listener;
        this.onItemButtonClickListener = onItemButtonClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_conference, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.conference = conferenceList.get(position);
        holder.tvId.setText(conferenceList.get(position).getStrId());
        holder.tvName.setText(conferenceList.get(position).getName());
        holder.tvStartDate.setText(conferenceList.get(position).getStringStartDate());
        holder.tvEndDate.setText(conferenceList.get(position).getStringEndDate());
        holder.tvLocation.setText(conferenceList.get(position).getLocation());
        holder.tvUpdate.setOnClickListener(this);
        holder.tvDelete.setOnClickListener(this);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onConferenceFragmentListener) {
                    onConferenceFragmentListener.onConferenceFragmentInteraction(holder.conference);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return conferenceList.size();
    }

    @Override
    public void onClick(View v) {
        onItemButtonClickListener.onItemClick(v.getId());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View layout;
        public final TextView tvId, tvName, tvStartDate, tvEndDate, tvLocation;
        public final TextView tvUpdate, tvDelete;
        public Conference conference;

        public ViewHolder(View view) {
            super(view);
            layout = view;
            tvId = (TextView) view.findViewById(R.id.text_id);
            tvName = (TextView) view.findViewById(R.id.text_name);
            tvStartDate = (TextView) view.findViewById(R.id.text_start_date);
            tvEndDate = (TextView) view.findViewById(R.id.text_end_date);
            tvLocation = (TextView) view.findViewById(R.id.text_location);
            tvUpdate = (TextView) view.findViewById(R.id.text_update);
            tvDelete = (TextView) view.findViewById(R.id.text_delete);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvName.getText() + "'";
        }
    }
}
