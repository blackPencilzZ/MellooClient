package com.auvx.melloo.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.auvx.melloo.R;
import com.auvx.melloo.activity.MainActivity;
import com.auvx.melloo.activity.VisitActivity;
import com.auvx.melloo.constant.field.AccountField;
import com.auvx.melloo.context.Melloo;
import com.auvx.melloo.domain.MomentRecord;
import com.auvx.melloo.domain.MomentsAccountBinding;

import java.util.List;

public class MomentsAccountBindingListAdapter
        extends RecyclerView.Adapter<MomentsAccountBindingListAdapter.ViewHolder> {

    private List<MomentsAccountBinding> mMomentsAccountBindingList;
    private static ViewGroup mParent;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView accountIdView;
        ImageView accountThumbnailView;
        RecyclerView momentsView;

        public ViewHolder(View view) {
            super(view);
            accountIdView = (TextView) view.findViewById(R.id.account_id);
            accountThumbnailView = (ImageView) view.findViewById(R.id.account_thumbnail);
            momentsView = (RecyclerView) view.findViewById(R.id.moment_record_content);
        }

        public RecyclerView getMomentsView() {
            return momentsView;
        }
    }

    public MomentsAccountBindingListAdapter(List<MomentsAccountBinding> momentsAccountBindingList) {
        mMomentsAccountBindingList = momentsAccountBindingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mParent = parent;
        View momentsSomebodyView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.moments_somebody, parent, false);
        ViewHolder holder = new ViewHolder(momentsSomebodyView);

        holder.accountThumbnailView.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        int position = holder.getAdapterPosition();
                        MomentsAccountBinding binding = mMomentsAccountBindingList.get(position);
                        Long accountId = binding.getAccountId();
                        //　TODO 结束当前活动，然后使用Intent传递accountId到　VisitActivity
                        // TODO 使用accountId作为条件，向服务器请求　用户资料
                        Intent intent = new Intent(mParent.getContext(), VisitActivity.class);
                        intent.putExtra(AccountField.ACCOUNT_ID, accountId);
                        mParent.getContext().startActivity(intent);
                    }
                }
        );
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MomentsAccountBinding momentsAccountBinding = mMomentsAccountBindingList.get(position);
        holder.accountIdView.setText(momentsAccountBinding.getAccountId().toString());
        holder.accountThumbnailView.setImageResource(R.drawable.lilithposter);

        //TODO 在这里有position啊！ 所以可以顺便绑定内层的了啊
        bindMomentsSomebodyView(holder.getMomentsView(), momentsAccountBinding.getMoments());
    }

    private void bindMomentsSomebodyView(RecyclerView momentsSomebodyView, List<MomentRecord> moments) {

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(mParent.getContext(), LinearLayout.HORIZONTAL, false);
        momentsSomebodyView.setLayoutManager(layoutManager);
        MomentListAdapter momentListAdapter = new MomentListAdapter(moments);
        momentsSomebodyView.setAdapter(momentListAdapter);

    }

    @Override
    public int getItemCount() {
        return mMomentsAccountBindingList.size();
    }
}
