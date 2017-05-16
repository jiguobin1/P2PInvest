package jiguobin.com.p2pinvest.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import jiguobin.com.p2pinvest.R;
import jiguobin.com.p2pinvest.bean.Image;
import jiguobin.com.p2pinvest.common.AppNetConfig;


/**
 * Created by acer-PC on 2017/5/12.
 */

public class HomeFragment extends Fragment {
    @InjectView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_title_setting)
//    ImageView ivTitleSetting;
//    @InjectView(R.id.vp_home)
    ViewPager vpHome;
    @InjectView(R.id.tv_home_product)
    TextView tvHomeProduct;
    @InjectView(R.id.tv_home_yearrater)
    TextView tvHomeYearrater;
//    @InjectView(R.id.cp_home_indicator)
//    CirclePageIndicator cpHomeIndicator;
    private List<Image.ImageArrBean> imageArr;
    private Gson mGson = new Gson();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = UIUtils.getView(R.layout.home_fragment);
        View inflate = inflater.inflate(R.layout.home_fragment, null);
        ButterKnife.inject(this, inflate);
        //初始化title
        initTitle();
        initData();
        return inflate;
    }

    private void initData() {
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest(AppNetConfig.INDEX, RequestMethod.GET);
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();//得到请求数据
                Log.e("TAG", json);
                Image image = mGson.fromJson(json, Image.class);
                Image.ProInfoBean proInfo = image.getProInfo();
                imageArr = image.getImageArr();
                Log.e("TAG", "----------:" + imageArr);
                tvHomeProduct.setText(proInfo.getName());
                tvHomeYearrater.setText(proInfo.getYearRate() + "%");
                //设置viewpager适配器
                vpHome.setAdapter(new MyAdapter());
                //使用框架设置小圆圈显示
                //cpHomeIndicator.setViewPager(vpHome);
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    private void initTitle() {
        ivTitleBack.setVisibility(View.GONE);
        tvTitle.setText("首页");
        //ivTitleSetting.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageArr.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(getActivity());
            //1.imageView显示图片
            String imaurl = imageArr.get(position).getIMAURL();
            Log.e("TAG", "ppppp:" + imaurl);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);//设置图片位置居中
            Picasso.with(getActivity()).load(imaurl).error(R.mipmap.ic_launcher).into(imageView);
            //2.imageView添加到容器中
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);//移除操作
        }
    }
}
