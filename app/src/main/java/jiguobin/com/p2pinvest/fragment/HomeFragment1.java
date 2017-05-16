package jiguobin.com.p2pinvest.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.tools.Util;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import jiguobin.com.p2pinvest.R;
import jiguobin.com.p2pinvest.bean.Image;
import jiguobin.com.p2pinvest.common.AppNetConfig;
import jiguobin.com.p2pinvest.common.BaseFragment;
import jiguobin.com.p2pinvest.ui.RoundProgress;
import jiguobin.com.p2pinvest.util.UIUtils;


/**
 * Created by acer-PC on 2017/5/12.
 */

public class HomeFragment1 extends BaseFragment {
    @InjectView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_title_setting)
    ImageView ivTitleSetting;
    @InjectView(R.id.tv_home_product)
    TextView tvHomeProduct;
    @InjectView(R.id.tv_home_yearrater)
    TextView tvHomeYearrater;
    @InjectView(R.id.banner)
    Banner banner;
    @InjectView(R.id.roundPro_home)
    RoundProgress roundProHome;
    private List<Image.ImageArrBean> imageArr;
    private Image.ProInfoBean proInfo;
    private int currentProress;
    private Gson mGson = new Gson();
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            //设置当前的最大值
            roundProHome.setMax(100);
            for(int i=0;i<currentProress;i++){
                roundProHome.setProgress(i+1);
                SystemClock.sleep(20);
                //强制重绘
                //roundProHome.invalidate();//只有主线程中如此调用
                roundProHome.postInvalidate();//主线程和分线程都可以如此调用
            }
        }
    };

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        //View view = UIUtils.getView(R.layout.home_fragment);
//        View inflate = inflater.inflate(R.layout.home_fragment, null);
//        ButterKnife.inject(this, inflate);
//        //初始化title
//        initTitle();
//        initData();
//        return inflate;
//    }
@Override
public int getLayoutId() {
    return R.layout.home_fragment;
}

    public void initData() {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest(AppNetConfig.INDEX, RequestMethod.GET);
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();//得到请求数据
                Image image = mGson.fromJson(json, Image.class);
                proInfo= image.getProInfo();
                imageArr = image.getImageArr();
                //更新界面数据
                tvHomeProduct.setText(proInfo.getName());
                tvHomeYearrater.setText(proInfo.getYearRate() + "%");
                //获取数据中的进度值
                currentProress= Integer.parseInt(proInfo.getProgress());
                //在分线程中，实现进度的动态变化
                new Thread(runnable).start();
                //设置banner样式
                banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
                //设置图片加载器
                banner.setImageLoader(new GlideImageLoader());
                //设置图片集合
                ArrayList<String> imagesUrl = new ArrayList<String>(imageArr.size());
                for (int i = 0; i < imageArr.size(); i++) {
                    imagesUrl.add(imageArr.get(i).getIMAURL());
                }
                banner.setImages(imagesUrl);
                //设置banner动画效果
                banner.setBannerAnimation(Transformer.DepthPage);
                //设置标题集合（当banner样式有显示title时）
                String[] titles = new String[]{"分享砍学费", "人脉总动员", "想不到你是这样的app", "购物街爱不单行"};
                banner.setBannerTitles(Arrays.asList(titles));
                //设置自动轮播，默认为true
                banner.isAutoPlay(true);
                //设置轮播时间
                banner.setDelayTime(1500);
                //设置指示器位置（当banner模式中有指示器时）
                banner.setIndicatorGravity(BannerConfig.CENTER);
                //banner设置方法全部调用完毕时最后调用
                banner.setBannerAnimation(Transformer.CubeOut);
                banner.start();


            }

            @Override
            public void onFailed(int what, Response<String> response) {
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    public void initTitle() {
        ivTitleBack.setVisibility(View.GONE);
        tvTitle.setText("首页");
        ivTitleSetting.setVisibility(View.GONE);
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */
            //Picasso 加载图片简单用法
            Picasso.with(context).load((String) path).into(imageView);

        }

    }

}
