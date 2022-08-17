package com.codepath.apps.restclienttemplate.models;

import com.codepath.apps.restclienttemplate.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
@Parcel

public class Tweet {
    public String body;
    public String createdAt;
    public UserM user;
    public int retweet_count;
    public int favorite_count;

    public int initialRetweet;
    public int initialFavorite;
    public Entities entities;


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


    public UserM getUser() {
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
        tweet.user = UserM.fromJson(jsonObject.getJSONObject("user"));
        tweet.entities = Entities.fromJson(jsonObject.getJSONObject("entities"));

        tweet.id = jsonObject.getLong("id");

        tweet.retweet_count = jsonObject.getInt("retweet_count");
        tweet.favorite_count = jsonObject.getInt("favorite_count");

        tweet.initialRetweet = jsonObject.getInt("retweet_count");
        tweet.initialFavorite = jsonObject.getInt("favorite_count");


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
