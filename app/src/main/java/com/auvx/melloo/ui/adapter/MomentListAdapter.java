package com.auvx.melloo.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.auvx.melloo.R;
import com.auvx.melloo.domain.MomentRecord;

import java.util.List;

public class MomentListAdapter
        extends RecyclerView.Adapter<MomentListAdapter.ViewHolder> {

    ViewGroup mParent;
    private List<MomentRecord> mMomentRecords;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView momentIdView;
        RecyclerView contentView;

        public ViewHolder(View view) {
            super(view);
            momentIdView = (TextView) view.findViewById(R.id.account_id);
            contentView = (RecyclerView) view.findViewById(R.id.moment_record_content);
        }

        public RecyclerView getContentView() {
            return contentView;
        }
    }

    public MomentListAdapter(List<MomentRecord> momentRecords) {
        mMomentRecords = momentRecords;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mParent = parent;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.moment_record_content, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MomentRecord record = mMomentRecords.get(position);
        holder.momentIdView.setText(record.getAccountId().toString());

        bindRecordContentToView(holder.getContentView(), record.getTape(), record.getContentType());
    }

    private void bindRecordContentToView(RecyclerView contentView, List<String> contentItems,
                                         Integer contentMediaType) {

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(mParent.getContext(), LinearLayout.HORIZONTAL, false);
        contentView.setLayoutManager(layoutManager);
        MomentContentAdapter momentAdapter = new MomentContentAdapter(contentItems, contentMediaType);
        contentView.setAdapter(momentAdapter);

    }

    @Override
    public int getItemCount() {
        return mMomentRecords.size();
    }
    
}
