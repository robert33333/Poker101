package com.example.poker101;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    TextView chips_total_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        chips_total_text = findViewById(R.id.chips_total_text);
        chips_total_text.setText(Integer.toString(User.player_info.bani_totali));
    }

    @Override
    protected void onResume() {
        super.onResume();
        User.context = getApplicationContext();
        User.getMessage=true;
    }

    @Override
    protected void onPause() {
        User.getMessage=false;
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
