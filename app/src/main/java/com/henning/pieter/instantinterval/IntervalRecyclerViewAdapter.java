package com.henning.pieter.instantinterval;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.henning.pieter.instantinterval.IntervalFragment.OnListFragmentInteractionListener;


import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link IntervalItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class IntervalRecyclerViewAdapter extends RecyclerView.Adapter<IntervalRecyclerViewAdapter.ViewHolder> {

    private final List<IntervalItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public IntervalRecyclerViewAdapter(List<IntervalItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_interval, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mStartPodNameView.setText(mValues.get(position).startName);
        holder.mEndPodNameView.setText(mValues.get(position).endName);
        holder.mIntervalTimeView.setText(mValues.get(position).time);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mStartPodNameView;
        public final TextView mIntervalTimeView;
        public final TextView mEndPodNameView;
        public IntervalItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.interval_number);
            mStartPodNameView = (TextView) view.findViewById(R.id.start_pod_name);
            mIntervalTimeView = (TextView) view.findViewById(R.id.time);
            mEndPodNameView = (TextView) view.findViewById(R.id.end_pod_name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mStartPodNameView.getText() + "'";
        }
    }
}
