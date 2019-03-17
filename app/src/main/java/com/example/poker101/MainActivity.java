package com.example.poker101;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;

import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

//    public static CallbackManager callbackManager;
//    public static AccessTokenTracker accessTokenTracker;
//    public static ProfileTracker profileTracker;
//    public static AccessToken accessToken;
//    public JSONObject user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User.callbackManager = CallbackManager.Factory.create();

        // If the access token is available already assign it.
        User.accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = User.accessToken != null && !User.accessToken.isExpired();


        if (isLoggedIn) {
            //sarim peste login

            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email", "picture"));

//            accessTokenTracker = new AccessTokenTracker() {
//                @Override
//                protected void onCurrentAccessTokenChanged(
//                        AccessToken oldAccessToken,
//                        AccessToken currentAccessToken) {
//                    // Set the access token using
//                    // currentAccessToken when it's loaded or set.
//                }
//            };
//
//
//            profileTracker = new ProfileTracker() {
//                @Override
//                protected void onCurrentProfileChanged(
//                        Profile oldProfile,
//                        Profile currentProfile) {
//                    // App code
//                }
//            };

            //luam date
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

        } else {
            Fragment fragment_facebook_log_in_app_name = new Facebook_Log_In_Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_log_in, fragment_facebook_log_in_app_name, "fragment_facebook_log_in_app_name").commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        User.callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void nextActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (accessTokenTracker != null && accessTokenTracker.isTracking()) {
//            accessTokenTracker.stopTracking();
//        }
//        if (profileTracker != null && profileTracker.isTracking()) {
//            profileTracker.stopTracking();
//        }
    }
}
