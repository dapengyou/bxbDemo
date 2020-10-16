package com.test.bxbdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.oupeng.ad.sdk.Client;
import com.oupeng.ad.sdk.ClientBuilder;
import com.oupeng.ad.sdk.FeedViewAd;
import com.oupeng.ad.sdk.FeedViewAdResponseHandler;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ztt_test";
    protected RecyclerView mRecyclerView;
    protected AdShowAdapter mAdapter;
    protected SmartRefreshLayout mRefreshLayout;
    protected int mPage = 1;
    protected int mTotalPage = 100;
    protected boolean isRefresh = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mAdapter = new AdShowAdapter();
        mRecyclerView = findViewById(R.id.list_view);
        mRefreshLayout = findViewById(R.id.refresh);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.setEnableAutoLoadMore(true);//使上拉加载具有弹性效果
        mRefreshLayout.setEnableOverScrollBounce(true);//关闭越界回弹功能

        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Log.d(TAG, "方法：onLoadMore: ");
                isRefresh = false;
                mPage++;
                if (mPage <= mTotalPage) {
                    loadData();
                } else {
                    mRefreshLayout.finishLoadMore();
                    mRefreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Log.d(TAG, "方法：onRefresh: ");
                mPage = 1;
                isRefresh = true;
                loadData();
            }
        });

        loadData();
    }

    private void loadData() {
        String slotId = "1957166918";//代码位id
        Client adClient = new ClientBuilder(this)
                .slotId(slotId, 1) //请求广告数量
                .debug(true)
                .build(); //构建请求client
        adClient.requestViewAd(new FeedViewAdResponseHandler() {
            @Override
            public void onResponse(List<FeedViewAd> list) {
                onAdLoaded(list);
            }

            @Override
            public void onFailure(Throwable throwable) {
                showStatus(throwable.getMessage());
                finish();
            }
        });
    }

    private void onAdLoaded(List<FeedViewAd> resp) {
        if (isRefresh) {
            mAdapter.setDataList(resp);
            mRefreshLayout.finishRefresh();
        } else {
            mAdapter.addDataList(resp);
            mRefreshLayout.finishLoadMore();
        }

    }

    private void showStatus(String status) {
        Toast.makeText(this.getApplication(), status, Toast.LENGTH_LONG).show();
        Log.d(TAG, status);
    }
}