package com.stbu.bookms.activity;

import static com.stbu.bookms.activity.ExchangeActivity.LOGIN_USER;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @version 1.0
 * @className FindBookActivity
 * @description TODO 查找图书的活动
 */
public class FindBookActivity extends AppCompatActivity {
    private String userId;
    private String bookId;
    private String bookName;

    private int bookNumber;
    private BookDao bookDao = new BookDao(FindBookActivity.this);
    private BorrowDao borrowDao = new BorrowDao(FindBookActivity.this);
    private BookAdapter bookAdapter;

    private List<Book> datas = new ArrayList<>();
    private com.stbu.bookms.databinding.ActivityFindBookBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFindBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        userId = intent.getStringExtra("id");

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
            datas.addAll(books);
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

        bookAdapter.setOnItemClickListener(new OnItemClickListener<Book>() {
            @Override
            public void click(int position, Book bookInfo) {


                bookId = bookInfo.getBookId();
                bookName = bookInfo.getBookName();
                bookNumber = bookInfo.getBookNumber();
                if (bookNumber <= 0) {
                    Toast.makeText(getApplicationContext(), "该图书余量:0，不可互换", Toast.LENGTH_SHORT).show();
                } else {
                    boolean flag = false;
                    // 查看某学号用户的全部互换信息
                    List<Borrow> borrows = borrowDao.showAllBorrowBookForUser(userId);
                    for (int i = 0; i < borrows.size(); i++) {
                        if ((borrows.get(i).getBorrowBookId()).equals(bookId)) {
                            flag = true;
                        }
                    }
                    if (flag) {
                        Toast.makeText(getApplicationContext(), "你已互换，不可重复互换", Toast.LENGTH_SHORT).show();
                    } else {

                        DialogChangeBinding dialogBinding = DialogChangeBinding.inflate(getLayoutInflater());
                        AlertDialog dialog = new AlertDialog.Builder(FindBookActivity.this)
                                .setView(dialogBinding.getRoot())
                                .create();
                        dialog.show();
                        dialogBinding.btnCancel.setOnClickListener(view12 -> dialog.dismiss());

                        dialogBinding.book.setText(bookInfo.getBookName());
                        dialogBinding.buyer.setText(LOGIN_USER);
                        dialogBinding.saleName.setText(bookInfo.getBookAuth());
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
                            changeInfo.setSale_name(bookInfo.getBookAuth());
                            changeInfo.setBuyer_name(LOGIN_USER);
                            changeInfo.setBorrowBookName(bookInfo.getBookName());
                            changeInfo.setPrice(bookInfo.getPrice());
                            changeInfo.setRemake(remake.toString()); // pinglun
                            changeInfo.setDatetime(sdf.format(System.currentTimeMillis()));

                            Book tempBook = bookDao.borrowBookNumberChange(bookId);

                            UserDao userDao = new UserDao(FindBookActivity.this);

                            User user = new User(userId, "");

                           User userInfo = userDao.findUserById(user);
                           if (userInfo == null) {
                               Toast.makeText(FindBookActivity.this, "用户查找失败", Toast.LENGTH_SHORT).show();
                               return;
                           }

                           if (LOGIN_USER.equals(bookInfo.getBookAuth())) {
                               Toast.makeText(FindBookActivity.this, "自己的书不需要互换", Toast.LENGTH_SHORT).show();
                               return;
                           }

                            BigDecimal bigDecimal = new BigDecimal(userInfo.getAmount());
                            bigDecimal = bigDecimal.subtract(new BigDecimal(bookInfo.getPrice()));
                            if (bigDecimal.compareTo(BigDecimal.ZERO) < 0) {
                                Toast.makeText(FindBookActivity.this, "余额不足", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            Log.d("tag", "书价：="+bookInfo.getPrice().toString());
                            Log.d("tag", "余额：="+bigDecimal.toString());


                            user.setAmount(bigDecimal.toString());
                            userDao.updateUserAmount(user);

                            // 更新图书互换信息
                            bookDao.updateBorrowBookInfo(tempBook);


                            // 增加借书信息
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
