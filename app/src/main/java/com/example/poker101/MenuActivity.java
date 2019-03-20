package com.example.poker101;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
