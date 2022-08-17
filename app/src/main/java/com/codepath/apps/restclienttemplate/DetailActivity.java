package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
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
    private TextView retweets;
    private TextView favorites;
    private TextView time;
    TextView favoritesRed;



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

        retweets = findViewById(R.id.reply);
        favorites = findViewById(R.id.heart);
        time = findViewById(R.id.time);


        favoritesRed = findViewById(R.id.heart_red);


        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweets"));


        body.setText(tweet.getBody());
        UserName.setText(tweet.getUser().getName());
        screenName.setText(tweet.user.getScreenName());
        retweet.setText(tweet.getRetweet_count()+" RETWEETS");
        favorite.setText(tweet.getFavorite_count()+" FAVORITES");

        retweets.setText(tweet.getRetweet_count());
        favorites.setText(tweet.getFavorite_count());
        time.setText(tweet.getCreatedAt2());

        favoritesRed.setText(tweet.getFavorite_count());

        if(tweet.favorite_count == tweet.initialFavorite){
            favorites.setVisibility(View.VISIBLE);
            favoritesRed.setVisibility(View.INVISIBLE);
        }else{
            favorites.setVisibility(View.INVISIBLE);
            favoritesRed.setVisibility(View.VISIBLE);
        }

        retweets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tweet.retweet_count == tweet.initialRetweet) {
                    tweet.retweet_count++;
                }
                else{
                    tweet.retweet_count--;
                }
                retweets.setText(tweet.getRetweet_count());
                retweet.setText(tweet.getRetweet_count()+" RETWEETS");

            }
        });

        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tweet.favorite_count++;
                favorites.setVisibility(View.INVISIBLE);
                favoritesRed.setVisibility(View.VISIBLE);
                favoritesRed.setText(tweet.getFavorite_count());
                favorite.setText(tweet.getFavorite_count()+" FAVORITES");



            }


        });

        favoritesRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tweet.favorite_count--;
                favorites.setVisibility(View.VISIBLE);
                favoritesRed.setVisibility(View.INVISIBLE);
                favorites.setText(tweet.getFavorite_count());
                favorite.setText(tweet.getFavorite_count()+" FAVORITES");


            }


        });




        Glide.with(this).load(tweet.user
        .profileImageUrl)
                .transform(new RoundedCorners(70))
                .into(imageTweet);

    }
}