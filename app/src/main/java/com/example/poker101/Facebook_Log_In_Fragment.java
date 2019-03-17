package com.example.poker101;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class Facebook_Log_In_Fragment extends Fragment {

//    private CallbackManager callbackManager;

    public Facebook_Log_In_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_facebook__log__in_, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email", "picture"));
        LoginManager.getInstance().registerCallback(User.callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
//                        accessTokenTracker = new AccessTokenTracker() {
//                            @Override
//                            protected void onCurrentAccessTokenChanged(
//                                    AccessToken oldAccessToken,
//                                    AccessToken currentAccessToken) {
//                                // Set the access token using
//                                // currentAccessToken when it's loaded or set.
//                            }
//                        };
//
//
//                        profileTracker = new ProfileTracker() {
//                            @Override
//                            protected void onCurrentProfileChanged(
//                                    Profile oldProfile,
//                                    Profile currentProfile) {
//                                // App code
//                            }
//                        };

                        User.accessToken = AccessToken.getCurrentAccessToken();

                        GraphRequest request = GraphRequest.newMeRequest(
                                User.accessToken,
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        // Application code
                                        User.user = object;
                                        nextActivity();
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,link,picture,email,friends");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }

    public void nextActivity() {
        Activity activity1 = getActivity();
        Intent intent = new Intent(activity1, MenuActivity.class);
        startActivity(intent);
        activity1.finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        User.callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
