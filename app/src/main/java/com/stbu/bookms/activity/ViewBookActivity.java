package com.stbu.bookms.activity;

import static com.stbu.bookms.activity.ExchangeActivity.LOGIN_USER;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.stbu.bookms.adapter.BookAdapter;
import com.stbu.bookms.adapter.OnItemClickListener;
import com.stbu.bookms.databinding.ActivityViewBookBinding;
import com.stbu.bookms.entity.Book;
import com.stbu.bookms.util.db.BookDao;
import com.stbu.bookms.util.db.BorrowDao;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @className ViewBookActivity
 * @description TODO 查看图书信息活动类
 */
public class ViewBookActivity extends BaseActivity {
    private BookDao bookDao = new BookDao(ViewBookActivity.this);
    private BorrowDao borrowDao = new BorrowDao(ViewBookActivity.this);
    private BookAdapter bookAdapter;
    private com.stbu.bookms.databinding.ActivityViewBookBinding binding;

    private static final String[] items = {"经济投资", "人文社科", "教育培训", "少儿图书", "文学小说", "学习用书",
            "IT科技", "成功励志", "热门考试", "生活知识"};


    private String category = items[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewBookBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.getRoot().setOnClickListener(view -> hideKeyBoard(binding.etBookNameSearch));

        initView();
        initEvent();
    }

    private void initEvent() {


        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        binding.spinner.setAdapter(adapterSpinner);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = items[i];
                ArrayList<Book> books = bookDao.showBookInfo();
                datas.clear();
                for (int j = 0; j < books.size(); j++) {
                    if (TextUtils.equals(category, books.get(j).getBookCategory())) {
                        datas.add(books.get(j));
                    }
                }

                bookAdapter.notifyDataSetChanged();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // 返回
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
        //搜索
        binding.btnSearch.setOnClickListener(v -> {
            Log.d("tag","ffffvvvvvvvvvvvvv");
            String temp = binding.etBookNameSearch.getText().toString().trim();
            ArrayList<Book> books = (ArrayList<Book>) bookDao.findBookByName(temp);
            if (books != null && !books.isEmpty()) {
                datas.clear();
                datas.addAll(books);
            }else {
                datas.clear();
            }
            bookAdapter.notifyDataSetChanged();

        });
    }

    private void initView() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        ArrayList<Book> books = bookDao.showBookInfo();
        if (books != null && !books.isEmpty()) {
            datas.clear();
            datas.addAll(books);
        }else {
            datas.clear();
        }

        bookAdapter = new BookAdapter(ViewBookActivity.this, datas);
        binding.recyclerview.setAdapter(bookAdapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));


        addClick();


    }

    private final List<Book> datas = new ArrayList<>();


    private void addClick() {

//        bookAdapter.setOnItemClickListenerRemake((position, user) -> {
//
//        });

        if ("admin".equals(LOGIN_USER)) {
            bookAdapter.setOnItemClickListenerRemakeDel((position, user) -> {

                for (int i = 0; i < datas.size(); i++) {
                    if (datas.get(i).getBookId().equals(user.getBookId())) {
                        user.setRemakeJson("");
                        datas.get(i).setRemakeJson("");
                        bookDao.updateBookInfo(user);
                        bookAdapter.notifyDataSetChanged();
                        break;
                    }
                }

            });
        }



        // 为每一项数据绑定事件
        bookAdapter.setOnItemClickListenerOpt((position, book) -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(ViewBookActivity.this);
            builder.setTitle("请选择操作？");
            // 修改
            builder.setPositiveButton("修改", (dialog, whichButton) -> {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", book);
                intent.setClass(ViewBookActivity.this, UpdateBookActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            });
            // 删除
            builder.setNegativeButton("删除", (dialog, whichButton) -> {
                // 删除图书信息
                bookDao.deleteBookInfo(book);
                onStart();
            });
            builder.show();
        });


    }
}
