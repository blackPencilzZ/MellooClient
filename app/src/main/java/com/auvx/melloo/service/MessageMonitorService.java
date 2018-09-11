package com.auvx.melloo.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.auvx.melloo.domain.MessagePiece;
import com.auvx.melloo.ui.adapter.MessageListAdapter;
import com.auvx.melloo.util.JsonOperator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.List;

public class MessageMonitorService extends IntentService {

    private final int MSG_CAP = 2 * 1024;

    //用以绑定ConversationActivity的msgList
    private List mMsgList = null;

    //用以表示是否需要做msgList的绑定
    private boolean mMsgBufOn = false;
    private Long chatingWithMeAccountId;
    private RecyclerView mMsgBoard;
    private MessageListAdapter mMsgListAdapter;

    private MessageBinder mBinder = new MessageBinder();

    MessageMonitorService() {
        super("MessageMonitor");
    }

    class MessageBinder extends Binder {
        public void enableMsgBuf(Long accountId,
                                 List<MessagePiece> msgList,
                                 MessageListAdapter msgListAdapter,
                                 RecyclerView msgView) {
            mMsgBufOn = true;
            mMsgListAdapter = msgListAdapter;
            chatingWithMeAccountId = accountId;
            mMsgBoard = msgView;
            if (mMsgList != null && mMsgList.size() > 0) {
                mMsgList.clear();
            }
            mMsgList = msgList;
        }
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        while (true) {
            InetAddress localhost = InetAddress.getLocalHost();
            InetSocketAddress addr = new InetSocketAddress(localhost, 9779);
            ServerSocketChannel msgLsnCh = ServerSocketChannel.open();
            ServerSocket boundSock = msgLsnCh.socket();
            boundSock.bind(addr);
            SocketChannel msgReadCh = msgLsnCh.accept();

            ByteBuffer byteBuf = ByteBuffer.allocate(MSG_CAP);
            msgReadCh.read(byteBuf);
            byteBuf.flip();
            CharBuffer msgBuf = byteBuf.asCharBuffer();
            String msgStr = msgBuf.toString();


            ObjectMapper jsonMapper = JsonOperator.getMapper();
            MessagePiece msgPiece = jsonMapper.readValue(msgStr, MessagePiece.class);
            msgPiece.setTransferState(MessagePiece.TransferState.RECEIVED.getValue());

            //TODO 判断是否是　正在聊天　而且　是和对应的人　这时就需要　将接收到的　消息　显示到屏

            if (mMsgBufOn) {
                boolean accountIdMatch =
                        msgPiece.getSenderAccountId().equals(chatingWithMeAccountId);
                if (accountIdMatch) {
                    mMsgList.add(msgPiece);
                    mMsgListAdapter.notifyItemInserted(mMsgList.size() - 1);
                    mMsgBoard.scrollToPosition(mMsgList.size() - 1);
                }
            }

            //TODO 获取当前的活动,检查Intent中 senderAccountId　是否和消息中的　一致
            //TODO 如果一致　需要　将接收到的　消息　显示到屏

            //TODO　存储到本地数据库

            msgReadCh.close();
            msgLsnCh.close();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
