package com.example.android.newsflash;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TechNewsAdapter extends ArrayAdapter<TechNews> {

    public TechNewsAdapter(@NonNull Context context, @NonNull List<TechNews> news) {
        super(context, 0, news);
    }

    private ArrayList<TechNews> newsArrayList;
    Context context;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View newsItem = convertView;

        if(newsItem == null){
            newsItem = LayoutInflater.from(getContext()).inflate(R.layout.item_list,parent, false);
        }

        TechNews news = getItem(position);

        // Set the title text
        TextView titleView = (TextView) newsItem.findViewById(R.id.title);
        titleView.setText(news.getTitle());

        // Get authors name if available
        TextView authorName = (TextView) newsItem.findViewById(R.id.author);
        if(news.hasAuthor()){
            authorName.setText(news.getAuthor());
        }else{
            authorName.setText(R.string.no_author);
        }
        // Split the date and set each to its view
        TextView dateView = (TextView) newsItem.findViewById(R.id.date);
        TextView timeView = (TextView) newsItem.findViewById(R.id.time);

        String[] dateTime = news.getDate().split("T");

        String dateItem = dateTime[0];
        dateView.setText(dateItem);

        String time = dateTime[1].substring(0,5);
        timeView.setText(time);

        return newsItem;
    }
}
