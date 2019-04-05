package com.example.poker101;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.poker101.date.Comanda;

import org.json.JSONException;

import java.io.IOException;

import static com.example.poker101.User.MY_PREFS_NAME;
import static com.example.poker101.User.oos;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getUserPreferences
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        User.theme_id = prefs.getInt("theme_id",0);
        User.card_id = prefs.getInt("card_id",0);

        super.onCreate(savedInstanceState);

        switch (User.theme_id) {
            case 0:
                setTheme(R.style.Default);
                break;
            case 1:
                setTheme(R.style.Dark);
                break;
            case 2:
                setTheme(R.style.Light);
                break;
            default: setTheme(R.style.Default);
        }

        setContentView(R.layout.activity_menu);

        Fragment fragment_menu = new MenuFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_menu, fragment_menu, "fragment_menu").commit();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        User.callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendAvailabilityInfoToServer("userOnline");
    }

    @Override
    protected void onPause() {
        super.onPause();
        sendAvailabilityInfoToServer("userOffline");
    }

    private void sendAvailabilityInfoToServer(final String option) {
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (User.socket == null) {
                        User.initialize();
                    }
                    Comanda cmd =
                            new Comanda(option,
                                    User.user.get("id"));
                    oos.writeObject(cmd);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        Thread myThread = new Thread(myRunnable);
        myThread.start();
        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
