package com.example.trabajo1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
public class SplashActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        Intent intentSecond = new Intent(this, SecondActivity.class);
        Intent intentMain = new Intent(this, MainActivity.class);
        if (!TextUtils.isEmpty(Util.getUserMailPrefs(preferences)) &&!TextUtils.isEmpty(Util.getUserPassPrefs(preferences))) {
            startActivity(intentSecond);
        } else {
            startActivity(intentMain);
        }
        finish();
    }
}

