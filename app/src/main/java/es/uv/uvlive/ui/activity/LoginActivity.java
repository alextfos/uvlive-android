package es.uv.uvlive.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.atraverf.uvlive.R;
import es.uv.uvlive.presenter.LoginPresenter;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;

import es.uv.uvlive.session.BusinessError;
import es.uv.uvlive.ui.actions.SessionActions;

public class LoginActivity extends BaseActivity implements SessionActions {

    @BindView(R.id.login_user_et)
    protected EditText mUser;

    @BindView(R.id.login_password_et)
    protected EditText mPassword;

    @BindView(R.id.login_spinner)
    protected Spinner mSpinner;

    @BindView(R.id.login_b)
    protected Button loginButton;

    private LoginPresenter loginPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dark_text_spinner_item, LoginPresenter.LoginTypes.getLoginTypesDescriptions(this));
        mSpinner.setAdapter(adapter);
        // TODO Remove
        mUser.setText("atraifos");
        mPassword.setText("atraifos");
        setListeners();
    }

    private void setListeners() {
        mPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                    login();
                }
                return false;
            }
        });
    }

    @Override
    protected void initializePresenter() {
        loginPresenter = new LoginPresenter(this);
    }

    @OnClick(R.id.login_b)
    public void login() {
        blockFields();
        loginPresenter.login(mUser.getText().toString(),mPassword.getText().toString(),
                mSpinner.getSelectedItemPosition());
    }

    @Override
    public void onError(BusinessError businessError) {
        super.onError(businessError);
        unblockFields();
    }

    @Override
    public void loginOk() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    /*
    * Private methods
    * */
    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void blockFields() {
        mUser.setEnabled(Boolean.FALSE);
        mPassword.setEnabled(Boolean.FALSE);
        mSpinner.setEnabled(Boolean.FALSE);
        loginButton.setEnabled(Boolean.FALSE);
    }

    private void unblockFields() {
        mUser.setEnabled(Boolean.TRUE);
        mPassword.setEnabled(Boolean.TRUE);
        mSpinner.setEnabled(Boolean.TRUE);
        loginButton.setEnabled(Boolean.TRUE);
    }
}