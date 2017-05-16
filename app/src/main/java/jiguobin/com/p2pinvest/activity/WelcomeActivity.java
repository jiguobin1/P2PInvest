package jiguobin.com.p2pinvest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import jiguobin.com.p2pinvest.MainActivity;
import jiguobin.com.p2pinvest.R;
import jiguobin.com.p2pinvest.common.ActivityManager;

public class WelcomeActivity extends AppCompatActivity {

    @InjectView(R.id.iv_welcome_icon)
    ImageView ivWelcomeIcon;
    @InjectView(R.id.rl_welcome)
    RelativeLayout rlWelcome;
    private Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.inject(this);
        //将当前的activity添加到Activity
        ActivityManager.getInstance().add(this);
//        noBar();//全屏显示
        showAnimation();//启动动画

    }

    private void showAnimation() {
        //透明度
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(2000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(i);
//                finish();
                //结束activity的显示并从栈控件中移除
                ActivityManager.getInstance().remove(WelcomeActivity.this);
            }
        },3000);
        rlWelcome.startAnimation(alphaAnimation);//启动动画

    }
    /**
      * 显示动画:
      *     透明度: 0--1 持续2s
      */

    private void noBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);// 隐藏顶部的状态栏
        setContentView(R.layout.activity_welcome);
    }


}
