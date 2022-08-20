package com.codepath.apps.restclienttemplate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    private ImageView image;




//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        MenuItem actionViewItem = menu.findItem(R.id.miActionButton);
//
//        // Retrieve the action-view from menu
//
//        // Find the button within action-view
//        Button b = findViewById(R.id.miActionButton);
//
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Handle button click here
//
//
////                Intent i =  new Intent(DetailActivity.this, TimelineActivity.class);
//////                i.putExtra("tweets", Parcels.wrap(tweet));
////
////                DetailActivity.this.startActivity(i);
//            }
//        });
//
//        return super.onPrepareOptionsMenu(menu);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        getSupportActionBar().setLogo(R.drawable.ic_twitter);

        getSupportActionBar().setTitle("    Tweet"); // set the top title


        imageTweet = findViewById(R.id.imageTweet);
        UserName = findViewById(R.id.Name);
        screenName = findViewById(R.id.userName);

        body = findViewById(R.id.body);
        retweet = findViewById(R.id.retweet);
        favorite = findViewById(R.id.favorite);

        retweets = findViewById(R.id.reply);
        favorites = findViewById(R.id.heart);
        time = findViewById(R.id.time);
        image = findViewById(R.id.image);


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


        if(!tweet.entities.getDisplay_url().isEmpty()){
            Glide.with(this).load(tweet.entities.getDisplay_url())
                    .transform(new RoundedCorners(20))
                    .into(image);}

        Glide.with(this).load(tweet.user
        .profileImageUrl)
                .transform(new RoundedCorners(70))
                .into(imageTweet);

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(DetailActivity.this, TimelineActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(intent, 0);
        return true;
    }
}