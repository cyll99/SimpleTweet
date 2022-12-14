package com.codepath.apps.restclienttemplate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.TweetDao;
import com.codepath.apps.restclienttemplate.models.TweetWithUser;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity implements ComposeDialogFragment.EditListTweets {
    public static final String TAG="TimelineActivity";
    public static final int REQUEST_CODE=20;

    TwitterClient client;
    RecyclerView rvTweets;
    List<Tweet> tweets;
    TweetsAdapter adapter;
    SwipeRefreshLayout swipeContainer;
    EndlessRecyclerViewScrollListener scrollListener;
    TweetDao tweetDao;
    public static User thisUser;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            Tweet tweet = Parcels.unwrap(data.getParcelableExtra("tweet"));

            tweets.add(0,tweet);
            adapter.notifyItemInserted(0);
            rvTweets.smoothScrollToPosition(0);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        FloatingActionButton floatingBtn;

        floatingBtn = findViewById(R.id.floatingBut);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setLogo(R.drawable.ic_twitter);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle(""); // set the top title


        client = TwitterApp.getRestClient(this);
        tweetDao = ((TwitterApp) getApplicationContext()).getMyDatabase().tweetDao();



        swipeContainer = findViewById(R.id.swipeConainer);
        // Configure the refreshing colors

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,

                android.R.color.holo_green_light,

                android.R.color.holo_orange_light,

                android.R.color.holo_red_light);
        
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG,"fetching new data");
                populateHomeTimeline();

            }
        });



        // find the recycler view
        rvTweets = findViewById(R.id.rvTweets);

        // Init the list of tweets and adapter
        tweets = new ArrayList<>();
        adapter = new TweetsAdapter(this, tweets);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // recycler view setup: layout manager and the adapter
        rvTweets.setLayoutManager(layoutManager);
        rvTweets.setAdapter(adapter);

        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showEditDialog();


            }
        });

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.i(TAG,"onLoad More" + page);
                loadMoreData();

            }
        };
        // Adds the scroll listener to RecyclerView
        rvTweets.addOnScrollListener(scrollListener);

        // query for existing tweets in database
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "showing data from database");
                List<TweetWithUser> tweetWithUsers = tweetDao.recentItems();
                List<Tweet> tweetsFromDB =TweetWithUser.getTweetList(tweetWithUsers);
                adapter.clear();
                adapter.addAll(tweetsFromDB);
            }
        });
        populateHomeTimeline();
    }
    // Launch compose Fragment
    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        ComposeDialogFragment editNameDialogFragment = ComposeDialogFragment.newInstance("Some Title");

        Bundle bundle = new Bundle();
        bundle.putParcelable("profile", Parcels.wrap(thisUser));
        editNameDialogFragment.setArguments(bundle);

        editNameDialogFragment.show(fm, "fragment_edit_name");



    }

    private  void loadMoreData() {
        // 1. Send an API request to retrieve appropriate paginated data
        client.getNextPageOfTweets(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG,"on success load more data"+ json.toString());
                // 2. Deserialize and construct new model objects from the API response
                JSONArray jsonArray = json.jsonArray;
                try {
                    List<Tweet> tweets = Tweet.fromJsonArray(jsonArray);
                    // 3. Append the new data objects to the existing set of items inside the array of items
                    // 4. Notify the adapter of the new items made with `notifyItemRangeInserted()`


                    adapter.addAll(tweets );
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.i(TAG,"on failure load more data",  throwable);

            }
        },tweets.get(tweets.size() - 1).id);


    }

    private void populateHomeTimeline() {
        client.getCredentials(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG, "success crendentials" +json.toString());
                JSONObject jsonObject = json.jsonObject;
                try {
                    thisUser = User.fromJson(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG,"on success"+ json.toString());
                JSONArray jsonArray = json.jsonArray;
                try {
                    List<Tweet> tweetFromNetwork = Tweet.fromJsonArray(jsonArray);
                            adapter.clear();
                    adapter.addAll(tweetFromNetwork);

                    // NOw we call this method to signal that refreshing has finished
                    swipeContainer.setRefreshing(false);
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            Log.i(TAG, "saving data into database");

                            // insert user


                            List<User> userFromNetwork = User.fromJsonTweetArray(tweetFromNetwork);
                            tweetDao.insertModel(userFromNetwork.toArray(new User[0]));
                            //insert tweet
                            tweetDao.insertModel(tweetFromNetwork.toArray(new Tweet[0]));

                        }
                    });
                } catch (JSONException e) {
                     Log.e(TAG, "json exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG,"on failure", throwable);

            }
        });
    }

    @Override
    public void onFinishEditDialog(Tweet tweet) {
        tweets.add(0,tweet);
        adapter.notifyItemInserted(0);
        rvTweets.smoothScrollToPosition(0);
    }
}