package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.Viewholder>{

    Context context;
    List<Tweet> tweets;
    // Pass in context list of tweets

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new Viewholder(view);
    }

    // for each row inflate a layout

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

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




    public class Viewholder extends RecyclerView.ViewHolder{

        ImageView ivProfileImage;
        TextView tvbody;
        TextView tvScreenName;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvbody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);

        }

        public void bind(Tweet tweet) {
            tvbody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);
        }
    }
}
