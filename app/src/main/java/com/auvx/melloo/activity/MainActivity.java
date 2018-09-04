package com.auvx.melloo.activity;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.auvx.melloo.R;
import com.auvx.melloo.constant.AppLocalDataDir;
import com.auvx.melloo.constant.HttpAttributeName;
import com.auvx.melloo.constant.LogTag;
import com.auvx.melloo.constant.ResourcePath;
import com.auvx.melloo.domain.MomentRecord;
import com.auvx.melloo.domain.MomentsAccountBinding;
import com.auvx.melloo.domain.StandardFeedback;
import com.auvx.melloo.domain.UserLocation;
import com.auvx.melloo.exception.DataProcessingException;
import com.auvx.melloo.ui.MomentsAccountBindingListAdapter;
import com.auvx.melloo.util.HttpOperator;
import com.auvx.melloo.util.JsonOperator;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    //如果作为一个需要共享的变量的话，那就有点麻烦了
    public static String online_clue = null;
    public LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences appHandbook =
                getSharedPreferences(AppLocalDataDir.APP_SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor bookEditor = appHandbook.edit();
        bookEditor.putString("online_clue", "");
        online_clue = "xxx";
        checkPermission();
        //TODO
        //TODO 创建本地数据库
        //TODO 使用　LBS SDK 获取位置
        //TODO 使用 OkHttp 用位置坐标向服务器

        // TODO 1.查询本地网络是否可用 2.online_clue是否不为空  后h'f
        // 台创建接受消息请求
        /***************************************************************************************
        服务器端
         //TODO 查询Redis 并比较客户端参数的登录标识 判断用户是否是登录装状态
         //TODO 情况有 1 客户端标识空缺，2 Redis null（因为超时）, 3 客户端与Redis不匹配

         //TODO 根据客户端请求参数keep_online判断是否重新登录并使用secret_sign
         //TODO 重新登录后，刷新Redis，返回给客户端新生成的登录标识

         //TODO 重新登录是根据客户端请求参数 无须判断device 因为是重新嘛，当然不是更换设备登录了
         // 这个应该是在登录的Activity服务端的里的，
         //如果是更换设备，使用JPush给当前登录的设备推送下线通知，并清空Redis此用户的缓存

         //TODO 如果最终用户处于登录状态返回给客户端，附近的人的状态，以及已经登录标识online = 1
         //TODO 如果 最终判断用户不需要登录，返回附近的人状态，以及online = 0
        ****************************************************************************************/

        //TODO 就收服务端响应，刷新online, 展示MomentRecords
        /*MellooSQLiteOpenHelper dbHelper = new MellooSQLiteOpenHelper(this,
                "melloo.db", null, 1);*/

        outfitToolbar();
        outfitMomentsNearbyView();

        //TODO 给三个　发布　按钮绑定事件
        add876t7
        FloatingActionButton addTextMoment = findViewById(R.id.add_text_moment);
        addTextMoment.setOnClickListener(new View.OnClickListener() {

        });
        addTextMoment.setOnHoverListener(new View.OnHoverListener() {
            //显示　按钮功能和使用方式
        });
        addTextMoment.setOnLongClickListener(new View.OnLongClickListener() {

        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.stat:
                Toast.makeText(this, "You clicked stat", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this, "You clicked setting", Toast.LENGTH_LONG).show();
                break;
            default:
        }
        return true;
    }

    private void checkPermission() {
        List<Object> permissionList = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        } else {
//            requestAsyncLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "Those Is Necessary",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                } else {
                    Toast.makeText(this, "Unknow Error",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }


    private UserLocation requestSyncLocation() {
        final BDLocation location = mLocationClient.getLastKnownLocation();
        UserLocation userLocation = new UserLocation();
        userLocation.setLongitude(location.getLongitude());
        userLocation.setLatitude(location.getLatitude());
        location.getCountryCode();
        location.getCityCode();
        return userLocation;
    }

    private List<MomentsAccountBinding> requestMomentsNearby(UserLocation userLocation) {
        String url = ResourcePath.NetworkResourcePath.PATH_BASE
                + ResourcePath.NetworkResourcePath.MOMENTS_NEARBY;
        HashMap<String, String> extendHeaders = new HashMap<>();
        extendHeaders.put(HttpAttributeName.Extend.ONLINE_CLUE, online_clue);
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
        if(feedback.getStatusCode().equals(2000)) {
            List<MomentsAccountBinding> moments = feedback.getData();
            return moments;
        } else {
            return null;
        }
    }

    private void renderMomentsNearby(List<MomentsAccountBinding> ms) {
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

        RecyclerView momentsNearbyView = (RecyclerView) findViewById(R.id.moments_nearby);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        momentsNearbyView.setLayoutManager(layoutManager);
        MomentsAccountBindingListAdapter momentsAccountBindingAdapter =
                new MomentsAccountBindingListAdapter(bindingList);
        momentsNearbyView.setAdapter(momentsAccountBindingAdapter);
    }

    private void outfitMomentsNearbyView() {
        UserLocation location = requestSyncLocation();
        List<MomentsAccountBinding> momentsAccountBindings = requestMomentsNearby(location);
        renderMomentsNearby(momentsAccountBindings);
    }

    private void outfitToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
