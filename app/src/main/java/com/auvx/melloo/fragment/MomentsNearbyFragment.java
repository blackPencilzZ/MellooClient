package com.auvx.melloo.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.auvx.melloo.domain.MomentsAccountBinding;
import com.auvx.melloo.domain.StandardFeedback;
import com.auvx.melloo.domain.UserLocation;
import com.auvx.melloo.exception.DataProcessingException;
import com.auvx.melloo.ui.adapter.MomentsAccountBindingListAdapter;
import com.auvx.melloo.util.HttpOperator;
import com.auvx.melloo.util.JsonOperator;
import com.auvx.melloo.util.LocationManager;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class MomentsNearbyFragment extends Fragment {

    private List<MomentsAccountBinding> mBindingList;
    private RecyclerView mMomentsNearbyView;
    private MomentsAccountBindingListAdapter mBindingListAdapter;
    static final String ONLINE_CLUE_ABSENCE = null;

    public static MomentsNearbyFragment newInstance(String param1, String param2) {
        MomentsNearbyFragment fragment = new MomentsNearbyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentLayout =
                inflater.inflate(R.layout.fragment_moments_nearby, container, false);
        renderMomentsNearby(fragmentLayout, fetchMoments());
        return fragmentLayout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void renderMomentsNearby(View layout, List<MomentsAccountBinding> ms) {
        MomentsAccountBinding bindingT = new MomentsAccountBinding();
        bindingT.setAccountId(1001L);
        ArrayList<MomentRecord> momentsT = new ArrayList<>(1);
        MomentRecord momentT = new MomentRecord();
        momentT.setId(1234L);
        momentT.setContentType(1);
        ArrayList<String> tape = new ArrayList<>();
        tape.add("abcdefghijklmn");
        momentT.setTape(tape);
        momentsT.add(momentT);
        bindingT.setMoments(momentsT);

        MomentsAccountBinding bindingI = new MomentsAccountBinding();
        bindingI.setAccountId(1002L);
        ArrayList<MomentRecord> momentsI = new ArrayList<>(2);
        MomentRecord momentIa = new MomentRecord();
        MomentRecord momentIb = new MomentRecord();
        momentIa.setId(1235L);
        momentIa.setContentType(2);
        momentIb.setId(1236L);
        momentIb.setContentType(2);
        //momentI.setContentRef("abcdefghijklmn");
        momentsI.add(momentIa);
        momentsI.add(momentIb);
        bindingI.setMoments(momentsI);

        List<MomentsAccountBinding> bindingList = new ArrayList<>(2);

        bindingList.add(bindingT);
        bindingList.add(bindingI);

        RecyclerView momentsNearbyView = layout.findViewById(R.id.moments_nearby);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        momentsNearbyView.setLayoutManager(layoutManager);
        MomentsAccountBindingListAdapter momentsAccountBindingAdapter =
                new MomentsAccountBindingListAdapter(bindingList);
        momentsNearbyView.setAdapter(momentsAccountBindingAdapter);
    }

    private List<MomentsAccountBinding> fetchMoments() {
        UserLocation location = LocationManager.requestSyncLocation();
        List<MomentsAccountBinding> momentsAccountBindings = requestMomentsNearby(location);
        return momentsAccountBindings;
    }

    private List<MomentsAccountBinding> requestMomentsNearby(UserLocation userLocation) {
        String url = ResourcePath.NetworkResourcePath.PATH_BASE
                + ResourcePath.NetworkResourcePath.MOMENTS_NEARBY;
        HashMap<String, String> extendHeaders = new HashMap<>();
        String onlineClue = Melloo.getSharedPreferences()
                .getString(HttpAttributeName.Extend.ONLINE_CLUE, ONLINE_CLUE_ABSENCE);
        extendHeaders.put(HttpAttributeName.Extend.ONLINE_CLUE, onlineClue);
        Request request = HttpOperator.buildJsonEncodePostRequest(extendHeaders, url, userLocation);
        String respBodyText = HttpOperator.sendRequest(request);
        ObjectMapper mapper = JsonOperator.getMapper();
        StandardFeedback<List<MomentsAccountBinding>> feedback;
        try {
            feedback = mapper.readValue(respBodyText,
                    new TypeReference<StandardFeedback<List<MomentsAccountBinding>>>() {
                    });
        } catch (IOException e) {
            Log.d(LogTag.DATA_PROCESS, "", new DataProcessingException());
            return null;
        }
        if (feedback.getStatusCode().equals(2000)) {
            List<MomentsAccountBinding> moments = feedback.getData();
            return moments;
        } else {
            return null;
        }
    }
}
