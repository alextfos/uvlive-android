package es.uv.uvlive.ui.activity;

import android.content.Intent;
import android.os.SystemClock;

import es.uv.uvlive.data.database.models.ConversationTable;
import es.uv.uvlive.data.database.models.MessageTable;
import es.uv.uvlive.presenter.SplashPresenter;
import es.uv.uvlive.ui.actions.SplashActions;

/**
 * Created by alextfos on 24/11/2016.
 */

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
    public void onNotLogged() {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
}
