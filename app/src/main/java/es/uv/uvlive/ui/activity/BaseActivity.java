package es.uv.uvlive.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import es.uv.uvlive.data.ErrorManager;
import es.uv.uvlive.ui.actions.BaseActions;


public abstract class BaseActivity extends AppCompatActivity implements BaseActions {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializePresenter();
    }

    protected abstract void initializePresenter();

    @Override
    public void onError(int errorCode) {
        if (errorCode == ErrorManager.ERROR_CODE_TOKEN_EXPIRED) {
            logout();
        }
        Toast.makeText(this,getText(ErrorManager.getMessage(errorCode)),Toast.LENGTH_LONG).show();
    }

    public void logout() {
        // TODO make logout
    }
}
