package com.test.bxbdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oupeng.ad.sdk.FeedViewAd;

import java.util.ArrayList;
import java.util.List;

/**
 * @createTime: 2020/10/16
 * @author: lady_zhou
 * @Description:
 */
public class AdShowAdapter extends RecyclerView.Adapter {
    private List<FeedViewAd> mListItem = new ArrayList<>(1);

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_js_ad, parent, false);
        return new AdShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        showDataList((AdShowViewHolder) holder, position);

    }

    private void showDataList(AdShowViewHolder holder, int position) {
        if (mListItem != null && mListItem.size() > 0) {
            FeedViewAd feedViewAd = mListItem.get(position);
            View  view = feedViewAd.getExpressAdView(holder.itemView.getContext(),"");
            feedViewAd.bindData(view);
            holder.container.addView(view);
        }
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    private class AdShowViewHolder extends RecyclerView.ViewHolder {
        ViewGroup container;

        public AdShowViewHolder(@NonNull View itemView) {
            super(itemView);
            this.container = itemView.findViewById(R.id.container);
        }
    }

    public void setDataList(List<FeedViewAd> ads) {
        if (ads == null || ads.isEmpty()) {
            return;
        }
        mListItem.clear();
        mListItem.addAll(ads);
        notifyDataSetChanged();
    }

    public void addDataList(List<FeedViewAd> ads) {
        CommonUtils.removeDuplicate(ads, mListItem);
        int curSize = mListItem.size();
        mListItem.addAll(ads);
        notifyItemInserted(curSize);

    }
}
