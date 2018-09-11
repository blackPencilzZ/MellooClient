package com.auvx.melloo.ui.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.auvx.melloo.R;
import com.auvx.melloo.domain.MomentRecord;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class MomentContentAdapter extends RecyclerView.Adapter<MomentContentAdapter.BaseViewHolder> {

    private Integer mContentMediaType;
    private List<String> mItemRefs;

    private enum MomentViewType {
        TEXT(1), PICTURE(2), VIDEO(4);

        private int value;

        MomentViewType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public MomentContentAdapter(List<String> itemRefs, Integer contentMediaType) {
        mContentMediaType = contentMediaType;
        mItemRefs = itemRefs;
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        TextView mIdView;

        public BaseViewHolder(View view) {
            super(view);
            TextView idView = view.findViewById(R.id.moment_id);
            mIdView = idView;
        }
    }
    class TextViewHolder extends BaseViewHolder{
        TextView mContentView;
        TextViewHolder(View view){
            super(view);
            TextView contentView = view.findViewById(R.id.moment_text_record);
            mContentView = contentView;
        }
    }

    class ImageViewHolder extends BaseViewHolder{
        ImageView mContentView;
        ImageViewHolder(View view) {
            super(view);
            ImageView contentView = view.findViewById(R.id.moment_picture_record);

            mContentView = contentView;
        }
    }

    class VideoViewHolder extends BaseViewHolder{
        VideoView mContentView;
        VideoViewHolder(View view) {
            super(view);
            VideoView contentView = view.findViewById(R.id.moment_video_record);
            mContentView = contentView;
        }
    }
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MomentViewType.TEXT.getValue()) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.moment_text_record_item, parent, false);
            BaseViewHolder holder = new TextViewHolder(view);
            return holder;
        } else if (viewType == MomentViewType.PICTURE.getValue()) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.moment_picture_record_item, parent, false);
            BaseViewHolder holder = new ImageViewHolder(view);
            return holder;
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.moment_video_record_item, parent, false);
            BaseViewHolder holder = new VideoViewHolder(view);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (holder instanceof TextViewHolder) {
            ((TextViewHolder) holder).mContentView.setText(mItemRefs.get(position));
        } else if (holder instanceof ImageViewHolder) {
            String imgUrl = mItemRefs.get(position);
            Bitmap image = fetchImage(imgUrl);
            if (image != null) {
                ((ImageViewHolder) holder).mContentView.setImageBitmap(image);
            }
        }
    }

    private Bitmap fetchImage(String imgUrl) {
        InputStream is = null;
        try {
            try {
                URL url = new URL(imgUrl);
                is = url.openStream();
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        } catch (IOException e) {
            Log.d("","");
        }
        Bitmap bitmap = null;
        if (is != null) {
            bitmap = BitmapFactory.decodeStream(is);
        }
        return bitmap;
    }

    @Override
    public int getItemViewType(int position) {
        int contentType = mContentMediaType;
        if (contentType == MomentRecord.ContentMediaType.TEXT.getValue()) {
            return MomentViewType.TEXT.getValue();
        } else if (contentType == MomentRecord.ContentMediaType.PICTURE.getValue()) {
            return MomentViewType.PICTURE.getValue();
        } else {
            return MomentViewType.VIDEO.getValue();
        }
    }

    @Override
    public int getItemCount() {
        return mItemRefs.size();
    }
}
