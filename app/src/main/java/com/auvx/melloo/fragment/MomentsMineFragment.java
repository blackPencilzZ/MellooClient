package com.auvx.melloo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auvx.melloo.R;
import com.auvx.melloo.constant.HttpAttributeName;
import com.auvx.melloo.constant.ResourcePath;
import com.auvx.melloo.context.Melloo;
import com.auvx.melloo.domain.MomentRecord;
import com.auvx.melloo.domain.StandardFeedback;
import com.auvx.melloo.exception.DataProcessingException;
import com.auvx.melloo.ui.adapter.MomentListAdapter;
import com.auvx.melloo.util.HttpOperator;
import com.auvx.melloo.util.JsonOperator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class MomentsMineFragment extends Fragment {

    final static String ONLINE_CLUE_ABSENCE = null;

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
        View fragmentLayout = inflater.inflate(R.layout.fragment_moments_mine, container, false);
        RecyclerView momentsMineView = fragmentLayout.findViewById(R.id.moments_mine);
        renderMomentsMine(momentsMineView, fetchMoments());
        return fragmentLayout;
    }

    private void renderMomentsMine(View layout, List<MomentRecord> momentsMine) {
        RecyclerView momentsNearbyView = layout.findViewById(R.id.moments_mine);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        momentsNearbyView.setLayoutManager(layoutManager);
        MomentListAdapter momentListAdapter = new MomentListAdapter(momentsMine);
        momentsNearbyView.setAdapter(momentListAdapter);
    }

    private List<MomentRecord> fetchMoments() {
        List<MomentRecord> momentsMine = requestMomentsMine();
        return momentsMine;
    }

    private List<MomentRecord> requestMomentsMine() {
        String url = ResourcePath.NetworkResourcePath.PATH_BASE
                + ResourcePath.NetworkResourcePath.MOMENTS_NEARBY;
        HashMap<String, String> extendHeaders = new HashMap<>();

        String onlineClue = Melloo.getSharedPreferences()
                .getString(HttpAttributeName.Extend.ONLINE_CLUE, ONLINE_CLUE_ABSENCE);

        extendHeaders.put(HttpAttributeName.Extend.ONLINE_CLUE, onlineClue);
        Request request = HttpOperator.buildJsonEncodePostRequest(url,extendHeaders);
        String respBodyText = HttpOperator.sendRequest(request);
        ObjectMapper mapper = JsonOperator.getMapper();
        StandardFeedback<List<MomentRecord>> feedback;
        try {
            feedback = mapper.readValue(respBodyText,
                    new TypeReference<StandardFeedback<List<MomentRecord>>>() {
                    });
        } catch (IOException e) {
            Log.d(LogTag.DATA_PROCESS, "", new DataProcessingException());
            return null;
        }
        if (feedback.getStatusCode().equals(2000)) {
            List<MomentRecord> moments = feedback.getData();
            return moments;
        } else {
            return null;
        }
    }
}
