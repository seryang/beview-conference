package com.nhn.android.beview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nhn.android.beview.R;
import com.nhn.android.beview.fragment.admin.TrackFragment;
import com.nhn.android.beview.listener.OnItemButtonClickListener;
import com.nhn.android.beview.model.Track;

import java.util.List;

public class MyTrackRecyclerViewAdapter extends RecyclerView.Adapter<MyTrackRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private final List<Track> trackList;
    private final OnItemButtonClickListener onItemButtonClickListener;

    public MyTrackRecyclerViewAdapter(List<Track> trackList, OnItemButtonClickListener onItemButtonClickListener) {
        this.trackList = trackList;
        this.onItemButtonClickListener = onItemButtonClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_track, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = trackList.get(position);
        holder.tvName.setText(trackList.get(position).getName());
        holder.tvLocation.setText(trackList.get(position).getLocation());
        holder.tvUpdate.setOnClickListener(this);
        holder.tvDelete.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    @Override
    public void onClick(View v) {
        onItemButtonClickListener.onItemClick(v.getId());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View layout;
        public final TextView tvName, tvLocation;
        public final TextView tvUpdate, tvDelete;
        public Track mItem;

        public ViewHolder(View view) {
            super(view);
            layout = view;
            tvName = (TextView) view.findViewById(R.id.text_name);
            tvLocation = (TextView) view.findViewById(R.id.text_location);
            tvUpdate = (TextView) view.findViewById(R.id.text_update);
            tvDelete = (TextView) view.findViewById(R.id.text_delete);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvLocation.getText() + "'";
        }
    }
}
