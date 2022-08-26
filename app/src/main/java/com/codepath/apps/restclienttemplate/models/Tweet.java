package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.codepath.apps.restclienttemplate.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId"))

public class Tweet {
    @ColumnInfo
    public String body;

    @ColumnInfo
    public String createdAt;

    @Ignore
    public User user;

    @ColumnInfo
    public long userId;

    @ColumnInfo
    public int retweet_count;

    @ColumnInfo
    public int favorite_count;

    @ColumnInfo
    public boolean tweeted;

    @ColumnInfo
    public boolean favorited;

    @Ignore
    public Entities entities;

    @Ignore
    public ExtendedEntities extendedEntities;

    @ColumnInfo
    @PrimaryKey
    public long id;
//    public Organization url;

    public Tweet() {
    }

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        User user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.userId = user.id;
        tweet.user = user;

        tweet.entities = Entities.fromJson(jsonObject.getJSONObject("entities"));

        tweet.id = jsonObject.getLong("id");

        tweet.retweet_count = jsonObject.getInt("retweet_count");
        tweet.favorite_count = jsonObject.getInt("favorite_count");

        tweet.tweeted = jsonObject.getBoolean("retweeted");
        tweet.favorited = jsonObject.getBoolean("favorited");


        if (jsonObject.has("extended_entities")) {
            tweet.extendedEntities = ExtendedEntities.fromJson(jsonObject.getJSONObject("extended_entities"));

        } else {
            tweet.extendedEntities = new ExtendedEntities();
            tweet.extendedEntities.url = "";
            tweet.extendedEntities.type = "";
        }

        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        return "." + TimeFormatter.getTimeDifference(createdAt);
    }

    public String getCreatedAt2() {
        return "." + TimeFormatter.getTimeStamp(createdAt);
    }

    public User getUser() {
        return user;
    }

    public long getId() {
        return id;
    }

    public String getRetweet_count() {
        return String.valueOf(retweet_count);
    }

    public String getFavorite_count() {
        return String.valueOf(favorite_count);
    }

}
