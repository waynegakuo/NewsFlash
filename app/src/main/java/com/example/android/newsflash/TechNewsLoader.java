package com.example.android.newsflash;

import android.content.Context;

import java.util.List;

public class TechNewsLoader extends android.content.AsyncTaskLoader<List<TechNews>> {

    private String mUrl;

    public TechNewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<TechNews> loadInBackground() {

        if (mUrl == null){
            return null;
        }

        List<TechNews> news = QueryUtils.fetchJsonData(mUrl);
        return news;
    }
}

