package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>{

    Context context;
    List<Tweet> tweets;
    // Pass in context list of tweets

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // get the data

        Tweet tweet = tweets.get(position);

        // bind the tweet with viewholder
        holder.bind(tweet);

    }
    // bind values based on the position


    @Override
    public int getItemCount() {
        return tweets.size();
    }
    //Clean elements of recycler view
    public void clear(){

        tweets.clear();
        notifyDataSetChanged();
    }

        // Add a list of items
    public void addAll(List<Tweet> tweetList){
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }




    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivProfileImage;
        TextView tvbody;
        TextView name;
        TextView userName;
        TextView date;
        TextView favorites;
        TextView retweets;
        TextView favoritesRed;
        ImageView image;


        RelativeLayout container;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvbody = itemView.findViewById(R.id.tvBody);
            name = itemView.findViewById(R.id.sceenName);
            userName = itemView.findViewById(R.id.UName);
            container = itemView.findViewById(R.id.container);
            date = itemView.findViewById(R.id.Date);
            favorites = itemView.findViewById(R.id.heart);
            retweets = itemView.findViewById(R.id.reply);
            image = itemView.findViewById(R.id.image);

            favoritesRed = itemView.findViewById(R.id.heart_red);







        }

        public void bind(Tweet tweet) {
            tvbody.setText(tweet.body);
            name.setText(tweet.user.name);
            userName.setText(tweet.user.getScreenName());
            date.setText(tweet.getCreatedAt());
            retweets.setText(tweet.getRetweet_count());
            favorites.setText(tweet.getFavorite_count());

            favoritesRed.setText(tweet.getFavorite_count());

            if(tweet.favorite_count == tweet.initialFavorite){
                favorites.setVisibility(View.VISIBLE);
                favoritesRed.setVisibility(View.INVISIBLE);
            }else{
                favorites.setVisibility(View.INVISIBLE);
                favoritesRed.setVisibility(View.VISIBLE);
            }


            Glide.with(context).load(tweet.user.profileImageUrl)
                    .transform(new RoundedCorners(70))
                    .into(ivProfileImage);

            if(!tweet.entities.getDisplay_url().isEmpty()){
            Glide.with(context).load(tweet.entities.getDisplay_url())
                    .transform(new RoundedCorners(20))
                    .into(image);}



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
                }
            });

            favorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        tweet.favorite_count++;
                        favorites.setVisibility(View.INVISIBLE);
                        favoritesRed.setVisibility(View.VISIBLE);
                        favoritesRed.setText(tweet.getFavorite_count());



                }


            });

            favoritesRed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    tweet.favorite_count--;
                    favorites.setVisibility(View.VISIBLE);
                    favoritesRed.setVisibility(View.INVISIBLE);
                    favorites.setText(tweet.getFavorite_count());


                }


            });

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 2 Navigate on new activity on tap

                    Intent i =  new Intent(context, DetailActivity.class);
                    i.putExtra("tweets", Parcels.wrap(tweet));

                    context.startActivity(i);

                }
            });

        }
    }
}
