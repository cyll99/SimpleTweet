package com.codepath.apps.restclienttemplate.models;

import androidx.room.Entity;

import com.codepath.apps.restclienttemplate.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
@Parcel
@Entity

public class Tweet {
    public String body;
    public String createdAt;
    public User user;
    public int retweet_count;
    public int favorite_count;

    public boolean tweeted;
    public boolean favorited;


    public Entities entities;
    public ExtendedEntities extendedEntities;


    public long id;
//    public Organization url;

    public String getBody() {
        return body;
    }


    public String getCreatedAt() {
        return "."+TimeFormatter.getTimeDifference(createdAt);
    }
    public String getCreatedAt2() {
        return "."+TimeFormatter.getTimeStamp(createdAt);
    }


    public User getUser() {
        return user;
    }

    public long getId() {
        return id;
    }

    public Tweet(){}

    public String getRetweet_count() {
        return String.valueOf(retweet_count);
    }

    public String getFavorite_count() {
        return String.valueOf( favorite_count);
    }





    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.entities = Entities.fromJson(jsonObject.getJSONObject("entities"));

        tweet.id = jsonObject.getLong("id");

        tweet.retweet_count = jsonObject.getInt("retweet_count");
        tweet.favorite_count = jsonObject.getInt("favorite_count");

        tweet.tweeted = jsonObject.getBoolean("retweeted");
        tweet.favorited = jsonObject.getBoolean("favorited");


        if (jsonObject.has("extended_entities")){
            tweet.extendedEntities = ExtendedEntities.fromJson(jsonObject.getJSONObject("extended_entities"));

        }else{
            tweet.extendedEntities = new ExtendedEntities();
            tweet.extendedEntities.url = "";
            tweet.extendedEntities.type = "";
        }

        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();

        for (int i = 0; i<jsonArray.length(); i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

}
