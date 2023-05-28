package com.stbu.bookms.util.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.stbu.bookms.entity.Borrow;

import java.util.ArrayList;

/**
 * @version 1.0
 * @className BorrowDao
 * @description TODO 执行与互换相关的数据库操作
 */
public class BorrowDao {
    private Context context;

    public BorrowDao(Context context) {
        this.context = context;
    }

    /**
     * 互换的情况分析:
     * 管理员而言，可以删除用户的互换情况，可以查看互换情况。
     * 用户而言，可以借书和还书，这就需要增加，删除，查看
     * 所以，这里只设增加，删除，查看，不实现修改。
     */

    /**
     * 增加互换信息
     *
     * @param borrow 互换
     */
    public void addBorrowBookInfo(Borrow borrow) {
        SQLiteDatabase db = DBManager.getSqliteWritableDatabase(context);
        if (db.isOpen()) {
            //  "borrow_id varchar(30)," +
            //                        "buyer varchar(30)," +
            //                        "sale_name varchar(30)," +
            //                        "price varchar(30)," +
            //                        "datetime varchar(30)," +
            //                        "remake varchar(120)," +
            //                        "borrow_book_id varchar(30)," +
            //                        "borrow_book_name varchar(30)" +
            ContentValues values = new ContentValues();
            values.put("borrow_id", borrow.getBorrowId());
            values.put("address", borrow.getAddress());
            values.put("buyer", borrow.getBuyer_name());
            values.put("sale_name", borrow.getSale_name());
            values.put("price", borrow.getPrice());
            values.put("datetime", borrow.getDatetime());
            values.put("remake", borrow.getRemake());
            values.put("borrow_book_id", borrow.getBorrowBookId());
            values.put("borrow_book_name", borrow.getBorrowBookName());


            int rows = (int) db.insert(
                    "borrowInfo",
                    null,
                    values
            );
            if (rows == 0) {
                Log.d("添加借书信息操作", "添加借书信息失败");
            } else {
                Log.d("添加借书信息操作", "添加借书信息成功");
            }
            db.close();
        } else {
            Log.d("添加借书信息操作", "借书信息添加成功");
        }
    }

    /**
     * 删除互换信息
     *
     * @param borrow 互换
     */
    public void deleteBorrowBookInfo(Borrow borrow) {
        SQLiteDatabase db = DBManager.getSqliteWritableDatabase(context);
        if (db.isOpen()) {
            int rows = db.delete(
                    "borrowInfo",
                    "borrow_id = ? and borrow_book_id = ?",
                    new String[]{borrow.getBorrowId(), borrow.getBorrowBookId()}
            );
            if (rows == 0) {
                Log.d("删除借书信息操作", "删除借书信息失败！");
            } else {
                Log.d("删除借书信息操作", "删除借书信息成功！");
            }
            db.close();
        } else {
            Log.d("删除借书信息操作", "数据库打开失败");
        }
    }

    /**
     * 管理员查看所有人互换情况
     *
     * @return 将数据库中所有人的互换信息装入ArrayList返回。如果没有互换信息，则返回一个没有任何数据的ArrayList
     */
    public ArrayList<Borrow> showBorrowBookInfo() {
        ArrayList<Borrow> data = null;
        SQLiteDatabase db = DBManager.getSqliteReadableDatabase(context);
        if (db.isOpen()) {
            Cursor cursor = db.query(
                    "borrowInfo",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            data = new ArrayList<>();
            while (cursor.moveToNext()) {
                Borrow temp = new Borrow();
                temp.setBorrowId(cursor.getString(cursor.getColumnIndex("borrow_id")));
                temp.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                temp.setSale_name(cursor.getString(cursor.getColumnIndex("sale_name")));
                temp.setBuyer_name(cursor.getString(cursor.getColumnIndex("buyer")));
                temp.setDatetime(cursor.getString(cursor.getColumnIndex("datetime")));
                temp.setPrice(cursor.getString(cursor.getColumnIndex("price")));
                temp.setRemake(cursor.getString(cursor.getColumnIndex("remake")));
                temp.setBorrowBookId(cursor.getString(cursor.getColumnIndex("borrow_book_id")));
                temp.setBorrowBookName(cursor.getString(cursor.getColumnIndex("borrow_book_name")));
                data.add(temp);
            }
            cursor.close();
            db.close();
        }
        return data;
    }

    /**
     * 用户查看自己互换情况
     *
     * @return 将数据库中该用户的互换信息装入ArrayList返回。如果没有互换信息，则返回一个没有任何数据的ArrayList
     */
    public ArrayList<Borrow> showAllBorrowBookForUser(String borrowId) {
        ArrayList<Borrow> data = null;
        SQLiteDatabase db = DBManager.getSqliteReadableDatabase(context);
        if (db.isOpen()) {
            Cursor cursor = db.query(
                    "borrowInfo",
                    null,
                    "borrow_id = ?",
                    new String[]{borrowId},
                    null,
                    null,
                    null
            );
            data = new ArrayList<>();
            while (cursor.moveToNext()) {
                Borrow temp = new Borrow();
                temp.setBorrowId(cursor.getString(cursor.getColumnIndex("borrow_id")));
                temp.setBorrowBookId(cursor.getString(cursor.getColumnIndex("borrow_book_id")));
                temp.setBorrowBookName(cursor.getString(cursor.getColumnIndex("borrow_book_name")));
                data.add(temp);
            }
            cursor.close();
            db.close();
        }
        return data;
    }
}
