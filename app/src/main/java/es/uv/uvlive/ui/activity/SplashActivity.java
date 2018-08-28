package es.uv.uvlive.ui.activity;

import android.content.Intent;

import es.uv.uvlive.presenter.SplashPresenter;
import es.uv.uvlive.ui.actions.SplashActions;

public class SplashActivity extends BaseActivity implements SplashActions {

    @Override
    protected void initializePresenter() {
        SplashPresenter splashPresenter = new SplashPresenter(this);
        splashPresenter.getStatus();
    }

    @Override
    public void onLogged() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onNotLogged() {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
}
