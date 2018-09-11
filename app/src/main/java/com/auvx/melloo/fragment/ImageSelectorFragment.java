package com.auvx.melloo.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auvx.melloo.R;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ImageSelectorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ImageSelectorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageSelectorFragment extends Fragment {

    public ImageSelectorFragment() {
        // Required empty public constructor
    }

    public static ImageSelectorFragment newInstance(String param1, String param2) {
        ImageSelectorFragment fragment = new ImageSelectorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {

        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media._ID
        };

        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
            CursorLoader cursorLoader = null;
            if(id == LOADER_ALL) {
                cursorLoader = new CursorLoader(getActivity(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_PROJECTION,
                        IMAGE_PROJECTION[4] + "> 0 AND " + IMAGE_PROJECTION[3] + "=? OR "
                                + IMAGE_PROJECTION[3] + "=?",
                        new String[] {"image/jpeg", "image/png"},
                        IMAGE_PROJECTION[2] + "DESC");
            } else if (id == LOADER_CATEGORY) {
                cursorLoader = new CursorLoader(getActivity(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_PROJECTION,
                        IMAGE_PROJECTION[4] +  ">0 AND" + IMAGE_PROJECTION[0] + " LIKE '%"
                                + args.getString("path") + "%'",
                        null, IMAGE_PROJECTION[2] + " DESC");
            }
            return cursorLoader;
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
            if (data != null) {
                if (data.getCount() > 0) {
                    data.moveToFirst();
                    do{
                        String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                        String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                        long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));

                        if (!TextUtils.isEmpty(path)) {
                        }

                        if (!hasFolderGened) {
                            File folder = new File(path).getParentFile();
                            if(folder != null && folder.exists()) {
                                String f = folder.getAbsolutePath();
                                Folder fd = getFolderByPath(f);
                                if (fd == null) {

                                }
                            }
                        }
                    } while {
                        data.moveToNext();
                    }

                    mImageAdapter.setData(images);
                    if(reslutList != null && resultList.size() > 0) {
                        mImageAdapter.setDefaultSelected(resultList);
                    }
                    if (!hasFolderGened) {
                        mFolderAdapter.setData(mResultFolder);
                        hasFolderGened = true;
                    }
                }
            }
        }

        private Folder probeGenedFolder(String path) {
            if (mResultFolder != null) {
                for (Folder folder : mResultFolder) {
                    if (TextUtils.equals(folder.path, path)) {
                        return folder;
                    }
                }
            }
            return null;
        }
        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        }
    }

    public interface Callback{
        void onSingleImageSelected(String path);
        void onImageSelected(String path);
        void onImageUnselected(String path);
        void onCameraShot(File imageFile);
    }
}
