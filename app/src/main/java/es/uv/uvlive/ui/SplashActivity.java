package es.uv.uvlive.ui;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by alextfos on 24/11/2016.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class);
        // TODO make a status request and call
        startActivity(intent);
        finish();
    }

    @Override
    protected void initializePresenter() {

    }
}
