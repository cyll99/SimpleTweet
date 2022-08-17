package com.codepath.apps.restclienttemplate.models;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Entities {
    String display_url, type;

    public Entities(){}

    public String getDisplay_url() {
        return display_url;
    }

    public String getType() {
        return type;
    }

    public static Entities fromJson(JSONObject jsonObject) throws JSONException {
        Entities entities = new Entities();

        if (jsonObject.has("media")){
            final JSONArray media_Array = jsonObject.getJSONArray("media");
            entities.display_url = media_Array.getJSONObject(0).getString("media_url_https");
        }else {
            entities.display_url="";
        }
        entities.type = jsonObject.has("type") ? jsonObject.getString("type") : "";


        return entities;

    }

}
