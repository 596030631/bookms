package com.stbu.bookms.util.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.stbu.bookms.entity.Book;
import com.stbu.bookms.entity.ChangeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @className BookDao
 * @description TODO 执行与图书相关的数据库操作
 */
public class ChangeDao {
    private Context context;

    public ChangeDao(Context context) {
        this.context = context;
    }

    /**
     * 增加图书信息
     *
     * @param book 图书
     */
    public void addChangeInfo(ChangeInfo book) {
        SQLiteDatabase db = DBManager.getSqliteWritableDatabase(context);
        if (db.isOpen()) {
            //   "buyer varchar(30)," +
            //                        "address varchar(30)," +
            //                        "price varchar(30)," +
            //                        "book varchar(30)" +

            ContentValues values = new ContentValues();
            values.put("buyer", book.buyerName);
            values.put("sale_name", book.sale_name);
            values.put("address", book.address);
            values.put("book", book.bookName);
            values.put("price", book.price);

            int rows = (int) db.insert(
                    "changeInfo",
                    null,
                    values
            );
            if (rows == 0) {
                Log.d("添加图书操作", "添加图书失败");
                Toast.makeText(this.context, "添加记录失败", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("添加图书操作", "添加图书成功");
                Toast.makeText(this.context, "添加记录成功", Toast.LENGTH_SHORT).show();
            }
            db.close();
        } else {
            Log.d("添加图书操作", "数据库打开失败！");
        }
    }

    /**
     * 删除图书信息
     *
     * @param book 图书
     */
    public void deleteBookInfo(Book book) {
        SQLiteDatabase db = DBManager.getSqliteWritableDatabase(context);
        if (db.isOpen()) {
            String bookId = book.getBookId();
            int rows = db.delete(
                    "bookInfo",
                    "book_id = ?",
                    new String[]{bookId}
            );
            if (rows == 0) {
                Log.d("删除图书操作", "删除图书失败！");
                Toast.makeText(this.context, "删除图书失败", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("删除图书操作", "删除图书成功！");
                Toast.makeText(this.context, "删除图书成功", Toast.LENGTH_SHORT).show();
            }
            db.close();
        } else {
            Log.d("删除图书操作", "数据库打开失败！");
        }
    }

    /**
     * 查看所有图书信息
     *
     * @return 将数据库中所有的图书信息装入ArrayList返回。如果没有图书信息，则返回一个没有任何数据的ArrayList
     */
    public ArrayList<ChangeInfo> queryChangeInfo() {
        ArrayList<ChangeInfo> data = null;
        SQLiteDatabase db = DBManager.getSqliteReadableDatabase(context);
        if (db.isOpen()) {
            Cursor cursor = db.query(
                    "changeInfo",
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
                ChangeInfo temp = new ChangeInfo();
                temp.bookName = (cursor.getString(cursor.getColumnIndex("book")));
                temp.buyerName = (cursor.getString(cursor.getColumnIndex("buyer")));
                temp.sale_name = (cursor.getString(cursor.getColumnIndex("sale_name")));
                temp.price = (cursor.getString(cursor.getColumnIndex("price")));
                temp.address = (cursor.getString(cursor.getColumnIndex("address")));
                data.add(temp);
            }
            cursor.close();
            db.close();
        }
        return data;
    }

}
