package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

@Parcel
@Entity
public class ExtendedEntities {

    @ColumnInfo
    public String url;

    @ColumnInfo
    String type;

    public ExtendedEntities(){}

    public static ExtendedEntities fromJson(JSONObject jsonObject) throws JSONException {
        ExtendedEntities extended = new ExtendedEntities();

        if(!jsonObject.has("media")){
            extended.url = "";
            extended.type = ";";
        }else if (jsonObject.has("media")){
            final JSONArray media_Array = jsonObject.getJSONArray("media");
            extended.url = media_Array.getJSONObject(0).getString("url");
            extended.type = media_Array.getJSONObject(0).getString("type");
        }

        return extended;

    }
}
