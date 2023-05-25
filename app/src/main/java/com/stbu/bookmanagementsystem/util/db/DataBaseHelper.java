package com.stbu.bookmanagementsystem.util.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * @version 1.0
 * @className DataBaseHelper
 * @description TODO 操作数据库
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private final String TAG = DataBaseHelper.class.getName();
    public static DataBaseHelper dataBaseHelper;

    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 获取数据库的唯一实例
     *
     * @param context 上下文
     * @return 数据库实例
     */
    public static DataBaseHelper getInstance(Context context) {
        if (dataBaseHelper == null) {
            dataBaseHelper = new DataBaseHelper(context, "bms.db", null, 1);
        }
        return dataBaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建用户信息表
        String userInfo =
                "CREATE TABLE IF NOT EXISTS userInfo(" +
                        "id varchar(30)PRIMARY KEY," +
                        "name varchar(30)," +
                        "class_name varchar(30)," +
                        "password varchar(30)," +
                        "phone_number varchar(30)" +
                        ")";
        // 创建图书信息表
        String bookInfo =
                "CREATE TABLE IF NOT EXISTS bookInfo(" +
                        "book_id varchar(30)PRIMARY KEY," +
                        "book_name varchar(30)," +
                        "book_number int" +
                        ")";
        // 创建借阅信息表
        String borrowInfo =
                "CREATE TABLE IF NOT EXISTS borrowInfo(" +
                        "borrow_id varchar(30)," +
                        "borrow_book_id varchar(30)," +
                        "borrow_book_name varchar(30)" +
                        ")";

        db.execSQL(userInfo);
        db.execSQL(borrowInfo);
        db.execSQL(bookInfo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "数据库及表创建成功");
    }
}
