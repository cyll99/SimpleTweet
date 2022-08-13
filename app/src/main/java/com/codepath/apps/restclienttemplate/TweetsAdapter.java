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
        TextView likes;
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
//            likes = itemView.findViewById(R.id.likes);
            image = itemView.findViewById(R.id.image);






//
        }

        public void bind(Tweet tweet) {
            tvbody.setText(tweet.body);
            name.setText(tweet.user.name);
            userName.setText(tweet.user.getScreenName());
            date.setText(tweet.getCreatedAt());
//            likes.setText(tweet.user.likes);

//
//            Glide.with(context).load(tweet.url.getUrlP())
//                    .transform(new RoundedCorners(70))
//                    .into(image);

            Glide.with(context).load(tweet.user.profileImageUrl)
                    .transform(new RoundedCorners(70))
                    .into(ivProfileImage);
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
