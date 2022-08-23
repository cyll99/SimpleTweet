package com.codepath.apps.restclienttemplate.models;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity
public class User {
    @ColumnInfo
    @PrimaryKey
    public Long id;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String screenName;

    @ColumnInfo
    public String profileImageUrl;

    @ColumnInfo
    public int likes;

    @ColumnInfo
    public String image;


    public User(){}

    public static List<User> fromJsonTweetArray(List<Tweet> tweetFromNetwork) {
        List<User> users= new ArrayList<>();
        for(int i = 0; i< tweetFromNetwork.size(); i++){
            users.add(tweetFromNetwork.get(i).user);
        }
        return users;
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return "@"+screenName;
    }

    public int getLikes() {
        return likes;
    }

    public String getImage() {
        return image;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user = new User();

        user.name = jsonObject.getString("name");
        user.id = jsonObject.getLong("id");
        user.screenName = jsonObject.getString("screen_name");
        user.profileImageUrl = jsonObject.getString("profile_image_url_https");
        user.likes = jsonObject.getInt("favourites_count");
        user.image = jsonObject.getString("url");


        return user;

    }
}
