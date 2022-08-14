package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageTweet;
    private TextView UserName;
    private TextView body;
    private TextView screenName;
    private TextView retweet;
    private TextView favorite;
    private TextView time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_twitter);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle(""); // set the top title


        imageTweet = findViewById(R.id.imageTweet);
        UserName = findViewById(R.id.Name);
        screenName = findViewById(R.id.userName);

        body = findViewById(R.id.body);
        retweet = findViewById(R.id.retweet);
        favorite = findViewById(R.id.favorite);
        time = findViewById(R.id.time);




        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweets"));


        body.setText(tweet.getBody());
        UserName.setText(tweet.getUser().getName());
        screenName.setText(tweet.user.getScreenName());
        retweet.setText(tweet.retweet_count+" RETWEETS");
        favorite.setText(tweet.favorite_count+" FAVORITES");
        UserName.setText(tweet.getCreatedAt2());









        Glide.with(this).load(tweet.user
        .profileImageUrl)
                .transform(new RoundedCorners(70))
                .into(imageTweet);

    }
}