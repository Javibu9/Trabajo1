package com.example.trabajo1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;
import android.os.SystemClock;

public class App extends Application {

    @Override
    public void onCreate() {

        super.onCreate();
        SystemClock.sleep(3000);

    }
}
