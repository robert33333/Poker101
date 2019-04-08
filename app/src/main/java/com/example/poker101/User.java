package com.example.poker101;

import android.content.Context;
import android.content.Intent;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;

import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class User {


    public static Context context;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    public static JSONObject user;
    public static AccessToken accessToken;
    public static CallbackManager callbackManager;


    public static int card_id;
    public static int theme_id;


    public static boolean goBackToSettings = false;

    public static ObjectOutputStream oos;
    public static ObjectInputStream ois;
    public static Socket socket;


    public static String currentOpponent;

    public final static UserThread T = new UserThread();

    public static void initialize() {
        try {
            socket = new Socket("10.0.2.2", 9090);
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());


            T.start();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void goToWaitScreen() {
        Intent intent = new Intent(context, PlayActivity.class);
        intent.putExtra("layout",R.layout.fragment_play__response_);
        context.startActivity(intent);
    }

    public static void goToWaitScreen(boolean invited) {
        Intent intent = new Intent(context, PlayActivity.class);
        intent.putExtra("layout",R.layout.fragment_wait__accept_);
        context.startActivity(intent);
    }

    public static void goToMenu() {
        Intent intent = new Intent(context, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("fromDecline",true);
        context.startActivity(intent);
    }
}
