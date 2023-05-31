package com.stbu.bookms.util.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.stbu.bookms.entity.Book;
import com.stbu.bookms.entity.ExchangeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @className 用户交流
 * @description
 */
public class ExchangeDao {
    private Context context;

    public ExchangeDao(Context context) {
        this.context = context;
    }

    /**
     * 增加图书信息
     *
     * @param book 图书
     */
    public String addExchangeInfo(ExchangeInfo book) {
        String result = "失败";
        SQLiteDatabase db = DBManager.getSqliteWritableDatabase(context);
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put("loginUser", book.loginUser);
            values.put("chatUser", book.chatUser);
            values.put("message", book.message);
            values.put("time", book.time);
            int rows = (int) db.insert(
                    "exchangeInfo",
                    null,
                    values
            );
            if (rows == 0) {
                Log.d("添加图书操作", "添加留言失败");
                result = "数据库插入失败";
            } else {
                Log.d("添加图书操作", "留言成功");
                result = "留言成功";
            }
            db.close();
        } else {
            Log.d("留言成功", "数据库打开失败！");
            result = "数据库异常";
        }
        return result;
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
                    "userInfo",
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
     * 所有记录查询，用不到
     *
     * @return 返回图书集
     */
    public List<ExchangeInfo> queryChatMessage() {
        SQLiteDatabase db = DBManager.getSqliteReadableDatabase(context);
        List<ExchangeInfo> bookList = new ArrayList<>();

        String sql = "SELECT * FROM exchangeInfo ORDER BY time limit 100";//按时间排序，只展示前100条
        if (db.isOpen()) {
            Cursor csearch = db.rawQuery(sql, null);
            while (csearch.moveToNext()) {
                ExchangeInfo tempBook = new ExchangeInfo();
                tempBook.loginUser = (csearch.getString(csearch.getColumnIndex("loginUser")));
                tempBook.chatUser = (csearch.getString(csearch.getColumnIndex("chatUser")));
                tempBook.message = (csearch.getString(csearch.getColumnIndex("message")));
                tempBook.time = (csearch.getString(csearch.getColumnIndex("time")));
                bookList.add(tempBook);
            }
            csearch.close();
            db.close();
        }
        return bookList;
    }

    /**
     * 查询两个人的聊天记录
     */
    public List<ExchangeInfo> queryChatMessage(String... user) {
        SQLiteDatabase db = DBManager.getSqliteReadableDatabase(context);
        List<ExchangeInfo> bookList = new ArrayList<>();

        StringBuilder users = new StringBuilder("'"+user[0]);
        for (int i = 1; i < user.length; i++) {
            users.append("','").append(user[i]).append("'");
        }// 将接收到的多个用户的用户名拼接成一个字符串，用于后面的查询条件

        String sql = String.format("SELECT * FROM exchangeInfo WHERE loginUser in(%s) and chatUser in(%s) ORDER BY time limit 1000", users, users);
        Log.d("tag", "sql=" + sql);//拼接查询语句，用String.format()代替直接字符串拼接
        if (db.isOpen()) {
            Cursor csearch = db.rawQuery(sql, null);
            while (csearch.moveToNext()) {
                ExchangeInfo tempBook = new ExchangeInfo();
                tempBook.loginUser = (csearch.getString(csearch.getColumnIndex("loginUser")));
                tempBook.chatUser = (csearch.getString(csearch.getColumnIndex("chatUser")));
                tempBook.message = (csearch.getString(csearch.getColumnIndex("message")));
                tempBook.time = (csearch.getString(csearch.getColumnIndex("time")));
                bookList.add(tempBook);
            }
            csearch.close();
            db.close();
        }
        return bookList;
    }

    /**
     * 搜索图书功能
     *
     * @return 查询所有的用户列表，只要创建了都是你的好友，没做添加好友之类的
     */
    public List<ExchangeInfo> queryFriend() {
        SQLiteDatabase db = DBManager.getSqliteReadableDatabase(context);
        List<ExchangeInfo> bookList = new ArrayList<>();

        String sql = "SELECT * FROM exchangeInfo GROUP BY loginUser limit 100";//按章用户id继续宁分组
        if (db.isOpen()) {
            Cursor csearch = db.rawQuery(sql, null);
            while (csearch.moveToNext()) {
                ExchangeInfo tempBook = new ExchangeInfo();
                tempBook.loginUser = (csearch.getString(csearch.getColumnIndex("loginUser")));
                tempBook.chatUser = (csearch.getString(csearch.getColumnIndex("chatUser")));
                tempBook.message = (csearch.getString(csearch.getColumnIndex("message")));
                tempBook.time = (csearch.getString(csearch.getColumnIndex("time")));
                bookList.add(tempBook);
            }
            csearch.close();
            db.close();
        }
        return bookList;
    }


}
