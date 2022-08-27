package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class ReplyDialogFragment extends DialogFragment {

    private EditText mEditText;
    Button btnTweet;
    TwitterClient client;
    Context context;
    ImageButton cancel;
    TextInputLayout textField;
    TextView name;
    TextView username;
    ImageView profile;


    public static final int MAX_LINES = 280;
    public static final String TAG = "ComposeActivity";

    public ReplyDialogFragment() {
        // Empty constructor is required for DialogFragment

    }

    public static ReplyDialogFragment newInstance(String title) {
        ReplyDialogFragment frag = new ReplyDialogFragment();
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

        Bundle bundle = getArguments();
        Tweet tweet = Parcels.unwrap(bundle.getParcelable("tweets"));
        User thisUser = Parcels.unwrap(bundle.getParcelable("profile"));



        mEditText = view.findViewById(R.id.etCompose);
        btnTweet = view.findViewById(R.id.btnTweet);
        cancel = view.findViewById(R.id.btnCancel);
        textField = view.findViewById(R.id.textField);
        client = TwitterApp.getRestClient(context);
        name = view.findViewById(R.id.name);
        username = view.findViewById(R.id.username);
        profile = view.findViewById(R.id.profile);


        name.setText(thisUser.name);
        username.setText(thisUser.screenName);

        Glide.with(getContext()).load(thisUser.profileImageUrl)
                .transform(new RoundedCorners(70))
                .into(profile);

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setLayout(650, 2200);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        mEditText.setText(tweet.user.getScreenName()+" ");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        textField.setHint("Reply to " + tweet.user.getScreenName());
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

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dismiss();

                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG, "on failure to publish tweet", throwable);

                    }
                });

            }
        });

    }

}