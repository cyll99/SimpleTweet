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
    public User user;
    public int retweet_count;
    public int favorite_count;


    public long id;
//    public Url url;

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

    public int getRetweet_count() {
        return retweet_count;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.id = jsonObject.getLong("id");

        tweet.retweet_count = jsonObject.getInt("retweet_count");
        tweet.retweet_count = jsonObject.getInt("favorite_count");

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
