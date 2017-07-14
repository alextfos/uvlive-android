package es.uv.uvlive.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.atraverf.uvlive.R;
import es.uv.uvlive.presenter.LoginPresenter;

import butterknife.BindView;
import es.uv.uvlive.ui.actions.SessionActions;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements SessionActions {

    @BindView(R.id.login_user_et)
    EditText mUser;

    @BindView(R.id.login_password_et)
    EditText mPassword;

    @BindView(R.id.login_spinner)
    Spinner mSpinner;

    private LoginPresenter loginPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,R.array.type_login_array,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
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
        loginPresenter.login(mUser.getText().toString(),mPassword.getText().toString(),
                ((String) mSpinner.getSelectedItem()));
    }

    @Override
    public void loginOk() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}