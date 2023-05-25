package com.stbu.bookmanagementsystem.activity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.stbu.bookmanagementsystem.R;
import com.stbu.bookmanagementsystem.entity.Book;
import com.stbu.bookmanagementsystem.util.db.BookDao;

/**
 * @className UpdateBookActivity
 * @description TODO 更新图书信息的活动
 * @version 1.0
 */
public class UpdateBookActivity extends AppCompatActivity {
    private EditText et_book_id, et_book_name, et_book_number;
    private Button btn_save, btn_cancel;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        Bundle bundle = getIntent().getExtras();
        book = (Book) bundle.getSerializable("book");

        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        // 保存更新
        btn_save.setOnClickListener(v -> {
            if (et_book_name.length() == 0 || et_book_number.length() == 0) {
                Toast.makeText(UpdateBookActivity.this, "图书信息未填写完整", Toast.LENGTH_SHORT).show();
            } else {
                String bookId = et_book_id.getText().toString();
                String bookName = et_book_name.getText().toString();
                String str_bookNumber = et_book_number.getText().toString();
                int bookNumber = Integer.parseInt(str_bookNumber);
                Book book = new Book(bookId, bookName, bookNumber);
                BookDao bookDao = new BookDao(UpdateBookActivity.this);
                // 更新图书信息
                bookDao.updateBookInfo(book);
                Toast.makeText(UpdateBookActivity.this, "更新图书成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateBookActivity.this, ViewBookActivity.class);
                startActivity(intent);
                finish();
            }
        });
        
        // 取消更新
        btn_cancel.setOnClickListener(v -> {
            Intent intent = new Intent(UpdateBookActivity.this, ViewBookActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initData() {
        et_book_id.setText(book.getBookId());
        et_book_id.setEnabled(false);
        et_book_name.setText(book.getBookName());
        et_book_number.setText(String.valueOf(book.getBookNumber()));
    }

    private void initView() {
        et_book_id = findViewById(R.id.et_book_id);
        et_book_name = findViewById(R.id.et_book_name);
        et_book_number = findViewById(R.id.et_book_number);
        btn_save = findViewById(R.id.btn_save);
        btn_cancel = findViewById(R.id.btn_cancel);
    }
}
