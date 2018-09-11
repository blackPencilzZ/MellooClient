package com.auvx.melloo.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.auvx.melloo.domain.MessagePiece;

import java.io.Serializable;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder>
        implements Serializable{

    private List<MessagePiece> msgList;

    class ViewHolder extends RecyclerView.Adapter {

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List payloads) {
            super.onBindViewHolder(holder, position, payloads);
        }
    }
}
}
