package com.stbu.bookmanagementsystem.activity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.stbu.bookmanagementsystem.R;
import com.stbu.bookmanagementsystem.entity.Book;
import com.stbu.bookmanagementsystem.util.db.BookDao;

/**
 * @className AddBookActivity
 * @description TODO 添加图书信息的活动
 * @version 1.0
 */
public class AddBookActivity extends AppCompatActivity {
    private EditText et_book_id, et_book_name, et_book_number;
    private Button btn_save_info, btn_cancel_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);


        initView();
        initEvent();
    }

    private void initEvent() {
        // 保存数据
        btn_save_info.setOnClickListener(v -> {
            String tempBookId = et_book_id.getText().toString();
            String tempBookName = et_book_name.getText().toString();
            String str_tempBookNumber = et_book_number.getText().toString();
            int tempBookNumber;
            try {
                tempBookNumber = Integer.parseInt(str_tempBookNumber);
            }catch (NumberFormatException e) {
                Toast.makeText(this, "数量不支持", Toast.LENGTH_SHORT).show();
                return;
            }

            Book book = new Book(tempBookId, tempBookName, tempBookNumber);
            BookDao bookDao = new BookDao(AddBookActivity.this);

            if (tempBookId.length() == 0 || tempBookName.length() == 0 || str_tempBookNumber.length() == 0) {
                Toast.makeText(AddBookActivity.this, "图书信息未填写正确或完整！", Toast.LENGTH_SHORT).show();
            } else {
                if (bookDao.checkBookExist(book)) {
                    Toast.makeText(AddBookActivity.this, "图书编号已存在！", Toast.LENGTH_SHORT).show();
                } else {
                    bookDao.addBookInfo(book);
                    Intent intent = new Intent(AddBookActivity.this, ManageBookActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        // 取消添加
        btn_cancel_add.setOnClickListener(v -> {
            Intent intent = new Intent(AddBookActivity.this, ManageBookActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initView() {
        et_book_id = findViewById(R.id.et_book_id);
        et_book_name = findViewById(R.id.et_book_name);
        et_book_number = findViewById(R.id.et_book_number);
        btn_save_info = findViewById(R.id.btn_save_info);
        btn_cancel_add = findViewById(R.id.btn_cancel_add);
    }
}
