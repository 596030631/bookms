package com.stbu.bookms.util.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * @version 1.0
 * @className DBManager
 * @description TODO 数据库管理类
 */
public class DBManager {
    public static DataBaseHelper dataBaseHelper;

    /**
     * 返回一个可写的数据库
     *
     * @param context 上下文
     * @return SQLiteDatabase
     */
    public static SQLiteDatabase getSqliteWritableDatabase(Context context) {
        if (dataBaseHelper == null) {
            dataBaseHelper = DataBaseHelper.getInstance(context);
        }
        return dataBaseHelper.getWritableDatabase();
    }

    /**
     * 返回一个只读的数据库
     *
     * @param context 上下文
     * @return SQLiteDatabase
     */
    public static SQLiteDatabase getSqliteReadableDatabase(Context context) {
        if (dataBaseHelper == null) {
            dataBaseHelper = DataBaseHelper.getInstance(context);
        }
        return dataBaseHelper.getReadableDatabase();
    }
}
