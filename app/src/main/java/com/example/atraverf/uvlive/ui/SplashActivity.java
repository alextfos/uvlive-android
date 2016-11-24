package com.example.atraverf.uvlive.ui;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by alextfos on 24/11/2016.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
