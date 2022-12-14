package com.codepath.apps.restclienttemplate.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
@Entity
public class Entities {
    @ColumnInfo
    public String display_url;

    @ColumnInfo
    public String type;

    @PrimaryKey
    @ColumnInfo
    public Long id_photo;

    public Entities() {
    }

    public static Entities fromJson(JSONObject jsonObject) throws JSONException {
        Entities entities = new Entities();

        if (jsonObject.has("media")) {
            final JSONArray media_Array = jsonObject.getJSONArray("media");
            entities.display_url = media_Array.getJSONObject(0).getString("media_url_https");
            entities.id_photo = media_Array.getJSONObject(0).getLong("id");

        } else {
            entities.display_url = "";
        }
        entities.type = jsonObject.has("type") ? jsonObject.getString("type") : "";


        return entities;

    }

    public String getDisplay_url() {
        return display_url;
    }

    public String getType() {
        return type;
    }

}
