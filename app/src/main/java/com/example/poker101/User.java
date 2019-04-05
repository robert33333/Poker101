package com.example.poker101;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;

import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class User {
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

    public static void initialize() {
        try {
            socket = new Socket("10.0.2.2", 9090);
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
