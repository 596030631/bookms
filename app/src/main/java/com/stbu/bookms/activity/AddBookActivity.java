package com.stbu.bookms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.stbu.bookms.databinding.ActivityAddBookBinding;
import com.stbu.bookms.entity.Book;
import com.stbu.bookms.util.db.BookDao;

/**
 * @version 1.0
 * @className AddBookActivity
 * @description TODO 添加图书信息的活动
 */
public class AddBookActivity extends BaseActivity {

    private com.stbu.bookms.databinding.ActivityAddBookBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.getRoot().setOnClickListener(view -> hideKeyBoard(binding.etBookName));

        initView();
        initEvent();

        binding.btnBack.setOnClickListener(view -> finish());
    }

    private void initEvent() {
        // 保存数据
        binding.btnSaveInfo.setOnClickListener(v -> {
            String tempBookId = binding.etBookId.getText().toString();
            String tempBookName = binding.etBookName.getText().toString();
            String str_tempBookNumber = binding.etBookNumber.getText().toString();
            int tempBookNumber;
            try {
                tempBookNumber = Integer.parseInt(str_tempBookNumber);
            } catch (NumberFormatException e) {
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


    }

    private void initView() {

    }
}
