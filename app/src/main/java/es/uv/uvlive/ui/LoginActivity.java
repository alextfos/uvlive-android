package es.uv.uvlive.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.atraverf.uvlive.R;
import es.uv.uvlive.presenter.SessionPresenter;
import es.uv.uvlive.ui.actions.SessionActions;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by atraverf on 17/11/15.
 */
public class LoginActivity extends BaseActivity implements SessionActions {

    @InjectView(R.id.login_user_et)
    EditText mUser;

    @InjectView(R.id.login_password_et)
    EditText mPassword;

    @InjectView(R.id.login_spinner)
    Spinner mSpinner;

    private SessionPresenter sessionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.inject(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,R.array.type_login_array,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
    }

    @Override
    protected void initializePresenter() {
        sessionPresenter = new SessionPresenter(this);
    }

    @OnClick(R.id.login_b)
    public void login() {
        hideKeyboard();
        sessionPresenter.login(mUser.getText().toString(),mPassword.getText().toString(),
                (String) mSpinner.getSelectedItem());
    }

    @Override
    public void loginOk() {
        Intent intent = this.getIntent();
        this.setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void loginError() {
        Toast.makeText(this,"Ha habido un problema con el login",Toast.LENGTH_LONG).show();
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}