package com.auvx.melloo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.auvx.melloo.R;
import com.auvx.melloo.constant.HttpAttributeName;
import com.auvx.melloo.constant.field.AccountField;
import com.auvx.melloo.constant.field.AccountFieldValue;
import com.auvx.melloo.context.Melloo;
import com.auvx.melloo.util.HttpOperator;

public class VisitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit);
        Intent intent = getIntent();
        Long accountId = intent.getLongExtra(
                AccountField.ACCOUNT_ID, AccountFieldValue.ACCOUNT_ID_ABSENT);

        Button contactButton = findViewById(R.id.contact);
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = Melloo.getSharedPreferences();
                String onlineClue = pref.getString(HttpAttributeName.Extend.ONLINE_CLUE, null);

                //TODO 请求服务器对方是否在线

                if (onlineClue == null || 对方不在线) {
                    // TODO 如果自己不在线， 或者对方不在线 按钮 灰色
                    return;
                } else {
                    //TODO establishSession(对方);
                    //TODO 切换活动到 chatRoom
                }
            }
        });

        Button momentsDirectButton = findViewById(R.id.moments);
        momentsDirectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    //TODO
    private void requestAccountInfo(Long accountId) {
        HttpOperator.buildJsonEncodePostRequest(null, null);
    }

    private void requestMoments(Long accountid) {
        HttpOperator.buildJsonEncodePostRequest(null, null);
    }

    private void
}
