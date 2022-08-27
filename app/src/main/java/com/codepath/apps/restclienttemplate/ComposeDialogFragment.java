package com.codepath.apps.restclienttemplate;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;



import okhttp3.Headers;

public class ComposeDialogFragment extends DialogFragment {

    private EditText mEditText;
    Button btnTweet;
    TwitterClient client;
    Context context;
    ImageButton cancel;
    TextView name;
    TextView username;
    ImageView profile;


    public static final int MAX_LINES = 280;
    public static final String TAG = "ComposeActivity";
    public static final String KEY = "BROUILLON";

    public ComposeDialogFragment() {
        // Empty constructor is required for DialogFragment

    }

    public static ComposeDialogFragment newInstance(String title) {
        ComposeDialogFragment frag = new ComposeDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compose, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view

        mEditText = view.findViewById(R.id.etCompose);
        btnTweet = view.findViewById(R.id.btnTweet);
        cancel = view.findViewById(R.id.btnCancel);
        name = view.findViewById(R.id.name);
        username = view.findViewById(R.id.username);
        profile = view.findViewById(R.id.profile);

        client = TwitterApp.getRestClient(context);


        Bundle bundle = getArguments();
        User thisUser = Parcels.unwrap(bundle.getParcelable("profile"));

        name.setText(thisUser.name);
        username.setText(thisUser.screenName);

        Glide.with(getContext()).load(thisUser.profileImageUrl)
                .transform(new RoundedCorners(70))
                .into(profile);


        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        getDialog().getWindow().setLayout(650, 2200);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        // Saving draft...
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String draft = pref.getString(KEY, "");

        if(!draft.isEmpty()){
            mEditText.setText(draft);
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open();
            }
        });

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweetContent = mEditText.getText().toString();
                if (tweetContent.isEmpty()) {
                    Toast.makeText(context, "Sorry your tweet cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (tweetContent.length() > MAX_LINES) {
                    Toast.makeText(context, "Sorry your tweet is too long", Toast.LENGTH_LONG).show();
                    return;
                }
                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG, "on success load more data");
                        try {
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            Log.i(TAG, "published tweet is : " + tweet.body);
                            Intent intent = new Intent();
                            intent.putExtra("tweet", Parcels.wrap(tweet));

                            // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
                            EditListTweets listener = (EditListTweets) getTargetFragment();
                            listener.onFinishEditDialog(tweet);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG, "on failure to publish tweet", throwable);

                    }
                });
                dismiss();

            }
        });


    }
    // Open pop up
    public void open(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("Save draft?");
        alertDialogBuilder.setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        save();
                    }
                });

        alertDialogBuilder.setNegativeButton("Delete",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    // Save draft

    private void save(){
        String compose = mEditText.getText().toString();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(KEY, compose);
        edit.commit();
        dismiss();
    }

    // 1. Defines the listener interface with a method passing back data result.
    public interface EditListTweets {
        void onFinishEditDialog(Tweet tweet);
    }

    }
