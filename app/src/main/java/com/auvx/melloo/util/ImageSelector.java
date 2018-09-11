package com.auvx.melloo.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.auvx.melloo.activity.ImageSelectorActivity;

public class ImageSelector {

    private ImageSelector() {

    }

    public static ImageSelector create() {
        if (sSelector == null) {
            sSelector = new ImageSelector();
        }
        return sSelector;
    }

    public ImageSelector count(int count) {
        mMaxCount = count;
        return sSelector;
    }

    public void start(Activity activity, int requestCode) {
        final Context context = activity;
        if ((hasPermissoin(context)){
            activity.startActivityForResult(createIntent(context), requestCode);
        } else {

        }
    }

    public void start(Fragment fragment, int requestCode) {
        final Context context = fragment.getContext();
        if (hasPermission(context)) {
            fragment.startActivityForResult(createInent(context), requestCode);
        } else {

        }
    }

    private Intent createIntent(Context context) {
        Intent intent = new Intent(context, ImageSelectorActivity.class);
        intent.putExtra(ImageSelectorActivity.EXTRA_SELECT_COUNT, mMaxCount);
        if(mOriginData != null) {
            intent.putStringArrayListExtra(ImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mOriginData);
        }
        intent.putExtra(ImageSelectorActivity.EXTRA_SELECT_MODE,Mode);
    }
}
