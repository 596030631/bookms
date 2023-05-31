package com.stbu.bookms.util.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.stbu.bookms.entity.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @className BookDao
 * @description TODO 执行与图书相关的数据库操作
 */
public class BookDao {
    private Context context;

    public BookDao(Context context) {
        this.context = context;
    }

    /**
     * 增加图书信息
     *
     * @param book 图书
     */
    public void addBookInfo(Book book) {
        SQLiteDatabase db = DBManager.getSqliteWritableDatabase(context);
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put("book_id", book.getBookId());
            values.put("book_name", book.getBookName());
            values.put("image", book.getImage());
            values.put("book_number", book.getBookNumber());
            values.put("book_auth", book.getBookAuth());
            values.put("book_own", book.getBookOwn());
            values.put("book_category", book.getBookCategory());
            values.put("book_content", book.getBookContent());
            values.put("book_price", book.getPrice());




            int rows = (int) db.insert(
                    "bookInfo",
                    null,
                    values
            );
            if (rows == 0) {
                Log.d("添加图书操作", "添加图书失败");
                Toast.makeText(this.context, "添加图书失败", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("添加图书操作", "添加图书成功");
                Toast.makeText(this.context, "添加图书成功", Toast.LENGTH_SHORT).show();
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
     * 更新图书信息
     *
     * @param book 图书
     */
    public void updateBookInfo(Book book) {
        SQLiteDatabase db = DBManager.getSqliteWritableDatabase(context);
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put("book_id", book.getBookId());
            values.put("book_name", book.getBookName());
            values.put("book_number", book.getBookNumber());
            values.put("remake", book.getRemakeJson());
            int rows = db.update(
                    "bookInfo",
                    values,
                    "book_id = ?",
                    new String[]{book.getBookId()}
            );
            if (rows == 0) {
                Log.d("更新图书操作", "更新图书失败！");
            } else {
                Log.d("更新图书操作", "更新图书成功！");
            }
            db.close();
        } else {
            Log.d("更新图书操作", "数据库打开失败！");
        }
    }

    /**
     * 查看所有图书信息
     *
     * @return 将数据库中所有的图书信息装入ArrayList返回。如果没有图书信息，则返回一个没有任何数据的ArrayList
     */
    public ArrayList<Book> showBookInfo() {
        ArrayList<Book> data = null;
        SQLiteDatabase db = DBManager.getSqliteReadableDatabase(context);
        if (db.isOpen()) {
            Cursor cursor = db.query(
                    "bookInfo",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            data = new ArrayList<>();
            while (cursor.moveToNext()) {//跳转下一条记录
                Book temp = new Book();

                temp.setBookId(cursor.getString(cursor.getColumnIndex("book_id")));
                temp.setBookName(cursor.getString(cursor.getColumnIndex("book_name")));
                temp.setImage(cursor.getString(cursor.getColumnIndex("image")));
                temp.setBookAuth(cursor.getString(cursor.getColumnIndex("book_auth")));
                temp.setBookOwn(cursor.getString(cursor.getColumnIndex("book_own")));
                temp.setBookCategory(cursor.getString(cursor.getColumnIndex("book_category")));
                temp.setBookContent(cursor.getString(cursor.getColumnIndex("book_content")));
                temp.setPrice(cursor.getString(cursor.getColumnIndex("book_price")));
                temp.setBookNumber(cursor.getInt(cursor.getColumnIndex("book_number")));
                temp.setRemakeJson(cursor.getString(cursor.getColumnIndex("remake")));
                data.add(temp);
            }
            cursor.close();
            db.close();
        }
        return data;
    }
 /**
     * 查看所有图书信息
     *
     * @return 将数据库中所有的图书信息装入ArrayList返回。如果没有图书信息，则返回一个没有任何数据的ArrayList
     */
    public ArrayList<Book> showTop10BookInfo() {
        ArrayList<Book> data = null;
        SQLiteDatabase db = DBManager.getSqliteReadableDatabase(context);
        if (db.isOpen()) {
            Cursor cursor = db.query(//Cursor用于封装sql语句
                    "bookInfo",
                    null,
                    null,
                    null,
                    null,
                    null,
                    "book_price",
                    null
            );
            data = new ArrayList<>();
            while (cursor.moveToNext()) {
                Book temp = new Book();

                temp.setBookId(cursor.getString(cursor.getColumnIndex("book_id")));
                temp.setBookName(cursor.getString(cursor.getColumnIndex("book_name")));
                temp.setImage(cursor.getString(cursor.getColumnIndex("image")));
                temp.setBookAuth(cursor.getString(cursor.getColumnIndex("book_auth")));
                temp.setBookOwn(cursor.getString(cursor.getColumnIndex("book_own")));
                temp.setBookCategory(cursor.getString(cursor.getColumnIndex("book_category")));
                temp.setBookContent(cursor.getString(cursor.getColumnIndex("book_content")));
                temp.setPrice(cursor.getString(cursor.getColumnIndex("book_price")));
                temp.setBookNumber(cursor.getInt(cursor.getColumnIndex("book_number")));
                data.add(temp);
            }
            cursor.close();
            db.close();
        }
        return data;
    }

    /**
     * 通过bookId查找图书
     *
     * @return 如果数据库中有该bookId的图书，则将用户信息存入book对象返回；否则返回一个不含任何图书信息的book对象；
     */
    public boolean checkBookExist(Book book) {
        boolean bool = false;
        if (findBookId(book).getBookId() != null) {
            bool = true;
        }
        return bool;
    }

    /**
     * 添加图书时，通过bookId验证图书是否存在
     *
     * @param book 图书
     * @return 如果数据库中有图书，则将图书信息存入book对象返回；否则返回一个不含任何图书信息的book对象；
     */
    public Book findBookId(Book book) {
        SQLiteDatabase db = DBManager.getSqliteReadableDatabase(context);
        Book tempBook = null;
        if (db.isOpen()) {
            String bookId = book.getBookId();//先获取传入的图书的bookid
            Cursor cursor = db.query(
                    "bookInfo",
                    null,
                    "book_id = ?",
                    new String[]{bookId},
                    null,
                    null,
                    null
            );
            tempBook = new Book();
            while (cursor.moveToNext()) {
                tempBook.setBookId(cursor.getString(cursor.getColumnIndex("book_id")));
            }
            cursor.close();
            db.close();
        }
        return tempBook;
    }


    /**
     * 搜索图书功能
     *
     * @param bookName 图书
     * @return 返回图书集
     */
    public List<Book> findBookByName(String bookName) {
        SQLiteDatabase db = DBManager.getSqliteReadableDatabase(context);
        ArrayList<Book> bookList = new ArrayList<Book>();

        String sql = "SELECT * FROM bookInfo WHERE book_name LIKE '%" + bookName + "%'";
        if (db.isOpen()) {
            Cursor csearch = db.rawQuery(sql, null);//将类对象返回到csearch
            while (csearch.moveToNext()) {
                Book tempBook = new Book();

                tempBook.setBookId(csearch.getString(csearch.getColumnIndex("book_id")));
                tempBook.setBookName(csearch.getString(csearch.getColumnIndex("book_name")));
                tempBook.setImage(csearch.getString(csearch.getColumnIndex("image")));
                tempBook.setBookAuth(csearch.getString(csearch.getColumnIndex("book_auth")));
                tempBook.setBookOwn(csearch.getString(csearch.getColumnIndex("book_own")));
                tempBook.setBookCategory(csearch.getString(csearch.getColumnIndex("book_category")));
                tempBook.setPrice(csearch.getString(csearch.getColumnIndex("book_price")));
                tempBook.setBookContent(csearch.getString(csearch.getColumnIndex("book_content")));
                tempBook.setBookNumber(csearch.getInt(csearch.getColumnIndex("book_number")));
                tempBook.setRemakeJson(csearch.getString(csearch.getColumnIndex("remake")));
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
     * @return 返回图书集
     */
    public List<Book> findBookByCategory(String category) {
        SQLiteDatabase db = DBManager.getSqliteReadableDatabase(context);
        ArrayList<Book> bookList = new ArrayList<Book>();

        String sql = "SELECT * FROM bookInfo WHERE book_category LIKE '%" + category + "%'";
        if (db.isOpen()) {
            Cursor csearch = db.rawQuery(sql, null);
            while (csearch.moveToNext()) {
                Book tempBook = new Book();

                tempBook.setBookId(csearch.getString(csearch.getColumnIndex("book_id")));
                tempBook.setBookName(csearch.getString(csearch.getColumnIndex("book_name")));
                tempBook.setImage(csearch.getString(csearch.getColumnIndex("image")));
                tempBook.setBookAuth(csearch.getString(csearch.getColumnIndex("book_auth")));
                tempBook.setBookCategory(csearch.getString(csearch.getColumnIndex("book_category")));
                tempBook.setPrice(csearch.getString(csearch.getColumnIndex("book_price")));
                tempBook.setBookContent(csearch.getString(csearch.getColumnIndex("book_content")));
                tempBook.setBookNumber(csearch.getInt(csearch.getColumnIndex("book_number")));
                bookList.add(tempBook);
            }
            csearch.close();
            db.close();
        }
        return bookList;
    }


    /**
     * 还书时，更新图书的数量（+1）
     *
     * @param bookId 图书id
     * @return 将更新的图书信息查询并返回
     */
    public Book returnBookNumberChange(String bookId) {
        SQLiteDatabase db = DBManager.getSqliteReadableDatabase(context);
        Book book = null;
        if (db.isOpen()) {
            Cursor cursor = db.query(
                    "bookInfo",
                    null,
                    "book_id = ?",
                    new String[]{bookId},
                    null,
                    null,
                    null
            );
            if (cursor.moveToNext()) {
                book = new Book();
                book.setBookId(cursor.getString(cursor.getColumnIndex("book_id")));
                book.setBookName(cursor.getString(cursor.getColumnIndex("book_name")));
                book.setImage(cursor.getString(cursor.getColumnIndex("image")));
                book.setBookNumber(cursor.getInt(cursor.getColumnIndex("book_number")) + 1);
            }
            cursor.close();
            db.close();
        }
        return book;
    }

    //这里对借的书籍数量进行操作

    /**
     * 借书时，更新图书的数量（-1）
     *
     * @param bookId 图书id
     * @return 将更新的图书信息查询并返回
     */
    public Book borrowBookNumberChange(String bookId) {
        SQLiteDatabase db = DBManager.getSqliteReadableDatabase(context);
        Book book = null;
        if (db.isOpen()) {
            book = new Book();
            Cursor cursor = db.query(
                    "bookInfo",
                    null,
                    "book_id = ?",
                    new String[]{bookId},
                    null,
                    null,
                    null
            );
            if (cursor.moveToNext()) {
                book.setBookId(cursor.getString(cursor.getColumnIndex("book_id")));
                book.setBookName(cursor.getString(cursor.getColumnIndex("book_name")));
                book.setImage(cursor.getString(cursor.getColumnIndex("image")));
                book.setBookNumber(cursor.getInt(cursor.getColumnIndex("book_number")) - 1);
            }
        }
        return book;
    }

    /**
     * 更新互换后的图书信息
     *
     * @param book 图书
     */
    public void updateBorrowBookInfo(Book book) {
        SQLiteDatabase db = DBManager.getSqliteWritableDatabase(context);
        if (db.isOpen()) {
            ContentValues values = new ContentValues();//ContentValues常常用于插入或更新数据到SQLite数据库中，因为插入或更新数据时需要将数据封装成ContentValues对象
            values.put("book_id", book.getBookId());
            values.put("book_name", book.getBookName());
            values.put("book_number", book.getBookNumber());
            int rows = db.update(
                    "bookInfo",
                    values,
                    "book_id = ?",
                    new String[]{book.getBookId()}
            );
            if (rows == 0) {
                Log.d("更新图书互换信息操作", "更新图书互换信息失败！");
            } else {
                Log.d("更新图书互换信息操作", "更新图书互换信息成功！");
            }
        } else {
            Log.d("更新图书互换信息操作", "数据库打开失败！");
        }
    }
}
