package com.stbu.bookms.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {//Bundle 是 Android 中的一个重要类，主要用于实现不同组件之间进行数据交换
        super.onCreate(savedInstanceState);//// 调用父类的onCreate方法，//更加美观
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);//// 去除窗口标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//// 设置全屏显示：隐藏导航栏和状态栏
        View decorView = getWindow().getDecorView();//// 获取DecorView对象，用于控制系统UI的显示
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;// 定义隐藏系统UI的选项
        decorView.setSystemUiVisibility(uiOptions);//// 设置隐藏系统UI的选项
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);// 另一种方式设置隐藏状态栏的选项
    }



    protected void hideKeyBoard(View editable) {//隐藏软键盘的功能
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editable.getWindowToken(), 0);

    }
}
