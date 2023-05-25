package com.stbu.bookmanagementsystem.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.stbu.bookmanagementsystem.R;

/**
 * @className SplashActivity
 * @description TODO APP启动页面
 * @version 1.0
 */
public class SplashActivity extends AppCompatActivity {
    private int timer = 2;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg != null && msg.what == 0) {
                timer--;
                if (timer == 0) {
                    toNextActivity();
                }
            }
        }
    };

    /**
     * 页面跳转
     */
    private void toNextActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉状态栏, 此段代码应放在关联布局文件代码之前
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        new Thread(new TimerThread()).start();
    }

    /**
     * 使用 TimerThread 线程类完成启动页面倒计时的
     */
    class TimerThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 倒计时完成时
                if (timer == 0) {
                    break;
                }
                handler.sendEmptyMessage(0);
            }
        }
    }
}