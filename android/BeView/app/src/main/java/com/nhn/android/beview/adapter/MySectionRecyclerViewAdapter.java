package com.nhn.android.beview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nhn.android.beview.R;
import com.nhn.android.beview.fragment.admin.SectionFragment;
import com.nhn.android.beview.listener.OnItemButtonClickListener;
import com.nhn.android.beview.model.Section;

import java.util.List;

public class MySectionRecyclerViewAdapter extends RecyclerView.Adapter<MySectionRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private final List<Section> sectionList;
    private final SectionFragment.OnSectionFragmentListener onSectionFragmentListener;
    private final OnItemButtonClickListener onItemButtonClickListener;

    public MySectionRecyclerViewAdapter(List<Section> sectionList,
                                        SectionFragment.OnSectionFragmentListener onSectionFragmentListener,
                                        OnItemButtonClickListener onItemButtonClickListener) {
        this.sectionList = sectionList;
        this.onSectionFragmentListener = onSectionFragmentListener;
        this.onItemButtonClickListener = onItemButtonClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_section, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.section = sectionList.get(position);
        holder.tvName.setText(sectionList.get(position).getName());
        holder.tvDescription.setText(sectionList.get(position).getDescription());
        holder.tvTrack.setText(sectionList.get(position).getTrackName());
        holder.tvStartDate.setText(sectionList.get(position).getStringStartTime());
        holder.tvEndDate.setText(sectionList.get(position).getStringEndTime());
        holder.tvDelete.setOnClickListener(this);
        holder.tvUpdate.setOnClickListener(this);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onSectionFragmentListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    onSectionFragmentListener.onSectionFragmentInteraction(holder.section);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sectionList.size();
    }

    @Override
    public void onClick(View v) {
        onItemButtonClickListener.onItemClick(v.getId());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View layout;
        public final TextView tvName, tvDescription, tvTrack, tvStartDate, tvEndDate;
        public final TextView tvUpdate, tvDelete;
        public Section section;

        public ViewHolder(View view) {
            super(view);
            layout = view;
            tvName = (TextView) view.findViewById(R.id.text_name);
            tvDescription = (TextView) view.findViewById(R.id.text_description);
            tvTrack = (TextView) view.findViewById(R.id.text_track);
            tvStartDate = (TextView) view.findViewById(R.id.text_start_date);
            tvEndDate = (TextView) view.findViewById(R.id.text_end_date);
            tvUpdate = (TextView) view.findViewById(R.id.text_update);
            tvDelete = (TextView) view.findViewById(R.id.text_delete);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvName.getText() + "'";
        }
    }
}
