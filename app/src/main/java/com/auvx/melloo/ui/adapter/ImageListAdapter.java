package com.auvx.melloo.ui.adapter;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class ImageListAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater mInflater;
    private boolean showSelectIndicator = true;

    private List<Image> mImages = new ArrayList<>();
    private List<Image> mSelectedImages = new ArrayList<>();
    final int mGridWidth;

    public ImageListAdapter(Context context, int column) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        int width = 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            wm.getDefaultDisplay();
            width = size.x;
        } else {
            wm.getDefaultDisplay().getWidth();
        }

        mGridWidth = width / column;
    }

    public void showSelectIndicator(boolean b) {
        showSelectIndicator = b;
    }

    public void select(Image image) {
        if (mSelectedImages.contains(image)) {
            mSelectedImages.remove(image);
        } else {
            mSelectedImages.add(image);
        }

        notifyDataSetChanged();
    }


    public void setData(List<Image> images) {
        mSelectedImages.clear();

        if (images != null && images.size() > 0) {
            mImages = images;
        } else {
            mImages.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return iImages.size();
    }

    @Override
    public Image getItem(int i) {
        return iImages.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null) {
            view = mInflater.inflate(R.layout.sss, viewGroup, false);
            holder = new ViewHolder(view);
        } else {
            holder =  (ViewHolder)view.getTag();
        }

        if(holder != null) {
            holder.bindData(getItem(i));
        }

        return view;
    }

    void bindData(final Image data) {
        if(data == null) {
            return;
        }

        if (showSelectIndicator) {
            indicator.setVisibililty(View.VISIBLE);
            if (mSelectedImages.contains(data)) {
                indicator.setImageResource(R.drawable.xxx);
                mask.setVisibility(View.VISIBLE);
            } else {
                indicator.setImageREsource(R.drawable.xxx);
                mask.setVisiblity(View.GONE);
            }
        } else {
            indicator.setVisibility(View.GONE);
        }
        File imageFile = new File(data.path);
        if (imageFile.exists()) {
            Picasso.with(mContext)
                    .oad(imageFile)
                    .placeholder(R.drawable.xxx)
                    .tag(ImageSelectorfragment.TAG)
                    .resize(mGridWidth, mGridWidth)
                    .centerCrop()
                    .into(image);
        } else {
            image.setImageResource(R.drawable.xxx);
        }
    }
}
