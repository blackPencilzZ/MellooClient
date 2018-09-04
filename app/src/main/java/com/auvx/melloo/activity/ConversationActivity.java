package com.auvx.melloo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.auvx.melloo.R;
import com.auvx.melloo.constant.MsgConst;
import com.auvx.melloo.constant.field.AccountField;
import com.auvx.melloo.constant.field.AccountFieldValue;
import com.auvx.melloo.domain.MessagePiece;
import com.auvx.melloo.ui.MessageListAdapter;
import com.auvx.melloo.util.HttpOperator;
import com.auvx.melloo.util.JsonOperator;
import com.auvx.melloo.util.StringInspector;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.List;

public class ConversationActivity extends AppCompatActivity {

    List<MessagePiece> msgList;
    MessagePiece msgDraft;

    RecyclerView msgBoard;
    EditText msgEdit;

    MessageListAdapter msgListAdapter;

    SocketChannel msgCh;

    String MSG_ENCODE_CHARSET_NAME = "UTF-8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        // TODO 从Intent中取出 accountId
        Intent intent = getIntent();
        long targetAccountId = intent.getLongExtra(AccountField.ACCOUNT_ID,
                AccountFieldValue.ACCOUNT_ID_ABSENT);

        // TODO　绑定msgList
        Intent intentForMsg = new Intent();
        intentForMsg.putExtra(AccountField.ACCOUNT_ID, targetAccountId);
        intentForMsg.putExtra(MsgConst.MSG_LIST_ADAPTER, msgListAdapter);

        // TODO 请求服务器对方的 IP
        HttpOperator.buildJsonEncodePostRequest("", targetAccountId);


        // TODO　使用Socket与对方建立TCP 或者 UDP 连接
        InetAddress remote = InetAddress.getByName("www.melloo.antipixel.com");
        InetSocketAddress msgSock = new InetSocketAddress(remote, 9779);
        msgCh = SocketChannel.open(msgSock);

        // TODO 没有异常 ，accountId作为条件查询本地数据库

        // TODO 初始化msgList, 绑定RecyclerView

        // TODO 绑定 send_confirm 浮动按钮单击事件
        FloatingActionButton sendConfirmBtn = findViewById(R.id.send_confirm);
        EditText msgEdit = findViewById(R.id.msg_edit);
        RecyclerView msgBoard = findViewById(R.id.msg_board);

        if (StringInspector.isBlank(msgEdit.getText())) {
            sendConfirmBtn.hide();
        }

        msgEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (StringInspector.isBlank(msgEdit.getText()) && sendConfirmBtn.isShown()) {
                    sendConfirmBtn.hide();
                } else if (StringInspector.hasText(msgEdit.getText()) && !sendConfirmBtn.isShown()) {
                    sendConfirmBtn.show();
                }
                return false;
            }
        });

        sendConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msgText = msgEdit.getText().toString();
                MessagePiece msg = new MessagePiece();
                msg.setSenderAccountId();
                msg.setTransferState(MessagePiece.TransferState.SENT.getValue());
                msg.setContentMediaType(MessagePiece.ContentMediaType.TEXT.getValue());
                msg.setContentRef(msgText);
                msg.setCreateTime();
                msgList.add(msg);

                msgListAdapter.notifyItemInserted(msgList.size() - 1);
                msgBoard.scrollToPosition(msgList.size() - 1);

                //TODO 数据库插入

                //TODO 发送
                ObjectMapper jacksonMapper = JsonOperator.getMapper();
                String msgStr = jacksonMapper.writeValueAsString(msg);
                Charset msgEncodeCharset = Charset.forName(MSG_ENCODE_CHARSET_NAME);
                byte[] msgBytes = msgStr.getBytes(msgEncodeCharset);
                ByteBuffer msgBuf = ByteBuffer.wrap(msgBytes);

                msgCh.write(msgBuf);

                msgEdit.setText("");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
