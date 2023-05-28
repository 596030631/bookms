package com.stbu.bookms.activity;

import static com.stbu.bookms.activity.ExchangeActivity.LOGIN_USER;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.stbu.bookms.R;
import com.stbu.bookms.databinding.ActivityAddBookBinding;
import com.stbu.bookms.entity.Book;
import com.stbu.bookms.util.db.BookDao;

import java.io.IOException;
import java.io.InputStream;

/**
 * @version 1.0
 * @className AddBookActivity
 * @description TODO 添加图书信息的活动
 */
public class AddBookActivity extends BaseActivity {

    private static final int REQUEST_CODE = 11;
    private com.stbu.bookms.databinding.ActivityAddBookBinding binding;
    private static final String[] items = {"经济投资", "人文社科", "教育培训", "少儿图书", "文学小说", "学习用书",
            "IT科技", "成功励志", "热门考试", "生活知识"};


    private String category = items[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.getRoot().setOnClickListener(view -> hideKeyBoard(binding.etBookName));

        initView();
        initEvent();

        binding.btnBack.setOnClickListener(view -> finish());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        binding.spinner.setAdapter(adapter);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = items[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        binding.etBookAuth.setText(LOGIN_USER);

    }

//    private void saveImageToGallery(Bitmap bitmap, String fileName) {
//        // 保存图片到 Pictures 文件夹
//        String dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/book/";
//        File fileDir = new File(dirPath);
//        if (!fileDir.exists()) {
//            fileDir.mkdirs();
//        }
//        String filePath = dirPath + fileName;
//        File file = new File(filePath);
//        if (file.exists()) return;
//        try {
//            FileOutputStream outputStream = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
//            outputStream.flush();
//            outputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    // 保存图片到相册
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.TITLE, fileName);
//        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
//        values.put(MediaStore.Images.Media.DESCRIPTION, "My Image Description");
//        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
//        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
//        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
//        values.put(MediaStore.Images.Media.DATA, filePath);
//
//        getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//    }

    private String image;

    private void initEvent() {

        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                2);

        binding.imageview.setOnClickListener(view -> {
            AssetManager assetManager = getAssets();
            try {
                String[] files = assetManager.list("book");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请选择一个选项");
                builder.setSingleChoiceItems(files, -1, (dialog, which) -> {
                    // 处理用户选择的项
                    String selectedItem = files[which];
                    image = selectedItem;
                    Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_SHORT).show();
                    InputStream inputStream;
                    try {
                        inputStream = assetManager.open("book/" + selectedItem);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        inputStream.close();
                        binding.imageview.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    dialog.dismiss();
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // 保存数据
        binding.btnSaveInfo.setOnClickListener(v -> {
            String tempBookId = binding.etBookId.getText().toString();
            String tempBookName = binding.etBookName.getText().toString();
            String str_tempBookNumber = binding.etBookNumber.getText().toString();

            Editable auth = binding.etBookAuth.getText();
            Editable content = binding.etBookRemark.getText();
            Editable price = binding.etBookPrice.getText();

            if (image == null) {
                Toast.makeText(this, "还没选择图片", Toast.LENGTH_SHORT).show();
                return;
            }
            String localImage = image;
            image = null;

            if (auth == null || content == null || price == null) {
                Toast.makeText(this, "请录入完整", Toast.LENGTH_SHORT).show();
                return;
            }

            int tempBookNumber;
            try {
                tempBookNumber = Integer.parseInt(str_tempBookNumber);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "数量不支持", Toast.LENGTH_SHORT).show();
                return;
            }

            Book book = new Book(tempBookId, tempBookName, tempBookNumber);
            book.setBookCategory(category);
            book.setBookContent(content.toString());
            book.setPrice(price.toString());
            book.setBookAuth(LOGIN_USER);
            book.setImage(localImage);

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ImageView imageView = findViewById(R.id.imageview);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initView() {

    }
}
