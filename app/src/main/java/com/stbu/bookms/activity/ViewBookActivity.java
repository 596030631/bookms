package com.stbu.bookms.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        binding.spinner.setAdapter(adapter);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = items[i];
                ArrayList<Book> books = bookDao.showBookInfo();
                ArrayList<Book> ddddd = new ArrayList<>();
                for (int j = 0; j < books.size(); j++) {
                    if (TextUtils.equals(category, books.get(j).getBookCategory())) {
                        ddddd.add(books.get(j));
                    }
                }
                bookAdapter = new BookAdapter(ViewBookActivity.this, ddddd);
                binding.recyclerview.setAdapter(bookAdapter);
                addClick();

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
            String temp = binding.etBookNameSearch.getText().toString().trim();
            ArrayList<Book> books = (ArrayList<Book>) bookDao.findBookByName(temp);
            bookAdapter = new BookAdapter(this, books);
            binding.recyclerview.setAdapter(bookAdapter);
            addClick();
        });
    }

    private void initView() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        ArrayList<Book> books = bookDao.showBookInfo();
        bookAdapter = new BookAdapter(ViewBookActivity.this, books);
        binding.recyclerview.setAdapter(bookAdapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));


        addClick();


    }


    private void addClick() {

        bookAdapter.setOnItemClickListenerRemake((position, user) -> {

        });

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
