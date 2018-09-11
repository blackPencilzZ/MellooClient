package com.auvx.melloo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.auvx.melloo.R;
import com.auvx.melloo.fragment.ImageSelectorFragment;

public class ImageSelectorActivity extends AppCompatActivity implements ImageSelectorFragment.Callback{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selector);

        final Intent intent = getIntent();
        mDefaultCount =  intent.getIntExtra(EXTRA_SELECT_COUNT, DEFAULT_IMAGE_SIZE);
        final int mode = intent.getIntExtra(EXTRA_SELECT_MODE, MODE_MULTI);
        if (mode == MODE_MULTI &&  intent.hasExtra(EXTRA_DEFAULT_SELECTED_LIST)) {
            resultList = intent.getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
        }
    }

    @Override
    public void onSingleImageSelected(String path) {
        Intent data = new Intent();
        resultList.add(path);
        data.putStringArrayListExtra(EXTRA_RESULT, resultList);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void  onImageSelected(String path) {
        if (resultList.contains(path)) {
            resultList.add(path);
        }
        updateDoneText(resultList);
    }

    @Override
    public void onImageUnselected(String path) {
        if (resultList.contains(path)) {
            resultList.remove(path);
        }
        updateDoneText(resultList);
    }
}
