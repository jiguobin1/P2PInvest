package jiguobin.com.p2pinvest.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import jiguobin.com.p2pinvest.R;
import jiguobin.com.p2pinvest.ui.LoadingPage;

/**
 * Created by acer-PC on 2017/5/16.
 */

public abstract class BaseFragment extends Fragment {
    private int layoutId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
////        View view = UIUtils.getView(R.layout.home_fragment);
//        View inflate = inflater.inflate(getLayoutId(), null);
//        ButterKnife.inject(this, inflate);

        LoadingPage loadingPage = new LoadingPage(container.getContext()) {
            @Override
            public int layoutId() {
                return getLayoutId();
            }
//        初始化title
//        initTitle();
//        初始化数据
//        initData();
        };
        return loadingPage;
    }
    //初始化界面的数据
    protected abstract void initData();
    //初始化title
    protected abstract void initTitle();
    //提供布局
    public abstract int getLayoutId();
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}

