package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends AppCompatActivity {

    TextView favoritesRed;
    TextView comment;
    EditText compose;
    Button btnTweet;
    ImageView imageTweet;
    TextView UserName;
    TextView body;
    TextView screenName;
    TextView retweet;
    TextView favorite;
    TextView retweets;
    TextView retweet_green;
    TextView favorites;
    TextView time;
    ImageView image;
    public static final int MAX_LINES = 140;
    TwitterClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        getSupportActionBar().setLogo(R.drawable.ic_twitter);

        getSupportActionBar().setTitle("    Tweet"); // set the top title


        imageTweet = findViewById(R.id.imageTweet);
        UserName = findViewById(R.id.Name);
        screenName = findViewById(R.id.userName);
        comment = findViewById(R.id.comment);
        compose = findViewById(R.id.etCompose);

        body = findViewById(R.id.body);
        retweet = findViewById(R.id.retweet);
        favorite = findViewById(R.id.favorite);

        retweets = findViewById(R.id.reply);
        retweet_green = findViewById(R.id.reply2);
        favorites = findViewById(R.id.heart);
        time = findViewById(R.id.time);
        image = findViewById(R.id.image);
        btnTweet = findViewById(R.id.btnTweet);


        favoritesRed = findViewById(R.id.heart_red);

        client = TwitterApp.getRestClient(this);

        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweets"));


        body.setText(tweet.getBody());
        compose.setHint("Reply to " + tweet.user.getName());

        // reply to tweet with the edit text at the bottom

        compose.setText(tweet.user.getScreenName() + " ");

        UserName.setText(tweet.getUser().getName());
        screenName.setText(tweet.user.getScreenName());
        retweet.setText(tweet.getRetweet_count() + " RETWEETS");
        favorite.setText(tweet.getFavorite_count() + " FAVORITES");

        retweets.setText(tweet.getRetweet_count());
        favorites.setText(tweet.getFavorite_count());
        time.setText(tweet.getCreatedAt2());

        favoritesRed.setText(tweet.getFavorite_count());

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweetContent = compose.getText().toString();
                if (tweetContent.isEmpty()) {
                    Toast.makeText(DetailActivity.this, "Sorry your tweet cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (tweetContent.length() > MAX_LINES) {
                    Toast.makeText(DetailActivity.this, "Sorry your tweet is too long", Toast.LENGTH_LONG).show();
                    return;
                }
                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        try {
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            Log.i("tweet", tweet.body);



                            compose.setHint("Reply");
                            compose.setText("");
                            Toast.makeText(DetailActivity.this, "Tweeted", Toast.LENGTH_LONG).show();




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

                    }
                });

            }
        });


        if (!tweet.favorited) {
            favorites.setVisibility(View.VISIBLE);
            favoritesRed.setVisibility(View.INVISIBLE);
        } else {
            favorites.setVisibility(View.INVISIBLE);
            favoritesRed.setVisibility(View.VISIBLE);
        }


        if (!tweet.tweeted) {
            retweets.setVisibility(View.VISIBLE);
            retweet_green.setVisibility(View.INVISIBLE);
        } else {
            retweets.setVisibility(View.INVISIBLE);
            retweet_green.setVisibility(View.VISIBLE);
        }

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog(Parcels.wrap(tweet));
            }
        });


        retweets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tweet.retweet_count++;
                retweet_green.setVisibility(View.VISIBLE);
                retweets.setVisibility(View.INVISIBLE);
                retweet_green.setText(tweet.getRetweet_count());
                retweet.setText(tweet.getRetweet_count() + " RETWEETS");
                tweet.tweeted =true;

            }
        });

        retweet_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tweet.retweet_count--;
                retweet_green.setVisibility(View.INVISIBLE);
                retweets.setVisibility(View.VISIBLE);
                retweets.setText(tweet.getRetweet_count());
                retweet.setText(tweet.getRetweet_count() + " RETWEETS");
                tweet.tweeted =false;
            }
        });

        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tweet.favorite_count++;
                favorites.setVisibility(View.INVISIBLE);
                favoritesRed.setVisibility(View.VISIBLE);
                favoritesRed.setText(tweet.getFavorite_count());
                favorite.setText(tweet.getFavorite_count() + " FAVORITES");
                tweet.favorited = true;


            }


        });

        favoritesRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tweet.favorite_count--;
                favorites.setVisibility(View.VISIBLE);
                favoritesRed.setVisibility(View.INVISIBLE);
                favorites.setText(tweet.getFavorite_count());
                favorite.setText(tweet.getFavorite_count() + " FAVORITES");
                tweet.favorited = false;


            }


        });


        if (!tweet.entities.getDisplay_url().isEmpty()) {
            Glide.with(this).load(tweet.entities.getDisplay_url())
                    .transform(new RoundedCorners(20))
                    .into(image);
        }

        Glide.with(this).load(tweet.user
                .profileImageUrl)
                .transform(new RoundedCorners(70))
                .into(imageTweet);

    }

    private void showEditDialog(Parcelable tweet) {
        Bundle bundle = new Bundle();

        bundle.putParcelable("tweets", tweet);
        bundle.putParcelable("profile", Parcels.wrap(TimelineActivity.thisUser));


        FragmentManager fm = getSupportFragmentManager();
        ReplyDialogFragment editNameDialogFragment = ReplyDialogFragment.newInstance("Some Title");
        editNameDialogFragment.setArguments(bundle);

        editNameDialogFragment.show(fm, "fragment_edit_name");


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(DetailActivity.this, TimelineActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(intent, 0);
        return true;
    }
}