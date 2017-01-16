package com.nhn.android.beview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nhn.android.beview.R;
import com.nhn.android.beview.listener.OnItemButtonClickListener;
import com.nhn.android.beview.model.Speaker;

import java.util.List;

public class MySpeakerRecyclerViewAdapter extends RecyclerView.Adapter<MySpeakerRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private final List<Speaker> speakerList;
    private final OnItemButtonClickListener onItemButtonClickListener;

    public MySpeakerRecyclerViewAdapter(List<Speaker> speakerList, OnItemButtonClickListener onItemButtonClickListener) {
        this.speakerList = speakerList;
        this.onItemButtonClickListener = onItemButtonClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_speaker, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item = speakerList.get(position);
        holder.tvName.setText(speakerList.get(position).getName());
        holder.tvEmail.setText(speakerList.get(position).getEmail());
        holder.tvPhone.setText(speakerList.get(position).getPhone());
        holder.tvDescription.setText(speakerList.get(position).getDescription());
        holder.tvUpdate.setOnClickListener(this);
        holder.tvDelete.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return speakerList.size();
    }

    @Override
    public void onClick(View v) {
        onItemButtonClickListener.onItemClick(v.getId());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvName, tvEmail, tvPhone, tvDescription;
        public final TextView tvUpdate, tvDelete;
        public Speaker item;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvName = (TextView) view.findViewById(R.id.text_name);
            tvEmail = (TextView) view.findViewById(R.id.text_email);
            tvPhone = (TextView) view.findViewById(R.id.text_phone);
            tvDescription = (TextView) view.findViewById(R.id.text_description);
            tvUpdate = (TextView) view.findViewById(R.id.text_update);
            tvDelete = (TextView) view.findViewById(R.id.text_delete);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvName.getText() + "'";
        }
    }
}
