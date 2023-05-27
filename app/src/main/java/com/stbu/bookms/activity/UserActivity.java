package com.stbu.bookms.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stbu.bookms.R;
import com.stbu.bookms.databinding.ActivityUserBinding;
import com.stbu.bookms.databinding.DialogPayBinding;
import com.stbu.bookms.entity.User;
import com.stbu.bookms.util.db.UserDao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @className UserActivity
 * @description TODO 显示用户主界面
 */
public class UserActivity extends BaseActivity {
    private User user;
    private List<ImageView> images;
    //下方指示点
    private List<View> dots;
    private int currentItem;
    //记录上一次点的位置
    private int oldPosition = 0;
    //存放图片的id
    private int[] imageIds = new int[]{R.drawable.lun1, R.drawable.lun2, R.drawable.lun3};
    //存放图片的标题，可以设为空
    private String[] titles = new String[]{"title1", "title2", "title3"};
    private TextView title;
    private ViewPagerAdapter adapter;
    //定时调度机制
    private ScheduledExecutorService scheduledExecutorService;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private com.stbu.bookms.databinding.ActivityUserBinding binding;

    private final UserDao userDao = new UserDao(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initData();

        setView();

        initEvent();
    }


    //UI界面的更新
    private void setView() {
        //显示的图片
        images = new ArrayList<ImageView>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        //显示的小点
        dots = new ArrayList<View>();
        dots.add(findViewById(R.id.dot_0));
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));

        title = findViewById(R.id.title);
        title.setText(titles[0]);

        adapter = new ViewPagerAdapter();
        binding.viewpager.setAdapter(adapter);

        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                title.setText(titles[position]);
                dots.get(position).setBackgroundResource(R.drawable.book);
                dots.get(oldPosition).setBackgroundResource(R.drawable.icon);
                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
        });
    }

    /*定义的适配器*/
    public class ViewPagerAdapter extends PagerAdapter {
        //返回当前有效视图的个数。
        @Override
        public int getCount() {
            return images.size();
        }

        //判断instantiateItem函数所返回来的Key与一个页面视图是否是代表的同一个视图
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView(images.get(position));
        }

        //创建指定位置的页面视图，也就是将一张图片放到容器中的指定位置
        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            view.addView(images.get(position));
            return images.get(position);
        }

    }

    /**
     * 利用线程池定时执行动画轮播
     */
    @Override
    public void onStart() {
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        //每隔2s时间，固定执行轮播任务。
        scheduledExecutorService.scheduleWithFixedDelay(new ViewPageTask(), 2, 2, TimeUnit.SECONDS);
    }

    /**
     * 图片轮播任务,发送轮播消息
     */
    private class ViewPageTask implements Runnable {
        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            mHandler.sendEmptyMessage(0);   //发送轮播消息
        }
    }

    /**
     * 接收子线程传递过来的数据
     */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            //这里不是具体的数据，而是一个轮播信号，目的是切换下一张图片
            binding.viewpager.setCurrentItem(currentItem);
        }
    };

    //当切换到其他界面时，关闭后台轮播
    @Override
    public void onStop() {
        super.onStop();
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }

    private void initData() {
        user = getUserAccount();
        String welcome = "欢迎你，" + user.getName();
        binding.tvUserWelcome.setText(welcome);
        binding.tvAmount.setText("金额:" + user.getAmount());
    }

    private void initView() {

    }

    @SuppressLint("CommitPrefEdits")
    private void initEvent() {

        binding.btnExit.setOnClickListener(view -> {
            sharedPreferences = getSharedPreferences("userInfo", 0);
            editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(UserActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        binding.btnPay.setOnClickListener(view -> {
            DialogPayBinding dialogPayBinding = DialogPayBinding.inflate(getLayoutInflater());
            AlertDialog dialog = new AlertDialog.Builder(UserActivity.this)
                    .setView(dialogPayBinding.getRoot())
                    .create();
            dialog.show();

            dialogPayBinding.payButton.setOnClickListener(view1 -> {
                dialog.dismiss();

                BigDecimal decimal = new BigDecimal(user.getAmount());
                decimal = decimal.add(new BigDecimal(dialogPayBinding.etAmount.getText().toString()));

                user.setAmount(decimal.toString());
                binding.tvAmount.setText("金额:" + user.getAmount());
                userDao.updateUserInfo(user);
                Toast.makeText(UserActivity.this, "修改成功", Toast.LENGTH_SHORT).show();

            });

            dialogPayBinding.cancelButton.setOnClickListener(view12 -> {
                dialog.dismiss();
            });
        });

        // 图书交换
        binding.btnExchange.setOnClickListener(view -> {
            Intent intent = new Intent(UserActivity.this, ChangeActivity.class);
            startActivity(intent);
        });

        // 留言交流
        binding.btnChat.setOnClickListener(view -> {
            Intent intent = new Intent(UserActivity.this, FriendsActivity.class);
            startActivity(intent);
        });
        // 查找图书
        binding.btnFindBook.setOnClickListener(v -> {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("id", user.getId());
            intent.putExtras(bundle);
            intent.setClass(getApplicationContext(), FindBookActivity.class);
            startActivity(intent);
            finish();
        });

        // 查看已借书籍
//        binding.btnViewBorrowBook.setOnClickListener(v -> {
//            Intent intent = new Intent();
//            Bundle bundle = new Bundle();
//            bundle.putString("id", user.getId());
//            intent.putExtras(bundle);
//            intent.setClass(getApplicationContext(), BorrowBookActivity.class);
//            startActivity(intent);
//            finish();
//        });

        // 修改用户密码
        binding.btnUpdateUserPwd.setOnClickListener(v -> {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("id", user.getId());
            bundle.putString("password", user.getPassword());
            intent.putExtras(bundle);
            intent.setClass(getApplicationContext(), UpdatePasswordActivity.class);
            startActivity(intent);
            finish();
        });

        // 添加书籍
        binding.btnAddBook.setOnClickListener(view -> {
            Intent intent = new Intent(UserActivity.this, AddBookActivity.class);
            startActivity(intent);
        });

    }

    private User getUserAccount() {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", 0);
        // 读取数据
        String strJson = sharedPreferences.getString("user", "");
        // 处理数据 json 字符串 => object
        Gson gson = new Gson();
        User user = gson.fromJson(strJson, new TypeToken<User>() {
        }.getType());
        return user;
    }
}
