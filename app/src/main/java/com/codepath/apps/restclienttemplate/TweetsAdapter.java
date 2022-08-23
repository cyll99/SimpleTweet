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
import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.ui.SimpleMainThreadMediaPlayerListener;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;

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
        VideoPlayerView mVideoPlayer_1;
        ImageView mVideoCover;


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

            mVideoPlayer_1 = itemView.findViewById(R.id.video_player_1);
            mVideoCover = itemView.findViewById(R.id.video_cover_1);
        }

        public void bind(Tweet tweet) {
            tvbody.setText(tweet.body);
            name.setText(tweet.user.name);
            userName.setText(tweet.user.getScreenName());
            date.setText(tweet.getCreatedAt());
            retweets.setText(tweet.getRetweet_count());
            favorites.setText(tweet.getFavorite_count());

            favoritesRed.setText(tweet.getFavorite_count());

            if(!tweet.favorited){
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
                if(!tweet.tweeted) {
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
                    tweet.favorited = false;


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


            if(!tweet.extendedEntities.url.isEmpty()){
                mVideoPlayer_1.setVisibility(View.VISIBLE);
                VideoPlayerManager<MetaData> mVideoPlayerManager = new SingleVideoPlayerManager(new PlayerItemChangeListener() {
                    @Override
                    public void onPlayerItemChanged(MetaData metaData) {

                    }
                });


                mVideoPlayer_1.addMediaPlayerListener(new SimpleMainThreadMediaPlayerListener(){
                    @Override
                    public void onVideoPreparedMainThread() {
                        // We hide the cover when video is prepared. Playback is about to start
                        mVideoCover.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onVideoStoppedMainThread() {
                        // We show the cover when video is stopped
                        mVideoCover.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onVideoCompletionMainThread() {
                        // We show the cover when video is completed
                        mVideoCover.setVisibility(View.VISIBLE);
                    }
                });



                  mVideoPlayerManager.playNewVideo(null, mVideoPlayer_1, tweet.extendedEntities.url);

            }
        }
    }
}
