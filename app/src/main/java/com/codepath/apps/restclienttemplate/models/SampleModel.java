package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/*
 * This is a temporary, sample model that demonstrates the basic structure
 * of a SQLite persisted Model object. Check out the Room guide for more details:
 * https://github.com/codepath/android_guides/wiki/Room-Guide
 *
 */
@Entity
public class SampleModel {

	@PrimaryKey(autoGenerate = true)
	Long id;

	// Define table fields
	@ColumnInfo
	private String name;
	private String body;
	private String createdAt;
//	private User user;
	private int retweet_count;
	private int favorite_count;
	private boolean favorited;
	boolean retweeted;

	public SampleModel() {
		super();
	}

	// Parse model from JSON
	public SampleModel(JSONObject object){
		super();

		try {


			this.name = object.getString("title");
			this.body = object.getString("text");
			this.createdAt = object.getString("created_at");
			this.retweet_count = object.getInt("retweet_count");
			this.favorite_count = object.getInt("favorite_count");
			this.favorited = object.getBoolean("favorited");
			this.retweeted = object.getBoolean("retweeted");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// Getters
	public String getName() {
		return name;
	}

	public String getBody() {
		return body;
	}

	public String getCreatedAt() {
		return createdAt;
	}

//	public User getUser() {
//		return user;
//	}

	public int getRetweet_count() {
		return retweet_count;
	}

	public int getFavorite_count() {
		return favorite_count;
	}

	public boolean isFavorited() {
		return favorited;
	}

	public boolean isRetweeted() {
		return retweeted;
	}

	// Setters
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

//	public void setUser(User user) {
//		this.user = user;
//	}

	public void setRetweet_count(int retweet_count) {
		this.retweet_count = retweet_count;
	}

	public void setFavorite_count(int favorite_count) {
		this.favorite_count = favorite_count;
	}

	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	public void setRetweeted(boolean retweeted) {
		this.retweeted = retweeted;
	}
}
