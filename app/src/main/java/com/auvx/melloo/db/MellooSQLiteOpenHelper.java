package com.auvx.melloo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MellooSQLiteOpenHelper extends SQLiteOpenHelper {

    //麻痹的，没有账号密码怎么做自动登录呀
    public static final String SQL_CREATE_ACCOUNT = "CREATE TABLE account ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "phone TEXT,"
            + "virtual_name TEXT,"
            + "virtual_birthday TEXT,"
            + "secret_sign TEXT,"
            + "online_clue TEXT,"
            + "keep_online INTEGER,"//keep on line or auto offline
            + "jpush_alias TEXT,"
            + "device TEXT)";

    public static final String SQL_CREATE_CHAT = "CREATE TABLE chat ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "sender_account_id INTEGER,"
            + "created_by_me INTEGER,"
            + "content_media_type INTEGER,"
            + "content_ref TEXT,"
            + "transfer_state INTEGER,"
            + "create_time TEXT,"
            + "uuid TEXT"
            + ")";


    private Context mContext;

    public MellooSQLiteOpenHelper(Context context, String dbName,
                                  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbName, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ACCOUNT);
        db.execSQL(SQL_CREATE_CHAT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersioni) {

    }
}
