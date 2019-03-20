package com.example.poker101;

import android.app.Activity;
import android.content.Intent;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;

import org.json.JSONObject;

public class User {
    public static JSONObject user;
    public static AccessToken accessToken;
    public static CallbackManager callbackManager;
}
