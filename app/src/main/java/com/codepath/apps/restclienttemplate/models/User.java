package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {
    public String name;
    public String screenName;
    public String profileImageUrl;
    public int likes;
    public String image;


    public User(){}

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
        user.screenName = jsonObject.getString("screen_name");
        user.profileImageUrl = jsonObject.getString("profile_image_url_https");
        user.likes = jsonObject.getInt("favourites_count");
        user.image = jsonObject.getString("url");


        return user;

    }
}
