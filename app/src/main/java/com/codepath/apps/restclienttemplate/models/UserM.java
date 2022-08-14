package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class UserM {
    public String name;
    public String screenName;
    public String profileImageUrl;
    public int likes;
    public String image;


    public UserM(){}

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

    public static UserM fromJson(JSONObject jsonObject) throws JSONException {
        UserM user = new UserM();

        user.name = jsonObject.getString("name");
        user.screenName = jsonObject.getString("screen_name");
        user.profileImageUrl = jsonObject.getString("profile_image_url_https");
        user.likes = jsonObject.getInt("favourites_count");
        user.image = jsonObject.getString("url");


        return user;

    }
}
