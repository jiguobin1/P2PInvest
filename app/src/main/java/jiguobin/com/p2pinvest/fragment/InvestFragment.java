package jiguobin.com.p2pinvest.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import jiguobin.com.p2pinvest.R;
import jiguobin.com.p2pinvest.common.BaseFragment;
import jiguobin.com.p2pinvest.util.UIUtils;

/**
 * Created by acer-PC on 2017/5/12.
 */

public class InvestFragment extends BaseFragment {
    @InjectView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_title_setting)
    ImageView ivTitleSetting;
    private TextView showtv;


    @Override
    protected void initData() {

    }

    public void initTitle() {
        ivTitleBack.setVisibility(View.GONE);
        tvTitle.setText("投资");
        ivTitleSetting.setVisibility(View.GONE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.invest_fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
