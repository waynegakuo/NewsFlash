package com.example.android.newsflash;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<TechNews>> {

    private String API_URL = "https://content.guardianapis.com/search?";
    private static final String LOG_TAG = MainActivity.class.getName();

    private TechNewsAdapter newsAdapter; //setting up an adapter for the new array

    private TextView emptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Get the list View
        ListView list = (ListView) findViewById(R.id.list);



        // Setup a empty adapter for the list view
        newsAdapter = new TechNewsAdapter(this, new ArrayList<TechNews>());
        list.setAdapter(newsAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // Find the current news item that was clicked on
                TechNews item = newsAdapter.getItem(position);
                Uri url = Uri.parse(item.getUrl());

                Intent webIntent = new Intent(Intent.ACTION_VIEW, url);
                startActivity(webIntent);
            }
        });

        // Check network connection
        ConnectivityManager connectionManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo internetConnection = connectionManager.getActiveNetworkInfo();

        // fetch information if network is active
        if (internetConnection != null && internetConnection.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(0, null, this);
        } else {
            emptyStateTextView = (TextView)findViewById(R.id.no_news);
            emptyStateTextView.setText(R.string.connection_failed);
        }

    }

    @Override
    public Loader<List<TechNews>> onCreateLoader(int i, Bundle bundle) {
        Uri url = Uri.parse(API_URL);

        //appending to the path
        Uri.Builder uriBuilder = url.buildUpon();
        //the most newest news comes on top
        uriBuilder.appendQueryParameter("order-by", "newest");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("q", "tech");
        uriBuilder.appendQueryParameter("api-key", "13d83a43-4ea4-4fdd-905d-6b9e7d49a302");

        return new TechNewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<TechNews>> loader, List<TechNews> news) {

        ListView list = (ListView) findViewById(R.id.list);
        TextView empty_list = (TextView) findViewById(R.id.no_news);
        list.setEmptyView(empty_list);

        newsAdapter.clear();

        if (news != null && !news.isEmpty()) {
            newsAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<TechNews>> loader) {
        newsAdapter.clear();
    }
}
