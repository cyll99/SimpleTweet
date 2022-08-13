package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel

public class Url {
    String urlP;

    public Url(){}

    public String getUrlP() {
        return urlP;
    }

    public static Url fromJson(JSONObject jsonObject) throws JSONException {
        Url url = new Url();

        url.urlP = jsonObject.getString("url");
        return url;
    }

}
