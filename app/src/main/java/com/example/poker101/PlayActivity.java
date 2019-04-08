package com.example.poker101;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.poker101.date.Comanda;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.poker101.User.currentOpponent;
import static com.example.poker101.User.oos;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        User.context = getApplicationContext();

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

        int layout = getIntent().getIntExtra("layout",-1);
        if (layout == -1) {
            //error
            return;
        }
        if (layout == R.layout.fragment_play__response_) {
            Fragment play_response_fragment = new Play_Response_Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_menu, play_response_fragment, "play_response_fragment").commit();
        } else {
            Fragment wait_accept_fragment = new Wait_Accept_Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_menu, wait_accept_fragment, "wait_accept_fragment").commit();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (User.socket == null) {
                        User.initialize();
                    }
                    Comanda cmd =
                            new Comanda("declineInvite",
                                    currentOpponent);
                    oos.writeObject(cmd);
                    User.currentOpponent = null;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception exp) {
                    exp.printStackTrace();
                }

            }


            ;
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
