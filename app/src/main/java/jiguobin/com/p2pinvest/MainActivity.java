package jiguobin.com.p2pinvest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import jiguobin.com.p2pinvest.common.ActivityManager;
import jiguobin.com.p2pinvest.fragment.HomeFragment;
import jiguobin.com.p2pinvest.fragment.HomeFragment1;
import jiguobin.com.p2pinvest.fragment.InvestFragment;
import jiguobin.com.p2pinvest.fragment.MeFragment;
import jiguobin.com.p2pinvest.fragment.MoreFragment;

public class MainActivity extends AppCompatActivity {


    @InjectView(R.id.rb_home)
    RadioButton rbHome;
    @InjectView(R.id.rb_invest)
    RadioButton rbInvest;
    @InjectView(R.id.rb_me)
    RadioButton rbMe;
    @InjectView(R.id.rb_more)
    RadioButton rbMore;
    @InjectView(R.id.group)
    RadioGroup group;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private HomeFragment1 homeFragment1;
    private InvestFragment investFragment;
    private MeFragment meFragment;
    private MoreFragment moreFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        //将当前的activity添加到Activity
        ActivityManager.getInstance().add(this);
        //模拟异常
//        String str=null;
//        if(str.equals("abc")){
//            Log.e("TAG","abc");
//        }
        initfragment();
    }
    //加载碎片相关变量
    private void initfragment() {
        homeFragment1=new HomeFragment1();
        investFragment=new InvestFragment();
        meFragment=new MeFragment();
        moreFragment=new MoreFragment();
        fm=getSupportFragmentManager();
        ft=fm.beginTransaction();
        ft.add(R.id.fl_main,homeFragment1);
        ft.add(R.id.fl_main,investFragment);
        ft.add(R.id.fl_main,meFragment);
        ft.add(R.id.fl_main,moreFragment);
        ft.show(homeFragment1);
        ft.hide(investFragment);
        ft.hide(meFragment);
        ft.hide(moreFragment);
        ft.commit();
        //默认选中常用框架
        group.check(R.id.rb_home);
    }
    //点击切换不同碎片
    @OnClick({R.id.rb_home, R.id.rb_invest, R.id.rb_me, R.id.rb_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_home:
                ft=fm.beginTransaction();
                ft.show(homeFragment1);
                ft.hide(investFragment);
                ft.hide(meFragment);
                ft.hide(moreFragment);
                ft.commit();
                break;
            case R.id.rb_invest:
                ft=fm.beginTransaction();
                ft.show(investFragment);
                ft.hide(homeFragment1);
                ft.hide(meFragment);
                ft.hide(moreFragment);
                ft.commit();
                break;
            case R.id.rb_me:
                ft=fm.beginTransaction();
                ft.show(meFragment);
                ft.hide(investFragment);
                ft.hide(homeFragment1);
                ft.hide(moreFragment);
                ft.commit();
                break;
            case R.id.rb_more:
                ft=fm.beginTransaction();
                ft.show(moreFragment);
                ft.hide(investFragment);
                ft.hide(meFragment);
                ft.hide(homeFragment1);
                ft.commit();
                break;
        }
    }


    private boolean flag=true;
    private static final int WHAT_RESET_BACK = 1;
    //线程中复原
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case WHAT_RESET_BACK:
                    flag=true;
                    break;
            }
        }
    };
    //重写onKeyUp(),实现连续点击两次退出程序
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && flag){//第一次点击
            Toast.makeText(MainActivity.this,"再点击一次退出应用",Toast.LENGTH_SHORT).show();
            flag=false;
            //发送延迟消息 超过两秒后不能退出
            handler.sendEmptyMessageDelayed(WHAT_RESET_BACK,2000);
            return true;//第一次点击走这个退不出去
        }
        return super.onKeyUp(keyCode, event);
    }
    //为了避免出现内存的泄露，需要在onDestroy（）中。移除所有未被执行的信息
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //handler.removeMessages(WHAT_RESET_BACK);//移除指定id的所有信息
        handler.removeCallbacksAndMessages(null);//移除所有线程
    }
}
