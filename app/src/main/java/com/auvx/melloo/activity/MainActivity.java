package com.auvx.melloo.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.auvx.melloo.R;
import com.auvx.melloo.constant.AppLocalDataDir;
import com.auvx.melloo.fragment.MomentsMineFragment;
import com.auvx.melloo.fragment.MomentsNearbyFragment;
import com.baidu.location.LocationClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //如果作为一个需要共享的变量的话，那就有点麻烦了
    public static String online_clue = null;
    public LocationClient mLocationClient;

    //witchcraft 数据
    private float homeX;
    private float homeY;

    private float lastX;
    private float lastY;

    private boolean dragable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO 更换Fragment
        replaceFragment(new MomentsNearbyFragment());

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

        // TODO 1.查询本地网络是否可用 2.online_clue是否不为空  后台创建接受消息请求
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

        //TODO 给右侧两个按钮绑定事件
        FloatingActionButton momentsNearbyLink = findViewById(R.id.moments_nearby_link_button);
        FloatingActionButton momentsMineLink = findViewById(R.id.moments_mine_link_button);
        momentsNearbyLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new MomentsNearbyFragment());
            }
        });
        momentsMineLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new MomentsMineFragment());
            }
        });

        View witchcraft = findViewById(R.id.witchcraft);
        homeX = witchcraft.getX();
        homeY = witchcraft.getY();
        witchcraft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                float rawX = motionEvent.getRawX();
                float rawY = motionEvent.getRawY();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = rawX;
                        lastY = rawY;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float dx = rawX - lastX;
                        float dy = rawY - lastY;
                        float x = view.getX();
                        float y = view.getY();

                        view.setX(x);
                        view.setY(y);

                        lastX = rawX;
                        lastY = rawY;
                        break;
                    case MotionEvent.ACTION_UP:

                        float wcTransferX = rawX - homeX;
                        float wcTransferY = rawY - homeY;
                        if (wcTransferY > 100 && Math.abs(wcTransferX) < 10) {

                            //TODO 显示编辑一条moment

                        } else if (wcTransferX < -100 && Math.abs(wcTransferY) < 10) {

                            //TODO 显示拍摄一条moment

                        } else if (wcTransferX > 100 && Math.abs(wcTransferY) < 10) {

                            //TODO 显示录制一条moment

                        } else {
                            // do nothing
                        }
                        view.setX(homeX);
                        view.setY(homeY);
                        break;
                }
                return false;
            }
        });


        Button pickPics = findViewById(R.id.pick_picture);
        pickPics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.
                        PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {

            case PICK_PICTURE:
                if (resultCode == RESULT_OK) {
                    handleImageOnKitKat(data);
                }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {

        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse(
                        "content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }

        displayImage(imagePath);
    }
    public static final int PICK_PICTURE = 2;

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PICTURE);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver()
                .query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close();
        }

        return path;
    }


    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        } else {

        }
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
                    Toast.makeText(this, "Unknow Error", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.moments_layout, fragment);
        return;
    }

    private void outfitToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
