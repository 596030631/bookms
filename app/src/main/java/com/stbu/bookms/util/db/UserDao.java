package com.stbu.bookms.util.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.stbu.bookms.entity.User;

import java.util.ArrayList;

/**
 * @version 1.0
 * @className UserDao
 * @description TODO 执行与用户相关的数据库操作
 */
public class UserDao {
    private Context context;

    public UserDao(Context context) {
        this.context = context;
    }

    /**
     * 增加用户信息
     *
     * @param user 用户
     */
    public void addUserInfo(User user) {
        SQLiteDatabase db = DBManager.getSqliteWritableDatabase(context);
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put("id", user.getId());
            values.put("name", user.getName());
            values.put("class_name", user.getAddress());
            values.put("password", user.getPassword());
            values.put("phone_number", user.getPhoneNumber());
            values.put("amount", user.getAmount());
            int rows = (int) db.insert(
                    "userInfo",
                    null,
                    values
            );
            if (rows == 0) {
                Log.d("添加用户操作", "添加用户失败");
            } else {
                Log.d("添加用户操作", "添加用户成功");
            }
            db.close();
        } else {
            Log.d("添加用户操作", "数据库打开失败！");
        }
    }

    /**
     * 删除用户信息
     *
     * @param user 用户
     */
    public void deleteUserInfo(User user) {
        SQLiteDatabase db = DBManager.getSqliteWritableDatabase(context);
        if (db.isOpen()) {
            int rows = db.delete(
                    "userInfo",
                    "id = ?",
                    new String[]{user.getId()}
            );
            if (rows == 0) {
                Log.d("删除用户操作", "删除用户失败");
            } else {
                Log.d("删除用户操作", "删除用户成功");
            }
            db.close();
        } else {
            Log.d("删除用户操作", "数据库打开失败");
        }
    }

    /**
     * 更新用户信息
     *
     * @param user 用户
     */
    public void updateUserInfo(User user) {
        SQLiteDatabase db = DBManager.getSqliteWritableDatabase(context);
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put("id", user.getId());
            values.put("name", user.getName());
            values.put("class_name", user.getAddress());
            values.put("password", user.getPassword());
            values.put("phone_number", user.getPhoneNumber());
            values.put("amount", user.getAmount());
            int rows = db.update(
                    "userInfo",
                    values,
                    "id = ?",
                    new String[]{user.getId()}
            );
            if (rows == 0) {
                Log.d("更新用户操作", "更新用户失败");
            } else {
                Log.d("更新用户操作", "更新用户成功");
            }
            db.close();
        } else {
            Log.d("更新用户信息操作", "数据库打开失败");
        }
    }

    /**
     * 查看所有用户信息
     */
    public ArrayList<User> showUserInfo() {
        ArrayList<User> data = null;
        SQLiteDatabase db = DBManager.getSqliteReadableDatabase(context);
        if (db.isOpen()) {
            Cursor cursor = db.query(
                    "userInfo",
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
                User temp = new User();
                temp.setId(cursor.getString(cursor.getColumnIndex("id")));
                temp.setName(cursor.getString(cursor.getColumnIndex("name")));
                temp.setAddress(cursor.getString(cursor.getColumnIndex("class_name")));
                temp.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                temp.setPhoneNumber(cursor.getString(cursor.getColumnIndex("phone_number")));
                temp.setAmount(cursor.getString(cursor.getColumnIndex("amount")));
                data.add(temp);
            }
            cursor.close(); // 关闭 cursor
            db.close();
        }
        return data;
    }

    /**
     * 增删改查之后，考虑到增删改查可能需要的方法，
     * 比如:
     * 1.增加用户要看看是否已存在，
     * 2.删除用户和修改用户都需要存在该用户
     */

    /**
     * 增加用户时，判断是否已存在
     *
     * @param user 用户
     * @return 返回ture，则代表用户存在；否则不存在
     */
    public boolean checkExist(User user) {
        boolean bool = false;
        if (findId(user).getId() != null) {
            bool = true;
        }
        return bool;
    }

    /**
     * 通过用户id查找用户
     *
     * @param user 用户
     * @return 如果数据库中有该id的用户，则将用户信息存入user对象返回；否则返回一个不含任何用户信息的user对象；
     */
    public User findId(User user) {
        SQLiteDatabase db = DBManager.getSqliteReadableDatabase(context);
        User tempUser = null;
        if (db.isOpen()) {
            String id = user.getId();
            Cursor cursor = db.query(
                    "userInfo",
                    null,
                    "id = ?",
                    new String[]{id},
                    null,
                    null,
                    null);
            tempUser = new User();
            while (cursor.moveToNext()) {
                tempUser.setId(cursor.getString(cursor.getColumnIndex("id")));
            }
            cursor.close();
            db.close();
        }
        return tempUser;
    }

    /**
     * 登录时验证账号是否存在
     *
     * @param user 用户
     * @return 如果数据库中有用户，则将用户信息存入user对象返回；否则返回一个不含任何用户信息的user对象；
     */
    public User userIsExist(User user) {
        User tempUser = null;
        String id = user.getId();
        String password = user.getPassword();
        SQLiteDatabase db = DBManager.getSqliteReadableDatabase(context);
        if (db.isOpen()) {
            // 查找数据库中是否存在该用户
            Cursor cursor = db.rawQuery(
                    "select * from userInfo where id = ? and password = ?",
                    new String[]{id, password}
            );
            while (cursor.moveToNext()) {
                tempUser = new User();
                tempUser.setId(cursor.getString(cursor.getColumnIndex("id")));
                tempUser.setName(cursor.getString(cursor.getColumnIndex("name")));
                tempUser.setAddress(cursor.getString(cursor.getColumnIndex("class_name")));
                tempUser.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                tempUser.setPhoneNumber(cursor.getString(cursor.getColumnIndex("phone_number")));
                tempUser.setAmount(cursor.getString(cursor.getColumnIndex("amount")));
            }
            cursor.close();
            db.close();
        }
        return tempUser;
    }

    /**
     * 通过用户id,获取该用户所有信息，用于用户修改密码使用
     *
     * @param user 用户
     * @return 如果数据库中有该id的用户，则将用户信息存入user对象返回；否则返回一个不含任何用户信息的user对象；
     */
    public User findUserById(User user) {
        SQLiteDatabase db = DBManager.getSqliteReadableDatabase(context);
        User tempUser = null;
        if (db.isOpen()) {
            Cursor cursor = db.query(
                    "userInfo",
                    null,
                    "id = ?",
                    new String[]{user.getId()},
                    null,
                    null,
                    null);
            while (cursor.moveToNext()) {
                tempUser = new User();
                tempUser.setId(cursor.getString(cursor.getColumnIndex("id")));
                tempUser.setName(cursor.getString(cursor.getColumnIndex("name")));
                tempUser.setAddress(cursor.getString(cursor.getColumnIndex("class_name")));
                tempUser.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                tempUser.setPhoneNumber(cursor.getString(cursor.getColumnIndex("phone_number")));
                tempUser.setAmount(cursor.getString(cursor.getColumnIndex("amount")));
            }
            cursor.close();
            db.close();
        }
        return tempUser;
    }
}
