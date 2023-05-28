package com.stbu.bookms.util.db;

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

    private static final String userInfo =
            "CREATE TABLE IF NOT EXISTS userInfo(" +
                    "id varchar(30)PRIMARY KEY," +
                    "name varchar(30)," +
                    "address varchar(30)," +
                    "password varchar(30)," +
                    "phone_number varchar(30)," +
                    "amount varchar(30)" +
                    ")";

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
            dataBaseHelper = new DataBaseHelper(context, "bms.db", null, 2);
        }
        return dataBaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建用户信息表

        // 创建图书信息表
        //   private String bookId; // 图书编号
        //    private String bookName; // 图书名称
        //    private String bookAuth; // 图书左右着
        //    private String bookCategory; // 图书类别
        //    private String bookContent; // 图书介绍
        //    private int bookNumber; // 图书数量
        //    private String price = "0.00"; // 图书价格
        String bookInfo =
                "CREATE TABLE IF NOT EXISTS bookInfo(" +
                        "book_id varchar(30)PRIMARY KEY," +
                        "image varchar(90)," +
                        "book_name varchar(30)," +
                        "book_auth varchar(30)," +
                        "book_category varchar(30)," +
                        "book_content varchar(90)," +
                        "book_price varchar(30)," +
                        "book_number int," +
                        "remake varchar(120)" +
                        ")";
        // 创建互换信息表

        //  "buyer varchar(30)," +
        //                        "sale_name varchar(30)," +
        //                        "address varchar(30)," +
        //                        "price varchar(30)," +
        //                        "book varchar(30)" +

        String borrowInfo =
                "CREATE TABLE IF NOT EXISTS borrowInfo(" +
                        "borrow_id varchar(30)," +
                        "buyer varchar(30)," +
                        "sale_name varchar(30)," +
                        "price varchar(30)," +
                        "datetime varchar(30)," +
                        "address varchar(30)," +
                        "remake varchar(120)," +
                        "borrow_book_id varchar(30)," +
                        "borrow_book_name varchar(30)" +
                        ")";

        // 创建用户交流
        String exchangeInfo =
                "CREATE TABLE IF NOT EXISTS exchangeInfo(" +
                        "loginUser varchar(30)," +
                        "chatUser varchar(30)," +
                        "message varchar(30)," +
                        "time varchar(30)" +
                        ")";


        db.execSQL(userInfo);
        db.execSQL(borrowInfo);
        db.execSQL(bookInfo);
        db.execSQL(exchangeInfo);
//        db.execSQL(changeInfo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "数据库及表创建成功");
        if (oldVersion == 1 && newVersion == 2) {
            ;
            db.execSQL("alter table  userInfo add amount varchar(30)");
        }
    }
}
