package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);



        imageTweet = findViewById(R.id.imageTweet);
        UserName = findViewById(R.id.Name);
        screenName = findViewById(R.id.userName);

        body = findViewById(R.id.body);


        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweets"));


        body.setText(tweet.getBody());
        UserName.setText(tweet.getUser().getName());
        screenName.setText(tweet.user.getScreenName());








        Glide.with(this).load(tweet.user
        .profileImageUrl)
                .transform(new RoundedCorners(70))
                .into(imageTweet);

    }
}