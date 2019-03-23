package com.example.poker101;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;

import org.json.JSONObject;

public class User {
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    public static JSONObject user;
    public static AccessToken accessToken;
    public static CallbackManager callbackManager;


    public static int card_id;
    public static int theme_id;


    public static boolean goBackToSettings = false;
}
