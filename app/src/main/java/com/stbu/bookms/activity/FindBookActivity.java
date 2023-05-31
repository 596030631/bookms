package com.stbu.bookms.activity;

import static com.stbu.bookms.activity.ExchangeActivity.LOGIN_USER;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stbu.bookms.adapter.BookAdapter;
import com.stbu.bookms.adapter.OnItemClickListener;
import com.stbu.bookms.databinding.ActivityFindBookBinding;
import com.stbu.bookms.databinding.DialogChangeBinding;
import com.stbu.bookms.entity.Book;
import com.stbu.bookms.entity.Borrow;
import com.stbu.bookms.entity.User;
import com.stbu.bookms.util.db.BookDao;
import com.stbu.bookms.util.db.BorrowDao;
import com.stbu.bookms.util.db.UserDao;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @version 1.0
 * @className FindBookActivity
 * @description TODO 查找图书的活动
 */
public class FindBookActivity extends BaseActivity {
    private String userId;
    private String bookId;
    private String bookName;

    private int bookNumber;
    private BookDao bookDao = new BookDao(FindBookActivity.this);
    private final BorrowDao borrowDao = new BorrowDao(FindBookActivity.this);
    private BookAdapter bookAdapter;

    private final List<Book> datas = new ArrayList<>();
    private ActivityFindBookBinding binding;
    private static final String[] items = {"经济投资", "人文社科", "教育培训", "少儿图书", "文学小说", "学习用书",
            "IT科技", "成功励志", "热门考试", "生活知识"};

    private String category = items[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFindBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        userId = intent.getStringExtra("id");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        binding.spinner.setAdapter(adapter);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = items[i];//分类

                String temp = binding.etBookNameSearch.getText().toString().trim();
                List<Book> books = bookDao.findBookByName(temp);
                datas.clear();//清空 List 中的所有元素

                for (int j = 0; j < books.size(); j++) {
                    if (category.equals(books.get(j).getBookCategory())) {
                        datas.add(books.get(j));
                    }
                }

                bookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        initView();
        ArrayList<Book> books = bookDao.showBookInfo();
        datas.addAll(books);
        initData();
        initEvent();
    }


    private void initEvent() {

        // 返回
        binding.btnReturn.setOnClickListener(view -> {
            Intent intent = new Intent(FindBookActivity.this, UserActivity.class);
            startActivity(intent);
            finish();
        });

        binding.btnSearch.setOnClickListener(v -> {
            String temp = binding.etBookNameSearch.getText().toString().trim();
            List<Book> books = bookDao.findBookByName(temp);
            datas.clear();

            for (int i = 0; i < books.size(); i++) {
                if (category.equals(books.get(i).getBookCategory())) {
                    datas.add(books.get(i));
                }
            }

            bookAdapter.notifyDataSetChanged();
        });
    }

    @SuppressLint("WrongViewCast")
    private void initView() {

    }

    private void initData() {
        bookAdapter = new BookAdapter(FindBookActivity.this, datas);
        bookAdapter.setOnItemClickListenerOpt(null);
        binding.recyclerview.setAdapter(bookAdapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));

        bookAdapter.setOnItemClickListenerRemake((position, user) -> {//评论

            Gson gson = new Gson();// JSON 格式的数据和 Java 对象之间进行相互转换
            Type type = new TypeToken<List<Book.BookRemake>>() {
            }.getType(); // 使用 TypeToken 获取要解析的类型，token身份验证令牌
            List<Book.BookRemake> personList = gson.fromJson(user.getRemakeJson(), type);
            Book.BookRemake bookRemake = new Book.BookRemake();
            bookRemake.remake = user.getAddRemake();
            bookRemake.userName = LOGIN_USER;

            if (personList == null) {
                Log.d("ddd","dddddddddddddddddddddddddddddddddddddddddd");
                personList = new ArrayList<>();
            }

            personList.add(bookRemake);
            BookDao bookDao = new BookDao(FindBookActivity.this);
            // 更新图书信息
            user.setRemakeJson(gson.toJson(personList));
            Log.d("tag","ccccc=" +user.toString());
            bookDao.updateBookInfo(user);

            for (int i = 0; i < datas.size(); i++) {
                if (datas.get(i).getBookId().equals(user.getBookId())) {
                    datas.get(i).setAddRemake(user.getRemakeJson());
                    break;
                }
            }

            bookAdapter.notifyDataSetChanged();
        });




        bookAdapter.setOnItemClickListener(new OnItemClickListener<Book>() {
            @Override
            public void click(int position, Book bookInfo) {//互换图书操作


                bookId = bookInfo.getBookId();
                bookName = bookInfo.getBookName();
                bookNumber = bookInfo.getBookNumber();
                if (bookNumber <= 0) {
                    Toast.makeText(getApplicationContext(), "该图书余量:0，不可互换", Toast.LENGTH_SHORT).show();
                } else {
                    boolean flag = false;
                    // 查看某账号用户的全部互换信息
                    List<Borrow> borrows = borrowDao.showAllBorrowBookForUser(userId);
                    for (int i = 0; i < borrows.size(); i++) {
                        if ((borrows.get(i).getBorrowBookId()).equals(bookId)) {
                            flag = true;
                        }
                    }
                    if (flag) {
                        Toast.makeText(getApplicationContext(), "你已互换，不可重复互换", Toast.LENGTH_SHORT).show();
                    } else {

                        DialogChangeBinding dialogBinding = DialogChangeBinding.inflate(getLayoutInflater());//xml_dialog_change
                        AlertDialog dialog = new AlertDialog.Builder(FindBookActivity.this)
                                .setView(dialogBinding.getRoot())
                                .create();
                        dialog.show();
                        dialogBinding.btnCancel.setOnClickListener(view12 -> dialog.dismiss());

                        dialogBinding.book.setText(bookInfo.getBookName());
                        dialogBinding.buyer.setText(LOGIN_USER);
                        dialogBinding.saleName.setText(bookInfo.getBookOwn());
                        dialogBinding.price.setText(bookInfo.getPrice());

                        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                        dialogBinding.datetime.setText(sdf.format(System.currentTimeMillis()));

                        dialogBinding.btnSure.setOnClickListener(view1 -> {


                            Editable address = dialogBinding.address.getText();
                            Editable remake = dialogBinding.remake.getText();


                            if (address == null) {
                                Toast.makeText(FindBookActivity.this, "请录入信息", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (address.toString().isEmpty()) {
                                Toast.makeText(FindBookActivity.this, "请录入信息", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            final Borrow changeInfo = new Borrow();

                            changeInfo.setAddress(address.toString());
                            changeInfo.setOwn(bookInfo.getBookOwn());
                            changeInfo.setBuyer_name(LOGIN_USER);
                            changeInfo.setBorrowBookName(bookInfo.getBookName());
                            changeInfo.setPrice(bookInfo.getPrice());
                            changeInfo.setRemake(remake.toString()); // pinglun
                            changeInfo.setDatetime(sdf.format(System.currentTimeMillis()));

                            Book tempBook = bookDao.borrowBookNumberChange(bookId);

                            UserDao userDao = new UserDao(FindBookActivity.this);

                            User user = new User(userId, "");

                            User userInfo = userDao.findUserById(user);


                            User user2 = new User(bookInfo.getBookOwn(), "");
                            user2 = userDao.findUserById(user2);


                            if (userInfo == null || user2 == null) {
                                Toast.makeText(FindBookActivity.this, "用户查找失败", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (LOGIN_USER.equals(bookInfo.getBookAuth())) {
                                Toast.makeText(FindBookActivity.this, "自己的书不需要互换", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            BigDecimal bigDecimal = new BigDecimal(userInfo.getAmount());//任意精度的金额
                            bigDecimal = bigDecimal.subtract(new BigDecimal(bookInfo.getPrice()));//相减
                            if (bigDecimal.compareTo(BigDecimal.ZERO) < 0) {
                                Toast.makeText(FindBookActivity.this, "余额不足", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            Log.d("tag", "书价：=" + bookInfo.getPrice().toString());
                            Log.d("tag", "余额：=" + bigDecimal.toString());


                            user.setAmount(bigDecimal.toString());
                            userDao.updateUserAmount(user);


                            BigDecimal fff = new BigDecimal(user2.getAmount());//金额处理
                            fff = fff.add(new BigDecimal(bookInfo.getPrice()));
                            user2.setAmount(fff.toString());
                            userDao.updateUserAmount(user2);

                            // 更新图书互换信息
                            bookDao.updateBorrowBookInfo(tempBook);


                            // 增加换书信息
                            borrowDao.addBorrowBookInfo(changeInfo);
                            bookDao = new BookDao(FindBookActivity.this);
                            Toast.makeText(getApplicationContext(), "互换成功", Toast.LENGTH_SHORT).show();

                            bookAdapter.notifyDataSetChanged();
                            dialog.dismiss();

                            Toast.makeText(FindBookActivity.this, "互换成功", Toast.LENGTH_SHORT).show();

                        });


                    }
                }


            }
        });
    }
}
