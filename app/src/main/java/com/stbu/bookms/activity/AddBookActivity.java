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
import com.stbu.bookms.entity.User;
import com.stbu.bookms.util.db.BookDao;
import com.stbu.bookms.util.db.UserDao;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @className AddBookActivity
 * @description TODO 添加图书信息的活动
 */
public class AddBookActivity extends BaseActivity {

    private static final int REQUEST_CODE = 11;//用于唯一标识一个请求
    private User mUser; // 选择的用户图书所有者
    private ActivityAddBookBinding binding;//绑定xml文件
    private static final String[] items = {"经济投资", "人文社科", "教育培训", "少儿图书", "文学小说", "学习用书",
            "IT科技", "成功励志", "热门考试", "生活知识"};


    private String category = items[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());// 将binding.getRoot()作为Activity的UI树

        binding.getRoot().setOnClickListener(view -> hideKeyBoard(binding.etBookName));// 给根视图设置单击事件，用lambda表达式调用hideKeyBoard方法

        initView();// 初始化UI视图
        initEvent();// 初始化事件

        binding.btnBack.setOnClickListener(view -> finish());// 设置返回按钮点击事件，点击后finish当前Activity
// 创建一个适配器并将其设置为下拉菜单的适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        binding.spinner.setAdapter(adapter);
// 为下拉菜单添加选中项监听器，监听用户选择，将选择的分类名称保存在category变量中
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = items[i];//选类别
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    private String image;

    private void initEvent() {

        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                2);//发起用户权限请求
//图书图片设置
        binding.imageview.setOnClickListener(view -> {
            AssetManager assetManager = getAssets();
            try {
                String[] files = assetManager.list("book");//图书图片
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请选择一个选项");
                builder.setSingleChoiceItems(files, -1, (dialog, which) -> {
                    // 处理用户选择的项
                    String selectedItem = files[which];
                    image = selectedItem;
                    Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_SHORT).show();//提示框
                    InputStream inputStream;//assets 文件夹中读取指定名称的图片文件，并将其显示在一个对话框中
                    try {
                        inputStream = assetManager.open("book/" + selectedItem);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        inputStream.close();
                        binding.imageview.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    dialog.dismiss();//关闭对话框
                });

                AlertDialog dialog = builder.create();//显示对话框
                dialog.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

//图书所有人分类
        if ("admin".equals(LOGIN_USER)) {//根据登录用户的身份来决定是否显示下拉框控件
            binding.spinnerBookAuth.setVisibility(View.VISIBLE);//将一个下拉框（Spinner）控件设置为可见状态

            UserDao userDao = new UserDao(this);// 将所有用户信息从数据库中获取出来，添加到 ffff 列表中
            final List<User> uuuuu = userDao.showUserInfo();
            List<String> ffff = new ArrayList<>();
            for (int i = 0; i < uuuuu.size(); i++) {
                ffff.add(uuuuu.get(i).getName());
            }
            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ffff);
            // 将 ffff 列表中的内容适配到下拉框控件中
            binding.spinnerBookAuth.setAdapter(adapter2);

            binding.spinnerBookAuth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    mUser = uuuuu.get(i);//当下拉框中的选项变更时，mUser 也会相应变更
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            binding.etBookOwn.setVisibility(View.GONE);
        } else {//// 隐藏书籍拥有者输入框
            binding.spinnerBookAuth.setVisibility(View.GONE);
            binding.etBookOwn.setVisibility(View.VISIBLE);
            binding.etBookOwn.setText(LOGIN_USER);
            mUser = new User(LOGIN_USER, "");
        }


        // 保存数据
        binding.btnSaveInfo.setOnClickListener(v -> {
            String tempBookId = binding.etBookId.getText().toString();
            String tempBookName = binding.etBookName.getText().toString();
            String str_tempBookNumber = binding.etBookNumber.getText().toString();

            Editable auth = binding.etBookAuth.getText();
            Editable content = binding.etRemake.getText();
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
            book.setBookAuth(auth.toString());
            book.setBookContent(content.toString());
            book.setPrice(price.toString());

            if ("admin".equals(LOGIN_USER)) {
                book.setBookOwn(mUser.getId());
            } else {
                book.setBookOwn(LOGIN_USER);
            }

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//用于处理从图库中选取图片的结果
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ImageView imageView = findViewById(R.id.imageview);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();//显示
            }
        }
    }

    private void initView() {

    }
}
